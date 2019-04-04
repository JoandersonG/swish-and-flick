package com.example.joanderson.swishflick.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.joanderson.swishflick.R;
import com.example.joanderson.swishflick.interfaces.FragmentComunicator;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.MyViewHolder> {

    private FragmentComunicator comunicator;

    public ProductsAdapter(FragmentComunicator comunicator) {
        this.comunicator = comunicator;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View item = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.adapter_products,viewGroup,false);

        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.ivAdapterProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //iniciar uma fragment
                comunicator.fragmentChange("iProductFragment","objeto produto");
            }
        });
    }

    @Override
    public int getItemCount() {
        return 20;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView ivAdapterProduct;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ivAdapterProduct = itemView.findViewById(R.id.ivAdapterProduct);
        }
    }


}
