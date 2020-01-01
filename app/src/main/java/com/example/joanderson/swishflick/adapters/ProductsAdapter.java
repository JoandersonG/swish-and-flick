package com.example.joanderson.swishflick.adapters;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.joanderson.swishflick.R;
import com.example.joanderson.swishflick.interfaces.FragmentComunicator;
import com.example.joanderson.swishflick.models.product.Artifact;
import com.example.joanderson.swishflick.models.product.Book;
import com.example.joanderson.swishflick.models.product.Clothing;
import com.example.joanderson.swishflick.models.product.Jewelry;
import com.example.joanderson.swishflick.models.product.Potion;
import com.example.joanderson.swishflick.models.product.Product;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.MyViewHolder> {

    private FragmentComunicator comunicator;
    private ArrayList<Product> products;
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();

    public ProductsAdapter(FragmentComunicator comunicator, ArrayList<Product> products) {
        this.comunicator = comunicator;
        this.products = products;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View item = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.adapter_products,viewGroup,false);

        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {
        //todo: if stock 0, then don't show
        String storageReferenceChild;
        final Product product = products.get(i);

        if (product.getClass() == Book.class) {
            storageReferenceChild = "books";
        }
        //todo: Broomstick
        else if (product.getClass() == Clothing.class) {
            storageReferenceChild = "clothings";
        }
        else if (product.getClass() == Potion.class) {
            storageReferenceChild = "potions";
        }
        else if (product.getClass() == Jewelry.class) {
            storageReferenceChild = "jewelrys";
        }
        else if (product.getClass() == Artifact.class) {
            storageReferenceChild = "artifacts";
        }
        else {
            //não encontrado
            storageReferenceChild = "";
        }

        final long TEN_MEGABYTES = 1024 * 1024 * 10;
        storageReference.child("images").child(storageReferenceChild).child(product.getImage()).child("0").getBytes(TEN_MEGABYTES).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
//                p0.imageView.setImageBitmap(categories.get(p1).imageBitmap);
//                category.setImageBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
//                System.out.println("imagem salva!");
//                adapter.notifyDataSetChanged();

                myViewHolder.ivAdapterProduct.setImageBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println("Erro: imagem não carregada");
            }
        });

        String price = product.getPrice().toString();
        myViewHolder.tvAdapterProductTitle.setText(product.getName());
        myViewHolder.tvAdapterProductPrice.setText(price);




        myViewHolder.ivAdapterProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //iniciar uma fragment
            Bundle bundle = new Bundle();
            bundle.putSerializable("product",product);
            //bundle.putString("searchData","object seeMore here");
            comunicator.fragmentChange("iProductFragment",bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        //System.out.println("product_size:" + products.size());
        return products.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView ivAdapterProduct;
        TextView tvAdapterProductPrice,tvAdapterProductTitle;
        Button btAdapterProductAddCart;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ivAdapterProduct = itemView.findViewById(R.id.ivAdapterProduct);
            tvAdapterProductPrice = itemView.findViewById(R.id.tvAdapterProductPrice);
            tvAdapterProductTitle = itemView.findViewById(R.id.tvAdapterProductTitle);
            btAdapterProductAddCart = itemView.findViewById(R.id.btAdapterProductAddCart);
        }
    }


}
