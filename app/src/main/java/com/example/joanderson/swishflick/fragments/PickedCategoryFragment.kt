package com.example.joanderson.swishflick.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.joanderson.swishflick.R
import com.example.joanderson.swishflick.adapters.ProductsAdapter
import com.example.joanderson.swishflick.interfaces.FragmentComunicator
import com.example.joanderson.swishflick.models.Cash
import com.example.joanderson.swishflick.models.Category
import com.example.joanderson.swishflick.models.product.*
import com.google.firebase.database.*
//import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_category_picked.*

class PickedCategoryFragment : Fragment() {

    lateinit var tvTitle : TextView
    lateinit var tvDescription : TextView
    lateinit var recyclerView : RecyclerView
    var databaseReference = FirebaseDatabase.getInstance().getReference()
    val products: ArrayList<Product> = ArrayList()//podem existir elementos null aqui dentro

    lateinit var category : Category
//        get() = field
//        set(value) {
//            field = value
//        }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //return super.onCreateView(inflater, container, savedInstanceState)
        //View view = inflater.inflate(R.layout.fragment_categories, container, false);
        //
        //        rvCategories = view.findViewById(R.id.fragmentCategoriesRecyclerView);
        val view = inflater.inflate(R.layout.fragment_category_picked,container,false);

        val productAdapter : ProductsAdapter = ProductsAdapter(activity as FragmentComunicator, products)

        recyclerView = view.findViewById(R.id.rvCategoryPicked)

        tvTitle = view.findViewById(R.id.tvTitleFragmentCategoryPicked)

        //------------------------------------------getting products from database:
        val valueEventListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
            override fun onDataChange(p0: DataSnapshot) {
                p0.children.forEach {
                    println("entrou aqui")
                    if (it.child("class").value.toString().toUpperCase().equals(category.title.toUpperCase())) {
                        println("e aqui também")
                        //salvo esse produto
                        val product : Product?
                        when (category.title) {
                            "books" -> product = it.getValue(Book::class.java)
                            "clothing" -> product = it.getValue(Clothing::class.java)
                            "jewlry" -> product = it.getValue(Jewelry::class.java)
                            "muggle_artifacts" -> product = it.getValue(Artifact::class.java)
                            "potion" -> product = it.getValue(Potion::class.java)
                            //"quidditch" -> product = it.getValue(Artifact::class.java)
                            "wizzard_artifacts" -> product = it.getValue(Artifact::class.java)
                            else -> product = Book("erro"," description ", Cash(0,0,0),0,10,"author","publisher")
                        }
                        if (product != null)   {
                            product.image = it.key
                            products.add(product) //salvando no vetor de produtos
                            productAdapter.notifyDataSetChanged();
                        }
                    }
                    else {
                        println("mas aqui não, ou seja: " + it.child("class").value.toString().toUpperCase() + " é diferente de " + category.title.toUpperCase())
                    }
                }
            }
        }
        databaseReference.child("products").orderByKey().addValueEventListener(valueEventListener)

        //------------------------------------------endGetFromDatabase

        val layoutManager : RecyclerView.LayoutManager = GridLayoutManager(context,2)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = productAdapter

        //tvTitle.setText()//todo agora:findviewbyid em kotlin
        //tvTitle.setText(category.title)
        //tvTitle.setText(R.string.)
        val stringResource : String// = category.title
        when (category.title) {
            "books" -> stringResource = getString(R.string.booksPT)
            "clothing" -> stringResource = getString(R.string.clothingsPT)
            "hogwarts" -> stringResource = "Hogwarts"
            "jewlry" -> stringResource = getString(R.string.jewlryPT)
            "muggle_artifacts" -> stringResource = getString(R.string.muggle_artifactsPT)
            "potion" -> stringResource = getString(R.string.potionsPT)
            "quidditch" -> stringResource = getString(R.string.quidditchPT)
            "wizzard_artifacts" -> stringResource = getString(R.string.wizzard_artifactsPT)
            else -> stringResource = category.title
        }
        tvTitle.setText(stringResource)
        return view
    }
}