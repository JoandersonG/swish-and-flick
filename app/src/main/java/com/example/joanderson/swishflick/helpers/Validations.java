package com.example.joanderson.swishflick.helpers;

import com.example.joanderson.swishflick.R;
import com.example.joanderson.swishflick.models.Cash;
import com.example.joanderson.swishflick.models.product.Book;

public abstract class Validations {

    public static int validateBook (Book book) {
        //int result = validate(book.getName());
        if (result != 0) return result;
        if (book.getName().isEmpty()) {
            return R.string.validation_error_empty_title;
        }
        if (book.getDescription().isEmpty()) {
            //return false;
        }
        if (book.getPagesAmount()  >= 0) {
            //return false;
        }
        return 0;
    }

    public static Object validateBook(String name, String description, Cash price, int stockAmount,
                                      int pagesAmount, String author, String publisher) {
        if (name.isEmpty()) {
            return R.string.validation_error_empty_title;
        }

        return 0;
    }

}
