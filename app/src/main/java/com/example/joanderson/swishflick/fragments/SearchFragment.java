package com.example.joanderson.swishflick.fragments;


import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.example.joanderson.swishflick.R;
import com.example.joanderson.swishflick.interfaces.FragmentComunicator;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    RecyclerView rvSearchPrevious;
    EditText etSearchbar;
    FragmentComunicator fragmentComunicator;
    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        rvSearchPrevious = view.findViewById(R.id.rvSearchPrevious);
        etSearchbar = view.findViewById(R.id.etSearch);
        fragmentComunicator = (FragmentComunicator) getActivity();

        etSearchbar.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (keyCode == EditorInfo.IME_ACTION_DONE)) {
                    fragmentComunicator.fragmentChange("iSearchResultsFragment",
                            etSearchbar.getText().toString());
                    return true;
                }
                System.out.println("não entrou");
                return false;
            }
        });
       /* etSearchbar.getText().toString().endsWith();
        etSearchbar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if (event.toString().charAt(event.toString().length()-1) == '\n') {
//                    //todo: inicio outra fragment
//                    fragmentComunicator.fragmentChange("iSearchResultsFragment",
//                            etSearchbar.getText().toString());
//
//                    return true;
//                }
                if (etSearchbar.getText().toString().charAt(etSearchbar.getText().toString().length()-1) == '') {
                    fragmentComunicator.fragmentChange("iSearchResultsFragment",
                            etSearchbar.getText().toString());
                    System.out.println("entrou");
                    return true;
                }
                System.out.println("não entrou");
                return false;
            }
        });*/





//        SearchCategoriesAdapter sca = new SearchCategoriesAdapter();
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
//        rvSearchPrevious.setLayoutManager(layoutManager);
//        rvSearchPrevious.setAdapter(sca);




        return view;
    }

}