package com.amayo.androidupax.movies.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.amayo.androidupax.R;
import com.amayo.androidupax.movies.MovieFragment;
import com.amayo.androidupax.movies.detail.DetailActivity;
import com.amayo.androidupax.movies.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class movieAdapter extends RecyclerView.Adapter<movieAdapter.CustomerViewHolder>
        implements ItemClickListener{

    private List<Movie> items;
    private Context context;

    public movieAdapter(List<Movie> items, Context context){
        this.context = context;
        this.items   = items;
    }


    @Override
    public CustomerViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_view_customer,viewGroup,false);
        return new CustomerViewHolder(v,this) ;
    }

    @Override
    public void onBindViewHolder(CustomerViewHolder viewHolder, int i) {
        String name = String.valueOf(items.get(i).getTitle());
        String qty = String.valueOf(items.get(i).getOverview());

        viewHolder.tvNameCustomer.setText(name);
        viewHolder.tvDefault.setText(qty);
        Picasso.with(context).load("https://image.tmdb.org/t/p/w300_and_h450_bestv2"
                + items.get(i)
                .getBackdropPath())
                .resize(200, 250)
                .into(viewHolder.imageMovie);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onItemClick(View view, int position) {

        Intent i = new Intent(context, DetailActivity.class);
        i.putExtra(MovieFragment.EXTRA_MOVIE_ID,items.get(position).getId());
        i.putExtra(MovieFragment.EXTRA_TITLE,items.get(position).getTitle());
        ((Activity) context).startActivityForResult(i, MovieFragment.REQUEST_UPDATE_DELETE_CUSTOMER);


    }


    public static class CustomerViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{
        public TextView tvNameCustomer,tvDefault;
        private CircleImageView imageMovie;
        public ItemClickListener listener;


        public CustomerViewHolder(View v,ItemClickListener listener){
            super(v);

            tvNameCustomer = v.findViewById(R.id.txtNameMovie);
            tvDefault = v.findViewById(R.id.txtDescriptionMovie);
            imageMovie = v.findViewById(R.id.imageMovie);
            this.listener = listener;
            v.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(v, getAdapterPosition());
        }
    }
}

interface ItemClickListener{
    void onItemClick(View view, int position);
}
