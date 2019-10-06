package com.example.joanderson.swishflick.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.joanderson.swishflick.R;
import com.example.joanderson.swishflick.adapters.HomeAdapter;
import com.example.joanderson.swishflick.interfaces.FragmentComunicator;
import com.example.joanderson.swishflick.models.HomeFragmentItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private CarouselView carouselView;
    private int[] sampleImages = {R.drawable.image_1, R.drawable.image_2, R.drawable.image_3};
    private RecyclerView recyclerView;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private Button searchButton;
    private FragmentComunicator fragmentComunicator;
    ArrayList <HomeFragmentItem> hfis = new ArrayList<>();
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.recyclerFragmentHome);
        fragmentComunicator = (FragmentComunicator) getActivity();

        //configurando adapter
        //HomeFragmentItem item;

        HomeAdapter homeAdapter = new HomeAdapter(getContext(),(FragmentComunicator) getActivity(),hfis);
        searchButton = view.findViewById(R.id.search_button);

        //configurando Recyclerview
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(false);

        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(homeAdapter);

        carouselView = (CarouselView) view.findViewById(R.id.carouselView);
        carouselView.setPageCount(sampleImages.length);
        carouselView.setImageListener(imageListener);
        return view;



    }

    private void StartSearchFragment(View v) {
        fragmentComunicator.fragmentChange("iSearchFragment",null);
    }

    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(sampleImages[position]);
        }
    };

}
