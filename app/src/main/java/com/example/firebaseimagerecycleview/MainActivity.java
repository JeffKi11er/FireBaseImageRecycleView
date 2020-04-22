package com.example.firebaseimagerecycleview;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firebaseimagerecycleview.organization.Upload;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static int PICK_IMAGE = 101;
    private Button btnSearch;
    private TextView tvBtnReload;
    private EditText edtSearch;
    private Button btnUpload;
    private ProgressBar progressBar;
    private ImageView imgShow;
    private Uri mUri;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private StorageTask uploadTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        edtSearch = (EditText) findViewById(R.id.edt_pick);
        btnSearch = (Button)findViewById(R.id.btn_pick);
        btnUpload = (Button)findViewById(R.id.btn_upload);
        tvBtnReload = (TextView) findViewById(R.id.btn_reload);
        progressBar = (ProgressBar)findViewById(R.id.progress_bar);
        imgShow = (ImageView)findViewById(R.id.img_show);
        btnUpload.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
        tvBtnReload.setOnClickListener(this);
        /* uploads/435435435.jpg */
        storageReference = FirebaseStorage.getInstance().getReference("uploads");
        databaseReference = FirebaseDatabase.getInstance().getReference("uploads");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_pick:
                openFileChooser();
                break;
            case R.id.btn_reload:

                break;
            case R.id.btn_upload:
                if (uploadTask!=null&&uploadTask.isInProgress()){
                    Toast.makeText(MainActivity.this,"Upload in Progress",Toast.LENGTH_LONG).show();
                }else {
                    upLoad();
                }
                break;
        }
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        //startActivityForResult(intent,PICK_IMAGE);
        startActivityForResult(intent,PICK_IMAGE);//Intent.createChooser(intent,"Select Profile Image"), PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null){
            mUri = data.getData();
            Picasso.get().load(mUri).into(imgShow);
            ///To set ImageView Uri like imageURI

        }
    }
    private String getFileExtension(Uri uri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap min = MimeTypeMap.getSingleton();
        return min.getExtensionFromMimeType(cr.getType(uri));
    }
    private void upLoad(){
        if (mUri!=null){
            /// uploads/System.currentTimeMillis()+"."+getFileExtension(uri) likes above
            /// child just to continue the storageReference
            StorageReference fileReference = storageReference.child(
                    System.currentTimeMillis() +"."+getFileExtension(mUri));
           uploadTask = fileReference.putFile(mUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(0);
                        }
                    },500);
                    Toast.makeText(MainActivity.this,"Uploaded Successful",Toast.LENGTH_LONG).show();
                    Upload upload = new Upload(edtSearch.getText().toString().trim(),
                            taskSnapshot.getStorage().getDownloadUrl().toString());
                    String uploadId = databaseReference.push().getKey();
                    databaseReference.child(uploadId).setValue(upload);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();

                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.
                            getTotalByteCount());
                    progressBar.setProgress((int) progress);

                }
            });
        }else {
            Toast.makeText(this,"No file selected",Toast.LENGTH_LONG).show();

        }
    }
}
