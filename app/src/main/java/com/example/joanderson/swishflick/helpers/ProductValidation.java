package com.example.joanderson.swishflick.helpers;

import android.content.Context;

import com.example.joanderson.swishflick.R;
import com.example.joanderson.swishflick.models.Cash;
import com.example.joanderson.swishflick.models.product.Artifact;
import com.example.joanderson.swishflick.models.product.Book;
import com.example.joanderson.swishflick.models.product.Broomstick;
import com.example.joanderson.swishflick.models.product.Clothing;
import com.example.joanderson.swishflick.models.product.Jewelry;
import com.example.joanderson.swishflick.models.product.Potion;
import com.example.joanderson.swishflick.models.product.Product;

import java.io.InvalidClassException;
import java.util.ArrayList;

public abstract class ProductValidation {


    private static boolean validateTitle(String title) {
        if (title.matches("[0-9]*")) {
            return false;
        }
        return true;
    }

    private static boolean validateDescription(String description) {
        if(description.matches("[0-9]*")) {
            return false;
        }
        return true;
    }

    private static boolean validatePages(int pages) {
        if(pages > 100000 || pages <= 0) {
            return false;
        }
        return true;
    }
    private static boolean validatePages(String pages) {
        if (pages.isEmpty()) {
            return false;
        }
        return validatePages(Integer.parseInt(pages));
    }

    private static boolean validatePublisher(String publisher) {
        if(publisher.matches("")) {
            return false;
        }
        return true;
    }

    private static boolean validateAuthor(String author) {
        if (author.isEmpty()) {
            return false;
        }
        if(author.matches(".*[0-9].*")) {
            return false;
        }
        return true;
    }

    private static boolean validateCurrency(int value) {
        if (value < 0) {
            return false;
        }
        return true;
    }

    private static boolean validateMaxSpeed(int maxSpeed) {
        if (maxSpeed <= 0) {
            return false;
        }
        return true;
    }

    private static boolean validateMaxSpeed(String maxSpeed) {
        if (maxSpeed.isEmpty()) {
            return false;
        }
        return validateMaxSpeed(Integer.parseInt(maxSpeed));
    }

    private static boolean validateSize(String size) {
        //todo: turn size into a Enum
        if (size.isEmpty()) {
            return false;
        }
        return true;
    }

    private static boolean validateAmount(int amount) {
        if (amount <= 0) {
            return false;
        }
        return true;
    }

    private static boolean validateAmount(String amount) {
        if (amount.isEmpty()) {
            return false;
        }
        return validateAmount(Integer.parseInt(amount));
    }

    private static boolean validateEffects(String effects) {
        //todo: turn effects into a list banck and front-end
        if (effects.isEmpty()) {
            return false;
        }
        return true;
    }

    private static boolean validateEffects(ArrayList<String> effects) {
        for (String e : effects) {
            if (!validateEffects(e)) return false;
        }
        return true;
    }

    //todo agora: public static boolean validateImages()

    public static int validateProduct(Product product) throws InvalidClassException {
        if (!validateTitle(product.getName())) {
            //erro no título
//            Toast toast = Toast.makeText(context,"Insira um título válido",Toast.LENGTH_LONG);
//            toast.show();
            //errorMessage = "Insira um título válido";
            return R.string.validation_error_title;
        }
        if (!validateDescription(product.getDescription())) {
            return R.string.validation_error_description;
        }
        if (!validateCurrency(product.getPrice().getGalleon())
                || !validateCurrency(product.getPrice().getSickle())
                || !validateCurrency(product.getPrice().getKnut())) {
            return R.string.validation_error_price;
        }


        int errorMessage = 0;
        if (product.getClass() == Book.class) {
            errorMessage = validateBook((Book) product);
        }
        else if (product.getClass() == Broomstick.class) {
            errorMessage = validateBroomstick((Broomstick) product);
        }
        else if (product.getClass() == Clothing.class) {
            errorMessage = validateClothing((Clothing) product);
        }
        else if (product.getClass() == Potion.class) {
            errorMessage = validatePotion((Potion) product);
        }
        else if (product.getClass() == Jewelry.class) {
            errorMessage = validateJewelry((Jewelry) product);
        }
        else if (product.getClass() == Artifact.class) {
            errorMessage = validateArtifact((Artifact) product);
        }
        else throw new InvalidClassException(product.getClass().toString());//todo: isso funciona?


        //todo: implement magic attributes
        return errorMessage;
    }

