package com.example.joanderson.swishflick.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.joanderson.swishflick.R;
import com.example.joanderson.swishflick.interfaces.FragmentComunicator;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoriesFragment extends Fragment {

    ImageButton ibClothing, ibPotions, ibQuiddich, ibHogwarts, ibMuggleArtefatcs,
            ibmMagicArtefacts, ibBooks, ibJewls;

    public CategoriesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_categories, container, false);

        ibClothing = view.findViewById(R.id.ibCategoriesClothing);
        ibBooks = view.findViewById(R.id.ibCategoriesBooks);
        ibHogwarts = view.findViewById(R.id.ibCategoriesHogwarts);
        ibJewls = view.findViewById(R.id.ibCategoriesJewls);
        ibmMagicArtefacts = view.findViewById(R.id.ibCategoriesMagicArtefacts);
        ibMuggleArtefatcs = view.findViewById(R.id.ibCategoriesMuggleArtefacts);
        ibPotions = view.findViewById(R.id.ibCategoriesPotions);
        ibQuiddich = view.findViewById(R.id.ibCategoriesQuiddich);

        return view;
    }

    public void openCategory(View view) {
        String operation;

        if (view.getId() == ibQuiddich.getId()) {
            Bundle bundle = new Bundle();
            bundle.putString("searchData","object seeMore here");
            ((FragmentComunicator) getActivity()).fragmentChange("iSeeMoreFragment",bundle);
        }
    }

}
