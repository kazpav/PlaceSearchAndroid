package com.example.android.appprealpha;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Pavlo on 11-Apr-18.
 */

public class GooglePlaceAdapter extends RecyclerView.Adapter<GooglePlaceAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<GooglePlace> googlePlaces;
    private Context mContext;

    GooglePlaceAdapter(Context context, List<GooglePlace> googlePlaces){
        this.googlePlaces = googlePlaces;
        this.inflater = LayoutInflater.from(context);
        this.mContext = context;
    }

    @Override
    public GooglePlaceAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GooglePlaceAdapter.ViewHolder holder, int position) {
        GooglePlace googlePlace = googlePlaces.get(position);
        //IMAGE
//        holder.imageView.setImageResource(googlePlace.getImageURL());
        if(!TextUtils.isEmpty(googlePlace.getImageURL())){
            Picasso.with(mContext).load(googlePlace.getImageURL())
                    .error(R.drawable.placeholder)
                    .placeholder(R.drawable.placeholder)
                    .into(holder.imageView);
        }
        holder.nameView.setText(googlePlace.getName());
        holder.categoryView.setText(googlePlace.getCategory());
        holder.ratingView.setText("Rating: "+googlePlace.getRating());
        holder.openView.setText("Open now: "+googlePlace.getOpen());

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Log.i("CLICKED", "onClick: "+googlePlaces.get(position).getName()+"===========================================");
                Uri.Builder builder = new Uri.Builder();
                builder.scheme("https")
                        .authority("www.google.com")
                        .appendPath("maps")
                        .appendPath("dir")
                        .appendPath("")
                        .appendQueryParameter("api", "1")
                        .appendQueryParameter("destination", googlePlaces.get(position).getAddress());
                String url = builder.build().toString();
                Intent i = new Intent( Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                view.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return googlePlaces.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final ImageView imageView;
        final TextView nameView, categoryView, ratingView, openView;

        private ItemClickListener itemClickListener;

        ViewHolder(View view){
            super(view);
            //IMAGE
            imageView = (ImageView)view.findViewById(R.id.iv_image);
            nameView = (TextView)view.findViewById(R.id.tv_name);
            categoryView = (TextView)view.findViewById(R.id.tv_category);
            ratingView = (TextView)view.findViewById(R.id.tv_rating);
            openView = (TextView)view.findViewById(R.id.tv_open);

            view.setOnClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener){
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view, getAdapterPosition());
        }
    }
}
