package com.example.firebaseimagerecycleview;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

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
}
