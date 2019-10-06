package com.example.joanderson.swishflick.models.product

import com.example.joanderson.swishflick.models.Cash

import java.util.ArrayList


class Jewelry  : Product {


    constructor(name: String, description: String, price: Cash, stockAmount: Int) : super(name, description, price, stockAmount) {

    }

    constructor(name: String, description: String, price: Cash, stockAmount: Int, magicProperties: ArrayList<String>) : super(name, description, price, stockAmount, magicProperties) {

    }

    constructor(name: String, description: String, price: Cash, stockAmount: Int, isMagicProduct: Boolean?) : super(name, description, price, stockAmount, isMagicProduct!!) {

    }

    override fun equals(other: Any?): Boolean {
        return super.equals(other) && other.javaClass == this.javaClass
    }
}
