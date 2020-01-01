package com.example.joanderson.swishflick.adapters

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.joanderson.swishflick.R
import com.example.joanderson.swishflick.interfaces.FragmentComunicator
import com.example.joanderson.swishflick.models.Category


class CategoriesAdapter(private val categories: ArrayList<Category>,private val fragmentComunicator: FragmentComunicator) : RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {

    //recebo um vetor de imagens


    //val linguagens = arrayListOf<String>("Java", "Kotlin")

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val item = LayoutInflater.from(p0.getContext())
                .inflate(R.layout.adapter_categories, p0, false)

        return ViewHolder(item)
    }

    override fun getItemCount(): Int {
        return categories.count()
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        //p0.imageView.setImageBitmap(BitmapFactory.decodeByteArray(images[p1],0,images[p1].size))
        //if (categories.get(p1).imageBitmap == null) println("" + p1 + " is null") else println("" + p1 + " not null")

        p0.imageView.setImageBitmap(categories.get(p1).imageBitmap);

        p0.imageView.setOnClickListener (object : View.OnClickListener{
            override fun onClick(p0: View?) {
                //inicia fragment com os dados da categoria atual;
                val bundle = Bundle()
                bundle.putParcelable("category",categories.get(p1))
                fragmentComunicator.fragmentChange("iCategoryPicked",bundle)
            }
        })

    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val imageView : ImageView = itemView.findViewById(R.id.ivAdapterCategories)
    }
}