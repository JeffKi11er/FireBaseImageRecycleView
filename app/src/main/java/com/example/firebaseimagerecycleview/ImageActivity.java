package com.example.firebaseimagerecycleview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class ImageActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        init();
    }

    private void init() {
        recyclerView = (RecyclerView)findViewById(R.id.rcl_view);
    }
}
