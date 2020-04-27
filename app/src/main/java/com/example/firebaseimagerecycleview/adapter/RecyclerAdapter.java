package com.example.firebaseimagerecycleview.adapter;

import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.firebaseimagerecycleview.R;
import com.example.firebaseimagerecycleview.organization.Upload;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyHolder> {
    private Context context;
    private List<Upload>pictures;

    public RecyclerAdapter(Context context, List<Upload> pictures) {
        this.context = context;
        this.pictures = pictures;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.image_item,parent,false);
        return new MyHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        Upload upload = pictures.get(position);
        holder.tvName.setText(upload.getmName());
        Picasso.get().load(upload.getImageUrl()).into(holder.imgRecycler);
        //Glide.with(context).load("com.google.android.gms.tasks.zzu@54b9440").into(holder.imgRecycler);
    }

    @Override
    public int getItemCount() {
        return pictures.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private ImageView imgRecycler;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tvName = (TextView)itemView.findViewById(R.id.tv_name);
            imgRecycler = (ImageView)itemView.findViewById(R.id.img_view_upload);
        }
    }
}
