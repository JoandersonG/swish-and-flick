package com.example.joanderson.swishflick.activites;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.joanderson.swishflick.R;
import com.example.joanderson.swishflick.models.product.Book;
import com.example.joanderson.swishflick.models.product.Product;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class AddProductsImageActivity extends AppCompatActivity {

    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    ImageView iv1, iv2, iv3, iv4, iv5;
    Button bt1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_products_image);
/*
        iv1 = findViewById(R.id.imageView6);
        iv2 = findViewById(R.id.imageView7);
        iv3 = findViewById(R.id.imageView8);
        iv4 = findViewById(R.id.imageView9);
        iv5 = findViewById(R.id.imageView10);
        bt1 = findViewById(R.id.buttonSend);
*///todo: resolver isso aqui
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv1.setDrawingCacheEnabled(true);
            }
        });

    }

    private void addValuesToDatabase() {








        //String imageId, String name, String description, Cash price, int stockAmount
        //Product produto1 = new Book("imageId","Os Contos de Beelde, o Bardo", "");

    }
}
