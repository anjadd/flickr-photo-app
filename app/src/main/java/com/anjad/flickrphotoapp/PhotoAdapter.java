package com.anjad.flickrphotoapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anjad.flickrphotoapp.models.AllPhotos;
import com.anjad.flickrphotoapp.models.Photo;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder> {

    private Context mContext;
    private AllPhotos mPhotos;
    private ListItemClickListener mListItemClickListener;

    public PhotoAdapter() {
    }

    public PhotoAdapter(Context mContext, AllPhotos mPhotos, ListItemClickListener mListItemClickListener) {
        this.mContext = mContext;
        this.mPhotos = mPhotos;
        this.mListItemClickListener = mListItemClickListener;
    }

    public interface ListItemClickListener{
        void onListItemClick(int position);
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item, parent, false);
        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
        if (mPhotos != null && mPhotos.getPhotos() != null) {
            Photo photo = mPhotos.getPhotos().get(position);

            Glide.with(mContext)
                    .load(photo.getSmallPhoto().getSmallPhotoLink())
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .into(holder.mIvPhoto);

            holder.mTvName.setText(photo.getTitle());
        }

    }

    @Override
    public int getItemCount() {
        if (mPhotos == null || mPhotos.getPhotos() == null)
            return 0;
        return mPhotos.getPhotos().size();
    }

    public class PhotoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.iv_rv_photo)
        ImageView mIvPhoto;

        @BindView(R.id.tv_photo_name)
        TextView mTvName;

        public PhotoViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mListItemClickListener.onListItemClick(getAdapterPosition());
        }
    }
}
