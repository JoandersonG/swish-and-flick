package com.example.joanderson.swishflick.activites;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.joanderson.swishflick.R;
import com.example.joanderson.swishflick.fragments.CartFragment;
import com.example.joanderson.swishflick.fragments.CategoriesFragment;
import com.example.joanderson.swishflick.fragments.HomeFragment;
import com.example.joanderson.swishflick.fragments.ProfileFragment;
import com.example.joanderson.swishflick.fragments.SearchFragment;
import com.example.joanderson.swishflick.fragments.SearchResultsFragment;
import com.example.joanderson.swishflick.interfaces.FragmentComunicator;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements FragmentComunicator {

    ImageButton ibHome, ibCategories, ibProfile, ibSearch, ibCart;
    protected DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ibHome = findViewById(R.id.ibHome);
        ibCategories = findViewById(R.id.ibCategories);
        ibProfile = findViewById(R.id.ibProfile);
        ibSearch = findViewById(R.id.ibSearch);
        ibCart = findViewById(R.id.ibCart);

        ref.child("usuarios").child("002").setValue("Ana");

        //inicia onCreate
        buttonActivate(ibHome);

    }


    public void buttonActivate(View v){
        if (v.isActivated()) {
            return;
        }
        ibSearch.setActivated(false);
        ibProfile.setActivated(false);
        ibCategories.setActivated(false);
        ibHome.setActivated(false);
        ibCart.setActivated(false);

        v.setActivated(true);

        loadFragment(v);
    }

    public void loadFragment(View v) {
        ImageButton ib = (ImageButton) v;
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        if (ib.getId() == ibHome.getId()) {
            //carrega fragment home
            HomeFragment homeFragment = new HomeFragment();
            fragmentTransaction.replace(R.id.main_frame, homeFragment);
        }
        else if (ib.getId() == ibCategories.getId()) {
            CategoriesFragment categoriesFragment = new CategoriesFragment();
            fragmentTransaction.replace(R.id.main_frame, categoriesFragment);
        }
        else if (ib.getId() == ibSearch.getId()) {
            SearchFragment searchFragment = new SearchFragment();
            fragmentTransaction.replace(R.id.main_frame, searchFragment);

        }
        else if (ib.getId() == ibProfile.getId()) {
            ProfileFragment profileFragment = new ProfileFragment();
            fragmentTransaction.replace(R.id.main_frame, profileFragment);
        }
        else if (ib.getId() == ibCart.getId()) {
            CartFragment cartFragment = new CartFragment();
            fragmentTransaction.replace(R.id.main_frame, cartFragment);
        }
        else {
            //TODO: erro;
        }
        fragmentTransaction.commit();
    }

    @Override
    public void fragmentChange(String operation, String data) {
        switch (operation) {
            case "iSearchResultsFragment":
                //i from initiate
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                SearchResultsFragment searchResultsFragment = new SearchResultsFragment();
                searchResultsFragment.setSearchString(data);
                fragmentTransaction.replace(R.id.main_frame,searchResultsFragment);
                fragmentTransaction.commit();
                break;
            default:
                //todo: erro
        }
    }
}
