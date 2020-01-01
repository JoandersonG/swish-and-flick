package com.example.joanderson.swishflick.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.joanderson.swishflick.R;
import com.example.joanderson.swishflick.adapters.ProductsAdapter;
import com.example.joanderson.swishflick.interfaces.FragmentComunicator;
import com.example.joanderson.swishflick.models.product.Product;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchResultsFragment extends Fragment {

    private RecyclerView rvSearchResults;
    private EditText etSearchResults;
    private String searchString;
    public SearchResultsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_results, container, false);


        rvSearchResults = view.findViewById(R.id.rvSearchResults);
        etSearchResults = view.findViewById(R.id.etSearchResults);

        etSearchResults.setText(searchString);
        ((InputMethodManager) getContext().getSystemService(getContext().INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(etSearchResults.getWindowToken(),0);

        //Now I find my results: activated enabled


        //then I send my results to be shown:
        ProductsAdapter productsAdapter = new ProductsAdapter((FragmentComunicator) getActivity(),new ArrayList<Product>());
        RecyclerView.LayoutManager layoutManager2 = new GridLayoutManager(getContext(),2);
        rvSearchResults.setLayoutManager(layoutManager2);
        rvSearchResults.setAdapter(productsAdapter);

        return view;
    }


    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    public String getSearchString() {
        return this.searchString;
    }

}
