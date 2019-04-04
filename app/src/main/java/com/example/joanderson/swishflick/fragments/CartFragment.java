package com.example.joanderson.swishflick.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.joanderson.swishflick.R;
import com.example.joanderson.swishflick.adapters.CartAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends Fragment {

    RecyclerView rvCartProducts;
    public CartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        rvCartProducts = view.findViewById(R.id.rvCartProducts);

        CartAdapter cartAdapter = new CartAdapter();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvCartProducts.setLayoutManager(layoutManager);
        rvCartProducts.setHasFixedSize(true);
        rvCartProducts.setAdapter(cartAdapter);

        return view;
    }

}
