package com.example.joanderson.swishflick.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.joanderson.swishflick.R;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View item = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.adapter_home, viewGroup, false);

        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView productImage1, productImage2, productImage3;
        TextView productName1, productName2, productName3;
        TextView productPrice1, productPrice2, productPrice3;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            productImage1 = itemView.findViewById(R.id.ivProduct1);
            productName1 = itemView.findViewById(R.id.tvProduct1);
            productPrice1 = itemView.findViewById(R.id.tvPrice1);

            productImage2 = itemView.findViewById(R.id.ivProduct2);
            productName2 = itemView.findViewById(R.id.tvProduct2);
            productPrice2 = itemView.findViewById(R.id.tvPrice2);

            productImage3 = itemView.findViewById(R.id.ivProduct3);
            productName3 = itemView.findViewById(R.id.tvProduct3);
            productPrice3 = itemView.findViewById(R.id.tvPrice3);
        }
    }

}
