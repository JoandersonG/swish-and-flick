package com.example.joanderson.swishflick.fragments;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.joanderson.swishflick.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductFragment extends Fragment {

    String product;
    Context context;
    CarouselView carouselView;
    Button btSize;
    ImageView imageProduct;
    private int[] sampleImages = {R.drawable.produto_sem_imagem, R.drawable.produto_sem_imagem, R.drawable.produto_sem_imagem};

    public ProductFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product, container, false);

        btSize = view.findViewById(R.id.btFragmentProductSize);
        carouselView = view.findViewById(R.id.carouselViewProductImages);
        context = view.getContext();
        btSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSizeMenu(btSize);
            }
        });

        StorageReference gsReference = FirebaseStorage.getInstance().getReferenceFromUrl("gs://swish-and-flick.appspot.com/images/books/3ac01796-8880-40f1-bbb4-94888515420d").child("the_standard_book_of_spells.jpg");
        final long TEN_MEGABYTES = 10 * 1024 * 1024;
        gsReference.getBytes(TEN_MEGABYTES).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                //coloco na imagem
                //Bitmap bpm = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                //imageProduct.setImageBitmap(bpm);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast toast = Toast.makeText(getContext(),"Falha ao recuperar imagem", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        int totalImages = 3;// =

        carouselView = (CarouselView) view.findViewById(R.id.carouselViewProductImages);
        carouselView.setPageCount(totalImages);
        carouselView.setImageListener(imageListener);
        return view;
    }

    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(sampleImages[position]);
        }
    };

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
