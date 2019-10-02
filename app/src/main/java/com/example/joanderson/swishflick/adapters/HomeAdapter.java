package com.example.joanderson.swishflick.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.joanderson.swishflick.R;
import com.example.joanderson.swishflick.interfaces.FragmentComunicator;
import com.example.joanderson.swishflick.models.HomeFragmentItem;
import com.example.joanderson.swishflick.models.product.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {

    private Context context;
    private FragmentComunicator comunicator;
    private ArrayList<HomeFragmentItem> items;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    public HomeAdapter(Context context, FragmentComunicator comunicator, ArrayList<HomeFragmentItem> items) {
        this.context = context;
        this.comunicator = comunicator;
        this.items = items;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View item = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.adapter_home, viewGroup, false);

        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {
       /* HomeFragmentItem currentItem = items.get(i);
        myViewHolder.sessionTitle.setText(currentItem.getTitle());
        DatabaseReference books = databaseReference.child("products").child("books");
        Query product = databaseReference.child("products").orderByKey().
        for (String id : currentItem.getProductsId()){

        }

        myViewHolder.productName1.setText(produtcs[0].getName());

*/


        myViewHolder.cvProduct1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("searchData","object product here");
                comunicator.fragmentChange("iProductFragment", bundle);
            }
        });

        myViewHolder.cvProduct2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Entrou no produto 2");
            }
        });

        myViewHolder.cvProduct3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Entrou no produto 3");
            }
        });
        myViewHolder.sessionTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("searchData","object seeMore here");
                comunicator.fragmentChange("iSeeMoreFragment",bundle);
            }
        });


    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView productImage1, productImage2, productImage3;
        TextView productName1, productName2, productName3;
        TextView productPrice1, productPrice2, productPrice3;
        TextView sessionTitle;
        CardView cvProduct1, cvProduct2, cvProduct3;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            sessionTitle = itemView.findViewById(R.id.tvHomeTitle);
            productImage1 = itemView.findViewById(R.id.ivProduct1);
            productPrice1 = itemView.findViewById(R.id.tvPrice1);
            cvProduct1 = itemView.findViewById(R.id.cvAdapterHome1);

            productImage2 = itemView.findViewById(R.id.ivProduct2);
            productPrice2 = itemView.findViewById(R.id.tvPrice2);
            cvProduct2 = itemView.findViewById(R.id.cvAdapterHome2);

            productImage3 = itemView.findViewById(R.id.ivProduct3);
            productPrice3 = itemView.findViewById(R.id.tvPrice3);
            cvProduct3 = itemView.findViewById(R.id.cvAdapterHome3);

        }

    }

}
