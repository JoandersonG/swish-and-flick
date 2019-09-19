package com.example.joanderson.swishflick.activites;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.example.joanderson.swishflick.R;
import com.example.joanderson.swishflick.fragments.AddProductFragment;
import com.example.joanderson.swishflick.fragments.CartFragment;
import com.example.joanderson.swishflick.fragments.CategoriesFragment;
import com.example.joanderson.swishflick.fragments.HomeFragment;
import com.example.joanderson.swishflick.fragments.HomeSeeMoreFragment;
import com.example.joanderson.swishflick.fragments.ProductFragment;
import com.example.joanderson.swishflick.fragments.ProfileFragment;
import com.example.joanderson.swishflick.fragments.SearchFragment;
import com.example.joanderson.swishflick.fragments.SearchResultsFragment;
import com.example.joanderson.swishflick.interfaces.FragmentComunicator;
import com.example.joanderson.swishflick.models.Cash;
import com.example.joanderson.swishflick.models.HomeFragmentItem;
import com.example.joanderson.swishflick.models.product.Book;
import com.example.joanderson.swishflick.models.product.Product;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements FragmentComunicator {

    ImageButton ibHome, ibCategories, ibProfile, ibSearch, ibCart;
    private BottomNavigationView bottom_navigation;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        ibHome = findViewById(R.id.ibHome);
        ibCategories = findViewById(R.id.ibCategories);
        ibProfile = findViewById(R.id.ibProfile);
        ibSearch = findViewById(R.id.ibSearch);
        ibCart = findViewById(R.id.ibCart);

        bottom_navigation = findViewById(R.id.bottom_navigation);
        bottom_navigation.setOnNavigationItemSelectedListener(navListener);

//        writeNewUser("user1","Joanderson", "joanderson@ufba.br");
       //addProductsToDatabase();
        //addMainSetProducts();

        //inicia onCreate
        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,
                new HomeFragment()).commit();
        //buttonActivate(ibHome);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;

                    switch (menuItem.getItemId()) {
                        case R.id.nav_home:
                            selectedFragment = new HomeFragment();
                            break;

                        case R.id.nav_categories:
                            selectedFragment = new CategoriesFragment();
                            break;
                        case R.id.nav_search:
                            selectedFragment = new SearchFragment();
                            break;
                        case R.id.nav_profile:
                            selectedFragment = new ProfileFragment();
                            break;
                        case R.id.nav_cart:
                            selectedFragment = new CartFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,
                            selectedFragment).commit();
                    return true;
                };
            };

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

        //loadFragment(v);
    }


    public void loadFragment(View v) {
        ImageButton ib = (ImageButton) v;
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        if (ib.getId() == ibHome.getId()) {
            //carrega fragment home
            HomeFragment homeFragment = new HomeFragment();
            //fragmentTransaction.setPrimaryNavigationFragment(homeFragment);
            fragmentTransaction.add(R.id.main_frame, homeFragment);
        }
        else if (ib.getId() == ibCategories.getId()) {
            CategoriesFragment categoriesFragment = new CategoriesFragment();
            fragmentTransaction.add(R.id.main_frame, categoriesFragment);
        }
        else if (ib.getId() == ibSearch.getId()) {
            SearchFragment searchFragment = new SearchFragment();
            fragmentTransaction.add(R.id.main_frame, searchFragment);

        }
        else if (ib.getId() == ibProfile.getId()) {
            ProfileFragment profileFragment = new ProfileFragment();
            fragmentTransaction.add(R.id.main_frame, profileFragment);
        }
        else if (ib.getId() == ibCart.getId()) {
            CartFragment cartFragment = new CartFragment();
            fragmentTransaction.add(R.id.main_frame, cartFragment);
        }
        else {
            //TODO: erro;
        }
        fragmentTransaction.commit();
    }

    @Override
    public void fragmentChange(String operation, Bundle data) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        switch (operation) {
            case "iSearchResultsFragment":
                SearchResultsFragment searchResultsFragment = new SearchResultsFragment();
                String search = data.getString("searchData","");
                searchResultsFragment.setSearchString(search);
                fragmentTransaction.replace(R.id.main_frame,searchResultsFragment);
                break;
            case "iProductFragment":
                ProductFragment productFragment = new ProductFragment();
                productFragment.setProduct("this is a product");
                fragmentTransaction.replace(R.id.main_frame,productFragment);
                break;
            case "iSeeMoreFragment":
                HomeSeeMoreFragment seeMoreFragment = new HomeSeeMoreFragment();
                fragmentTransaction.replace(R.id.main_frame,seeMoreFragment);
                break;
            case "iAddProductFragment":
                AddProductFragment addProductFragment = new AddProductFragment();
                fragmentTransaction.replace(R.id.main_frame,addProductFragment);
                break;
            case "fAddProductFragment":
                ProfileFragment profileFragment = new ProfileFragment();
                fragmentTransaction.replace(R.id.main_frame,profileFragment);
            default:
                //todo: {erro: fragment não encontrada}
        }
        fragmentTransaction.commit();
    }


    public void activityChange(String operation) {

        switch (operation) {
            case "iPhotoPicker":
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 1);
                break;
            default:
                //todo: {erro: comando não encontrado}
        }
    }

    private void writeNewUser(String userId, String name, String email) {
        DatabaseReference users = databaseReference.child("clients");
        //User user = new User(name, email);

        //databaseReference.child("users").child(userId).setValue(user);
    }

    private void addMainSetProducts() {
        final DatabaseReference products = databaseReference.child("products");

        Query item = products.orderByKey().limitToFirst(3);
        final ArrayList <String> produtos = new ArrayList<>();
        item.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data: dataSnapshot.getChildren()) {
                    produtos.add(data.getValue(String.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        HomeFragmentItem hfi = new HomeFragmentItem("Descubra a magia das palavras",produtos);

        databaseReference.child("home_fragment_items").push().setValue(hfi);

    }

    private void addProductsToDatabase() {

        DatabaseReference products = databaseReference.child("products");

        String randomId = UUID.randomUUID().toString();
        StorageReference images = storageReference.child("images").child("books").child(randomId);

        //String imageId, String name, String description, Cash price, int stockAmount
        Product produto1 = new Book("Os Contos de Beelde, o Bardo",
                "Uma clássica coletânea de contos infantis. Venha se encontar com as " +
                        "histórias de bruxos poderosos.",
                new Cash(0,20,5),50,120,
                "Beedle, O Bardo","Rocco" );

        products.child("books").child(randomId).setValue(produto1);

        /////
        randomId = UUID.randomUUID().toString();
        images = storageReference.child("images").child("books").child(randomId);

        //String imageId, String name, String description, Cash price, int stockAmount
        Product produto2 = new Book("Animais Fantáticos e Onde Habitam",
                "O mais famoso magizoologista Newt Scamander explica com riquesa de" +
                        " detalhes a vida, comportamentos e habitat dos mais diversos e incríveis " +
                        "animais mágicos.",
                new Cash(0,20,10),50,420,
                "Newt Scamander","Dan&Glo" );

        products.child("books").child(randomId).setValue(produto2);


        //-----------------

        randomId = UUID.randomUUID().toString();
        images = storageReference.child("images").child("books").child(randomId);

        //String imageId, String name, String description, Cash price, int stockAmount
        Product produto3 = new Book("The Standard Book of Spells",
                "The standard and widly used book for the most useful and practical " +
                        "spells, perfect for a student growing in the world of magic - and any " +
                        "adult who wishes to learn amazing skills to make life easier.",
                new Cash(0,28,5),50,500,
                "Miranda Gorshawk","StuMahoo" );

        products.child("books").child(randomId).setValue(produto3);

        //-----------------

        randomId = UUID.randomUUID().toString();
        images = storageReference.child("images").child("books").child(randomId);

        //String imageId, String name, String description, Cash price, int stockAmount
        Product produto4 = new Book("Advanced Potion Making 1st year",
                "The first book in standard magic schools arround the world, from one " +
                        "of the best potion maker the world had ever seen, it's common sense that" +
                        " if you never read this book, you - for sure - know nothing of potion making.",
                new Cash(0,28,5),70,450,
                "Liberatus Borage","BrackDawln" );

        products.child("books").child(randomId).setValue(produto4);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        AddProductFragment.activityResultImagePick(requestCode,resultCode, data);
    }
}
