package com.example.joanderson.swishflick.fragments;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.joanderson.swishflick.R;
import com.example.joanderson.swishflick.adapters.CategoriesAdapter;
import com.example.joanderson.swishflick.interfaces.FragmentComunicator;
import com.example.joanderson.swishflick.models.Category;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoriesFragment extends Fragment {

    //ImageButton ibClothing, ibPotions, ibQuiddich, ibHogwarts, ibMuggleArtefatcs,
    //        ibmMagicArtefacts, ibBooks, ibJewls;
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private RecyclerView rvCategories;

    public CategoriesFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_categories, container, false);

        rvCategories = view.findViewById(R.id.fragmentCategoriesRecyclerView);
        /*
        ibClothing = view.findViewById(R.id.ibCategoriesClothing);
        ibBooks = view.findViewById(R.id.ibCategoriesBooks);
        ibHogwarts = view.findViewById(R.id.ibCategoriesHogwarts);
        ibJewls = view.findViewById(R.id.ibCategoriesJewls);
        ibmMagicArtefacts = view.findViewById(R.id.ibCategoriesMagicArtefacts);
        ibMuggleArtefatcs = view.findViewById(R.id.ibCategoriesMuggleArtefacts);
        ibPotions = view.findViewById(R.id.ibCategoriesPotions);
        ibQuiddich = view.findViewById(R.id.ibCategoriesQuiddich);
        */

        final ArrayList<String> imagesUrl = new ArrayList<>();
        final ArrayList<String> categoriesTitle = new ArrayList<>();
        final ArrayList<Bitmap> imagesBitmaps = new ArrayList<>();
        final ArrayList<Category> categories = new ArrayList<>();

        final CategoriesAdapter adapter = new CategoriesAdapter(categories, (FragmentComunicator)getActivity());

        ValueEventListener eventListener = new ValueEventListener() {

            final long TEN_MEGABYTES = 1024 * 1024 * 10;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    //System.out.println(ds.getKey());
                    //imagesUrl.add(ds.child("url").getValue(String.class));
                    //categoriesTitle.add(ds.child("title").getValue(String.class));

                        //final Category category = ds.getValue(Category.class);
                        String title ;//= ds.child("title").getValue(String.class);
                        title = ds.getKey();
                        String imageUrl = ds.child("url").getValue(String.class);
                        //System.out.println(ds.getKey());
                        final Category category = new Category(title, imageUrl, null);
                        //if (category == null) System.out.println("Erro: category null");
                        //recupero a imagem:
                        //category.getUrl()
                        //storageReference.child("categories").
                        StorageReference gsReference = FirebaseStorage.getInstance().getReferenceFromUrl(imageUrl);
                        gsReference.getBytes(TEN_MEGABYTES).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                            @Override
                            public void onSuccess(byte[] bytes) {
                                category.setImageBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
                                adapter.notifyDataSetChanged();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                System.out.println("falha em getBytes");
                            }
                        });

                        categories.add(category);
                        adapter.notifyDataSetChanged();
                }
                    /*
                    imagesUrl.add(ds.child("url").getValue(String.class));
                    categoriesTitle.add(ds.child("title").getValue(String.class));
                    */
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        databaseReference.child("categories").addListenerForSingleValueEvent(eventListener);


        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(),2);
        rvCategories.setLayoutManager(layoutManager);
        rvCategories.setHasFixedSize(true);
        rvCategories.setAdapter(adapter);












        /*System.out.println("categorias recuperadas (url e imagens):");
        for (int i = 0; i < categories.size(); i++) {
            //System.out.println("Url imagens da categoria "+ categoriesTitle.get(i) + ": " + imagesUrl.get(i));
            System.out.print("Categoria " + categories.get(i).getTitle() + " imagem salva: ");
            if (categories.get(i).getImageBitmap() != null) {
                System.out.println("Imagem ainda vazia");
            }
            else {
                System.out.println("imagem já salva");
            }
        }*/

        return view;
    }



/*
    public void openCategory(View view) {
        String operation;

        if (view.getId() == ibQuiddich.getId()) {
            Bundle bundle = new Bundle();
            bundle.putString("searchData","object seeMore here");
            ((FragmentComunicator) getActivity()).fragmentChange("iSeeMoreFragment",bundle);
        }
    }
*/
}
