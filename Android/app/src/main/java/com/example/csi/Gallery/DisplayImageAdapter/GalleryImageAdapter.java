package com.example.csi.Gallery.DisplayImageAdapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.csi.Gallery.Interfaces.IRecyclerViewClickListener;
import com.example.csi.R;

import java.util.ArrayList;

public class GalleryImageAdapter extends RecyclerView.Adapter<GalleryImageAdapter.ImageViewHolder> {

    Context context;
    ArrayList<String> urlList;
    IRecyclerViewClickListener clickListener;

    public GalleryImageAdapter(Context context, ArrayList<String> urlList, IRecyclerViewClickListener clickListener){
        Log.i("urlListSize", String.valueOf(urlList.size()));
        this.context = context;
        this.urlList = urlList;
        this.clickListener = clickListener;
        Log.i("urlListSizePost", String.valueOf(urlList.size()));
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.gallery_item,viewGroup, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder imageViewHolder, int position) {
        Log.i("urlListSizeOnBind", String.valueOf(urlList.size()));
        String currentImage = urlList.get(position);
        Log.i("urlListSizeOnBindGetPos", urlList.get(position) + " at " + position);
        ImageView imageView = imageViewHolder.imageView;
        //final ProgressBar progressBar = imageViewHolder.progressBar;
        Log.i("onBindViewHolder","onBindViewHolder");

        Glide.with(context)
                .load(currentImage)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        Log.i("LoadFailed", "Failure");
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        //progressBar.setVisibility(View.GONE);
                        Log.i("ResourceReady", "yo success");
                        return false;
                    }
                }).into(imageView);
    }

    @Override
    public int getItemCount() {
        return urlList.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageView;
        //ProgressBar progressBar;

        public ImageViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            //progressBar = (ProgressBar) itemView.findViewById(R.id.progBar);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onClick(v, getAdapterPosition());
        }
    }
}