    //tests if all the fields for a bok are correct, if so, sends 0, else, sends the code to the error message

    private static int validateBook(Book book) {
        //String errorMessage;
        if (!validateAuthor(book.getAuthor())) return R.string.validation_error_author;
        if (!validatePublisher(book.getPublisher())) return R.string.validation_error_publisher;
        if (!validatePages(book.getPagesAmount())) return R.string.validation_error_pages;
        return 0;
        //todo: implement stock amout on product insertion
    }
    public static int validateBookFields(String title, String description, String pages, String author, String publisher, String galleons, String sickles, String knuts) {
        if (!validateTitle(title)) return R.string.validation_error_title;
        if (!validateDescription(description)) return R.string.validation_error_description;
        if (!validatePages(pages)) return R.string.validation_error_pages;
        if (!validatePublisher(publisher)) return R.string.validation_error_publisher;
        if (!validateAuthor(author)) return R.string.validation_error_author;
        int test = validateCash(galleons,sickles,knuts);
        if (test != 0) return test;
        return 0;
    }

    public static int validateBroomstickFields(String title, String description, String maxSpeed, String size, String galleons, String sickles, String knuts) {
        if (!validateTitle(title)) return R.string.validation_error_title;
        if (!validateDescription(description)) return R.string.validation_error_description;
        if (!validateMaxSpeed(maxSpeed)) return R.string.validation_error_max_speed;
        if (!validateSize(size)) return R.string.validation_error_size;
        int test = validateCash(galleons,sickles,knuts);
        if (test != 0) return test;
        return 0;
    }

    public static int validateClothingFields(String title, String description, String size, String galleons, String sickles, String knuts) {
        if (!validateTitle(title)) return R.string.validation_error_title;
        if (!validateDescription(description)) return R.string.validation_error_description;
        if (!validateSize(size)) return R.string.validation_error_size;
        int test = validateCash(galleons,sickles,knuts);
        if (test != 0) return test;
        return 0;
    }

    public static int validatePotionFields(String title, String description, String amount, ArrayList<String> effects, String galleons, String sickles, String knuts) {
        if (!validateTitle(title)) return R.string.validation_error_title;
        if (!validateDescription(description)) return R.string.validation_error_description;
        if (!validateAmount(amount)) return R.string.validation_error_amount;
        if (!validateEffects(effects)) return R.string.validation_error_effects;
        int test = validateCash(galleons,sickles,knuts);
        if (test != 0) return test;
        return 0;
    }

    public static int validateCash (int galleons, int sickles, int knuts) {

        if (galleons < 0 || sickles < 0 || knuts < 0) {
            return R.string.validation_error_price;
        }
        return 0;
    }

    public static int validateCash (String galleons, String sickles, String knuts) {
        if (galleons.isEmpty() && sickles.isEmpty() && knuts.isEmpty()) {
            return R.string.validation_error_price;
        }
        int gal,sic,knu;
        if (galleons.isEmpty()) gal = 0; else gal = Integer.parseInt(galleons);
        if (sickles.isEmpty()) sic = 0; else sic = Integer.parseInt(sickles);
        if (knuts.isEmpty()) knu = 0; else knu = Integer.parseInt(knuts);
        return validateCash(gal, sic, knu);
    }

    private static int validateBroomstick(Broomstick broomstick) {
        if (!validateMaxSpeed(broomstick.getMaxSpeed())) return R.string.validation_error_max_speed;
        //todo: if (!validateSize(broomstick.getSize())) return R.string.validation_error_size;
        return 0;
    }

    private static int validateClothing(Clothing clothing) {
        if (!validateSize(clothing.getSize())) return R.string.validation_error_size;
        return 0;
    }

    private static int validatePotion(Potion potion) {
        if (!validateAmount(potion.getMlQuantity())) return R.string.validation_error_amount;
        //todo: if (!validateEffects(potion.getEffects())) return R.string.validation_error_effects;
        return 0;
    }

    private static int validateJewelry(Jewelry jewelry) {
        return 0;
    }

    private static int validateArtifact(Artifact artifact) {
        return 0;
    }

    //todo: public static boolean validateImage()

}
