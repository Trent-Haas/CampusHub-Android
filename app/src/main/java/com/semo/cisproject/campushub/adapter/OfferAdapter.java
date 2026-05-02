package com.semo.cisproject.campushub.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.semo.cisproject.campushub.R;
import com.semo.cisproject.campushub.model.Product;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.MyViewHolder> {

    private final List<Product> offerList;
    private final Context context;
    private String tag;

    public OfferAdapter(List<Product> offerList, Context context) {
        this.offerList = offerList;
        this.context = context;
    }

    public OfferAdapter(List<Product> offerList, Context context, String tag) {
        this.offerList = offerList;
        this.context = context;
        this.tag = tag;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_offer, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final Product product = offerList.get(position);

        if (product.getImage() != null && !product.getImage().isEmpty()) {
            Picasso.get()
                    .load(product.getImage())
                    .placeholder(R.drawable.no_image)
                    .error(R.drawable.no_image)
                    .into(holder.imageView, new Callback() {
                        @Override
                        public void onSuccess() {
                            holder.progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError(Exception e) {
                            holder.progressBar.setVisibility(View.GONE);
                        }
                    });
        } else {
            holder.progressBar.setVisibility(View.GONE);
            holder.imageView.setImageResource(R.drawable.no_image);
        }
    }

    @Override
    public int getItemCount() {
        return (offerList != null) ? offerList.size() : 0;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        ProgressBar progressBar;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.offer_image);
            progressBar = itemView.findViewById(R.id.progressbar);
        }
    }
}