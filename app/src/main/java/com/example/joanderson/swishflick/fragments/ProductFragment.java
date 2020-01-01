package com.example.joanderson.swishflick.fragments;


import android.content.Context;
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
import com.example.joanderson.swishflick.models.product.Artifact;
import com.example.joanderson.swishflick.models.product.Book;
import com.example.joanderson.swishflick.models.product.Clothing;
import com.example.joanderson.swishflick.models.product.Jewelry;
import com.example.joanderson.swishflick.models.product.Potion;
import com.example.joanderson.swishflick.models.product.Product;
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

    Product product = null;
    String storageReferenceChild;
    //String imagesPath =null;
    Context context;
    TextView title, description;
    CarouselView carouselView;
    Button btSize;
    ImageView imageProduct;
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();

    private int[] productImages = {R.drawable.produto_sem_imagem, R.drawable.produto_sem_imagem, R.drawable.produto_sem_imagem};

    public ProductFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product, container, false);

        btSize = view.findViewById(R.id.btFragmentProductSize);
        title = view.findViewById(R.id.tvTitleFragmentProduct);
        description = view.findViewById(R.id.tvDescriptionProductFragment);
        //carouselView = view.findViewById(R.id.cvProductImagesFragmentProduct);
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

        carouselView = (CarouselView) view.findViewById(R.id.cvProductImagesFragmentProduct);
        carouselView.setPageCount(totalImages);
        carouselView.setImageListener(imageListener);


        if (product != null) {

            title.setText(product.getName());
            description.setText(product.getDescription());


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

            //final long TEN_MEGABYTES = 1024 * 1024 * 10;
//            storageReference.child("images").child(storageReferenceChild).child(product.getImage()).child("0").getBytes(TEN_MEGABYTES).addOnSuccessListener(new OnSuccessListener<byte[]>() {
//                @Override
//                public void onSuccess(byte[] bytes) {
//    //                p0.imageView.setImageBitmap(categories.get(p1).imageBitmap);
//    //                category.setImageBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
//    //                System.out.println("imagem salva!");
//    //                adapter.notifyDataSetChanged();
//
//                    myViewHolder.ivAdapterProduct.setImageBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    System.out.println("Erro: imagem não carregada");
//                }
//            });*/
//                storageReference.child("images").child()



            //todo: adicionar informações de estoque
        }


        return view;
    }

    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, final ImageView imageView) {

            final long TEN_MEGABYTES = 10 * 1024 * 1024;

            storageReference.child("images").child(storageReferenceChild).child(product.getImage()).child(String.valueOf(position)).getBytes(TEN_MEGABYTES).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    //                p0.imageView.setImageBitmap(categories.get(p1).imageBitmap);
                    //                category.setImageBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
                    //                System.out.println("imagem salva!");
                    //                adapter.notifyDataSetChanged();
                    imageView.setImageBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
                    //myViewHolder.ivAdapterProduct.setImageBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(context,"Erro: imagem não carregada",Toast.LENGTH_SHORT);
                    System.out.println("Erro: imagem não carregada");
                }
            });

            //imageView.setImageResource(productImages[position]);
        }
    };

    public void showSizeMenu(View view) {
        //todo: mudar isso para spinner
        PopupMenu popupMenu = new PopupMenu(context, view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.popup_size_menu,popupMenu.getMenu());
        popupMenu.show();
    }

    public void setProduct(Product product) {
        this.product = product;

    }

}
