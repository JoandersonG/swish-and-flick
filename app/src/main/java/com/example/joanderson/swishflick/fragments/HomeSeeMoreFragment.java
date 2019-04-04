package com.example.joanderson.swishflick.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.joanderson.swishflick.R;
import com.example.joanderson.swishflick.adapters.ProductsAdapter;
import com.example.joanderson.swishflick.interfaces.FragmentComunicator;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeSeeMoreFragment extends Fragment {

    RecyclerView rvProducts;
    TextView title;
    public HomeSeeMoreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_see_more, container, false);

        rvProducts = view.findViewById(R.id.rvHomeSeeMore);

        ProductsAdapter productsAdapter = new ProductsAdapter((FragmentComunicator) getActivity());

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(),2);
        rvProducts.setLayoutManager(layoutManager);
        rvProducts.setHasFixedSize(true);
        rvProducts.setAdapter(productsAdapter);

        return view;
    }

}
