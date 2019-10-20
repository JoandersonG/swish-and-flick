package com.example.joanderson.swishflick.activites;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

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
import com.example.joanderson.swishflick.models.product.Broomstick;
import com.example.joanderson.swishflick.models.product.Clothing;
import com.example.joanderson.swishflick.models.product.Potion;
import com.example.joanderson.swishflick.models.product.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

import static com.google.firebase.database.ServerValue.TIMESTAMP;

public class MainActivity extends AppCompatActivity implements FragmentComunicator {


    TextInputEditText galleons;
    ImageButton ibHome, ibCategories, ibProfile, ibSearch, ibCart;
    private BottomNavigationView bottom_navigation;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
/*
        ibHome = findViewById(R.id.ibHome);
        ibCategories = findViewById(R.id.ibCategories);
        ibProfile = findViewById(R.id.ibProfile);
        ibSearch = findViewById(R.id.ibSearch);
        ibCart = findViewById(R.id.ibCart);
*/
        bottom_navigation = findViewById(R.id.bottom_navigation);
        bottom_navigation.setOnNavigationItemSelectedListener(navListener);
//        galleons = findViewById(R.id.etFragAddPriceGalleon);
//        galleons.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if ( ((TextInputEditText)view).getText().toString().equals("0")) {
//                    ((TextInputEditText)view).setText("");
//                }
//            }
//        });
//        writeNewUser("user1","Joanderson", "joanderson@ufba.br");
       //addProductsToDatabase();
        //addMainSetProducts();

        //inicia onCreate
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_principal,
                new HomeFragment()).commit();
        //buttonActivate(ibHome);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

    }



    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;
                    String tagFragment = "";
                    switch (menuItem.getItemId()) {
                        case R.id.nav_home:
                            selectedFragment = new HomeFragment();
                            tagFragment = "HOME_FRAGMENT";
                            break;

                        case R.id.nav_categories:
                            selectedFragment = new CategoriesFragment();
                            tagFragment = "CATEGORIES_FRAGMENT";
                            break;
                        case R.id.nav_profile:
                            selectedFragment = new ProfileFragment();
                            tagFragment = "PROFILE_FRAGMENT";
                            break;
                        case R.id.nav_cart:
                            selectedFragment = new CartFragment();
                            tagFragment = "CART_FRAGMENT";
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_principal,
                            selectedFragment, tagFragment).commit();
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
            fragmentTransaction.add(R.id.frame_principal, homeFragment);
        }
        else if (ib.getId() == ibCategories.getId()) {
            CategoriesFragment categoriesFragment = new CategoriesFragment();
            fragmentTransaction.add(R.id.frame_principal, categoriesFragment);
        }
        else if (ib.getId() == ibSearch.getId()) {
            SearchFragment searchFragment = new SearchFragment();
            fragmentTransaction.add(R.id.frame_principal, searchFragment);

        }
        else if (ib.getId() == ibProfile.getId()) {
            ProfileFragment profileFragment = new ProfileFragment();
            fragmentTransaction.add(R.id.frame_principal, profileFragment);
        }
        else if (ib.getId() == ibCart.getId()) {
            CartFragment cartFragment = new CartFragment();
            fragmentTransaction.add(R.id.frame_principal, cartFragment);
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
                fragmentTransaction.replace(R.id.frame_principal,searchResultsFragment);
                break;
            case "iProductFragment":
                ProductFragment productFragment = new ProductFragment();
                productFragment.setProduct("this is a product");
                fragmentTransaction.replace(R.id.frame_principal,productFragment, "PRODUCT_FRAGMENT");
                break;
            case "iSeeMoreFragment":
                HomeSeeMoreFragment seeMoreFragment = new HomeSeeMoreFragment();
                fragmentTransaction.replace(R.id.frame_principal,seeMoreFragment);
                break;
            case "iAddProductFragment":

                AddProductFragment addProductFragment = new AddProductFragment();
                fragmentTransaction.replace(R.id.frame_principal,addProductFragment,"ADD_PRODUCT_FRAGMENT");
                break;
            case "fAddProductFragment":
                ProfileFragment profileFragment = new ProfileFragment();
                fragmentTransaction.replace(R.id.frame_principal,profileFragment);
            case "iSearchFragment":
                SearchFragment searchFragment = new SearchFragment();
                fragmentTransaction.replace(R.id.frame_principal,searchFragment);
            default:
                //todo: {erro: fragment não encontrada}
        }
        fragmentTransaction.commit();
    }

    public void testValidation(View view) {
        AddProductFragment addProductFragment = (AddProductFragment) getSupportFragmentManager().findFragmentByTag("ADD_PRODUCT_FRAGMENT");
        if (addProductFragment != null) addProductFragment.testValidation(view);
        else {
            System.out.println("fragment não encontrado");
        }
        if (addProductFragment == null) {
            Toast.makeText(this, "Erro interno: fragment não encontrada",Toast.LENGTH_SHORT).show();
            return;
        }
        if (addProductFragment.testValidation(view)) {
            Product product = addProductFragment.getProduct();
            saveProductOnDatabase(product, addProductFragment.getImageByte());
        }

    }

    /*public void getImage(View view) {
        AddProductFragment addProductFragment = (AddProductFragment) getSupportFragmentManager().findFragmentByTag("ADD_PRODUCT_FRAGMENT");
        if (addProductFragment == null) {
            Toast.makeText(this, "Erro interno: fragment não encontrada",Toast.LENGTH_SHORT).show();
            return;
        }
        addProductFragment.getImage(view);

    }*/

    public void saveProductOnDatabase(Product product, ArrayList<byte[]> images) {
        String tipo;
        String imageUrl;
        String randomId = UUID.randomUUID().toString();
        Map<String, String> timestamp = TIMESTAMP;

        if (product.getClass() == Book.class) {
            tipo = "books";
        }
        else if (product.getClass() == Broomstick.class) {
            tipo = "broomsticks";
        }
        else if (product.getClass() == Clothing.class) {
            tipo = "clothings";
        }
        else if (product.getClass() == Potion.class) {
            tipo = "potions";
        }
        else {
            Toast.makeText(this, "Erro: não foi possível salvar dados",Toast.LENGTH_LONG).show();
            return;
        }
        DatabaseReference nodeToAdd = databaseReference.child("products").child(randomId);
        StorageReference imageToAdd = storageReference.child("images").child(tipo).child(randomId);
        nodeToAdd.setValue(product);
        nodeToAdd.child("class").setValue(tipo);
        nodeToAdd.child("timestamp").setValue(timestamp);

        for (int i = 0; i < images.size(); i++) {
            imageToAdd.child(""+i).putBytes(images.get(i));
        }


        Toast.makeText(this, "Salvando dados...",Toast.LENGTH_LONG).show();

    }

    public void StartSearchFragment(View v) {
        fragmentChange("iSearchFragment",null);
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
