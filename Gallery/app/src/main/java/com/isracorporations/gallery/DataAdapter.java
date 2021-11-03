package com.isracorporations.gallery;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.net.CookieHandler;
import java.util.ArrayList;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    private ArrayList<File> imageUrlList;
    private Context context;


    public DataAdapter(Context context, ArrayList<File> imageUrls) {
        this.context = context;
        this.imageUrlList = imageUrls;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_url, parent, false);
        return new ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        holder.img.setImageURI(Uri.fromFile(imageUrlList.get(position)));

    }

    @Override
    public int getItemCount() {
        return imageUrlList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public static ImageView img;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.imageView);
        }
    }
}
