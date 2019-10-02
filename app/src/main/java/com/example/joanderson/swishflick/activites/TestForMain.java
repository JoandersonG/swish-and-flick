package com.example.joanderson.swishflick.activites;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.example.joanderson.swishflick.R;
import com.example.joanderson.swishflick.fragments.AddProductFragment;
import com.example.joanderson.swishflick.fragments.HomeFragment;
import com.example.joanderson.swishflick.fragments.HomeSeeMoreFragment;
import com.example.joanderson.swishflick.fragments.ProductFragment;
import com.example.joanderson.swishflick.fragments.ProfileFragment;
import com.example.joanderson.swishflick.fragments.SearchResultsFragment;
import com.example.joanderson.swishflick.interfaces.FragmentComunicator;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

public class TestForMain extends AppCompatActivity implements FragmentComunicator {

    CarouselView carouselView;

    int[] sampleImages = {R.drawable.image_1, R.drawable.image_2, R.drawable.image_3};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_for_main);

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_principal,
                new HomeFragment()).commit();

        carouselView = (CarouselView) findViewById(R.id.carouselView);
        carouselView.setPageCount(sampleImages.length);

        carouselView.setImageListener(imageListener);


    }

    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(sampleImages[position]);
        }
    };

    @Override
    public void fragmentChange(String operation, Bundle data) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        switch (operation) {
            case "iSearchResultsFragment":
                SearchResultsFragment searchResultsFragment = new SearchResultsFragment();
                String search = data.getString("searchData","");
                searchResultsFragment.setSearchString(search);
                fragmentTransaction.replace(R.id.frame_principal,searchResultsFragment);
                break;
            case "iProductFragment":
                ProductFragment productFragment = new ProductFragment();
                productFragment.setProduct("this is a product");
                fragmentTransaction.replace(R.id.frame_principal,productFragment);
                break;
            case "iSeeMoreFragment":
                HomeSeeMoreFragment seeMoreFragment = new HomeSeeMoreFragment();
                fragmentTransaction.replace(R.id.frame_principal,seeMoreFragment);
                break;
            case "iAddProductFragment":
                AddProductFragment addProductFragment = new AddProductFragment();
                fragmentTransaction.replace(R.id.frame_principal,addProductFragment);
                break;
            case "fAddProductFragment":
                ProfileFragment profileFragment = new ProfileFragment();
                fragmentTransaction.replace(R.id.frame_principal,profileFragment);
            default:
                //todo: {erro: fragment não encontrada}
        }
        fragmentTransaction.commit();
    }
}
