package com.example.joanderson.swishflick.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.joanderson.swishflick.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductFragment extends Fragment {

    String product;
    Context context;
    Button btSize;

    public ProductFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product, container, false);

        btSize = view.findViewById(R.id.btFragmentProductSize);
        context = view.getContext();
        btSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSizeMenu(btSize);
            }
        });
        return view;
    }

    public void showSizeMenu(View view) {
        //todo: mudar isso para spinner
        PopupMenu popupMenu = new PopupMenu(context, view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.popup_size_menu,popupMenu.getMenu());
        popupMenu.show();
    }

    public void setProduct(String product) {
        this.product = product;
    }

}
