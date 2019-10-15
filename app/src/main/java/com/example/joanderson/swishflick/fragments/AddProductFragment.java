package com.example.joanderson.swishflick.fragments;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.joanderson.swishflick.R;
import com.example.joanderson.swishflick.helpers.Permissions;
import com.example.joanderson.swishflick.helpers.ProductValidation;
import com.example.joanderson.swishflick.interfaces.FragmentComunicator;
import com.example.joanderson.swishflick.models.Cash;
import com.example.joanderson.swishflick.models.product.Book;
import com.example.joanderson.swishflick.models.product.Broomstick;
import com.example.joanderson.swishflick.models.product.Clothing;
import com.example.joanderson.swishflick.models.product.Potion;
import com.example.joanderson.swishflick.models.product.Product;

import java.io.InvalidClassException;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddProductFragment extends Fragment {

    private LinearLayout  linearLayoutOfPagesAndPublisher;
    private TextInputLayout textInputLayoutOfAuthor, textInputLayoutOfMaxSpeed,
            textInputLayoutOfSize, textInputLayoutOfColor, textInputLayoutOfMlQuantity,
            textInputLayoutOfEffects, textInputLayoutOfPriceKnut, textInputLayoutOfPriceSickle,
            textInputLayoutOfPriceGalleon;
    private Spinner spinnerCategory;
    private EditText title, description, pages, publisher, author, maxSpeed, size, color,
            mlQuantity, effects, galleon, sickle, knut;
    private static ImageView imageProduct;
    private FragmentComunicator fragmentComunicator;
    private String[] spinnerCategories;
    private String[] permissions = new String[] {
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    public AddProductFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_product, container, false);

        loadViewIds(view);
        loadSpinnerValues();
        loadLayoutSpinnerBased("Book");
        imageProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImage(1);
            }
        });

        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String current = getResources().getStringArray(R.array.categories)[position];
                loadLayoutSpinnerBased(current);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                spinnerCategory.setSelection(0);
            }
        });
        spinnerCategory.setSelection(0);

        //validate permissions:
        Permissions.validatePermissions(permissions,getActivity(),1);

        return view;
    }


    public void testValidation (View view) {
        //todo: implementar stock amount
        String category = spinnerCategory.getSelectedItem().toString();
        Product product = null;
        int validation = 0;

        if (galleon.getText().toString().isEmpty()) galleon.setText("0");
        if (sickle.getText().toString().isEmpty()) sickle.setText("0");
        if (knut.getText().toString().isEmpty()) knut.setText("0");

        try {
            switch (category) {
                case "Book":
                    //String name, String description, Cash price, int stockAmount,
                    //                int pagesAmount, String author, String publisher

                    validation = ProductValidation.validateBookFields(
                            title.getText().toString(),
                            description.getText().toString(),
                            pages.getText().toString(),
                            author.getText().toString(),
                            publisher.getText().toString(),
                            galleon.getText().toString(),
                            sickle.getText().toString(),
                            knut.getText().toString()
                    );
                    if (validation == 0) {//all fields are correct
                        //coloco no banco
                        product = new Book(
                                title.getText().toString(),
                                description.getText().toString(),
                                new Cash(Integer.parseInt(galleon.getText().toString()),
                                        Integer.parseInt(sickle.getText().toString()),
                                        Integer.parseInt(knut.getText().toString())),
                                10,
                                Integer.parseInt(pages.getText().toString()),
                                author.getText().toString(),
                                publisher.getText().toString()

                        );
                        System.out.println("tudo certo com livro" + title.getText().toString());
                    }
                    else {
                        Toast toast = Toast.makeText(getContext(), getString(validation), Toast.LENGTH_LONG);
                        toast.show();
                    }


                    break;
                case "Broomstick":
                    validation = ProductValidation.validateBroomstickFields(
                            title.getText().toString(),
                            description.getText().toString(),
                            maxSpeed.getText().toString(),
                            size.getText().toString(),
                            galleon.getText().toString(),
                            sickle.getText().toString(),
                            knut.getText().toString()
                    );

                    if (validation == 0) {
                        product = new Broomstick(
                                title.getText().toString(),
                                description.getText().toString(),
                                new Cash(Integer.parseInt(galleon.getText().toString()),
                                        Integer.parseInt(sickle.getText().toString()),
                                        Integer.parseInt(knut.getText().toString())),
                                10,
                                true,
                                Integer.parseInt(maxSpeed.getText().toString())
                        );
                        //envia pro banco
                    }
                    else {
                        Toast toast = Toast.makeText(getContext(), getString(validation), Toast.LENGTH_LONG);
                        toast.show();
                    }

                    break;
                case "Clothing":
                    validation = ProductValidation.validateClothingFields(
                            title.getText().toString(),
                            description.getText().toString(),
                            size.getText().toString(),
                            galleon.getText().toString(),
                            sickle.getText().toString(),
                            knut.getText().toString()
                    );

                    if (validation == 0) {

                        product = new Clothing(
                                title.getText().toString(),
                                description.getText().toString(),
                                new Cash(Integer.parseInt(galleon.getText().toString()),
                                        Integer.parseInt(sickle.getText().toString()),
                                        Integer.parseInt(knut.getText().toString())),
                                10,
                                false,
                                "P",
                                "Blue"
                        );

                    }

                    break;
                case "Potion":
                    ArrayList<String> effectList = new ArrayList<>();
                    effectList.add(effects.getText().toString());
                    validation = ProductValidation.validatePotionFields(
                            title.getText().toString(),
                            description.getText().toString(),
                            mlQuantity.getText().toString(),
                            effectList,
                            galleon.getText().toString(),
                            sickle.getText().toString(),
                            knut.getText().toString()
                    );

                    if (validation == 0) {

                        product = new Potion(
                                title.getText().toString(),
                                description.getText().toString(),
                                new Cash(Integer.parseInt(galleon.getText().toString()),
                                        Integer.parseInt(sickle.getText().toString()),
                                        Integer.parseInt(knut.getText().toString())),
                                10,
                                Integer.parseInt(mlQuantity.getText().toString()),
                                effectList

                        );

                    }

                    //todo: efeitos corretamente

                    break;
                default:
                    System.out.println("categoria inexistente: " + category);
                    throw new InvalidClassException(category);
            }
            //validation = ProductValidation.validateProduct(product);
            if (validation == 0) {
                //tudo certo
            } else {
                Toast toast = Toast.makeText(getContext(), getString(validation), Toast.LENGTH_LONG);
                toast.show();
            }
        }
        catch (NullPointerException e) {

            e.printStackTrace();
        }
        catch (Exception e) {
            //todo: especificar mais os exceptions possíveis;
            Toast toast = Toast.makeText(getContext(), getText(R.string.error_unknown), Toast.LENGTH_LONG);
            toast.show();
            e.printStackTrace();
        }
    }

    private void printErrorMessage(String mensagem) {
        Toast.makeText(getContext() , mensagem, Toast.LENGTH_SHORT).show();
    }

    private void loadLayoutSpinnerBased(String spinner) {

        if (spinner.equals("Broomstick")) {
            maxSpeed.setVisibility(EditText.VISIBLE);
            textInputLayoutOfMaxSpeed.setVisibility(TextInputLayout.VISIBLE);
        }
        else {
            maxSpeed.setVisibility(EditText.GONE);
            textInputLayoutOfMaxSpeed.setVisibility(TextInputLayout.GONE);
        }
        if (spinner.equals("Clothing") || spinner.equals("Broomstick")) {
            size.setVisibility(EditText.VISIBLE);
            textInputLayoutOfSize.setVisibility(TextInputLayout.VISIBLE);
        }
        else {
            size.setVisibility(EditText.GONE);
            textInputLayoutOfSize.setVisibility(TextInputLayout.GONE);
        }
        if (spinner.equals("Clothing")) {
            color.setVisibility(EditText.VISIBLE);
            textInputLayoutOfColor.setVisibility(TextInputLayout.VISIBLE);
        }
        else {
            color.setVisibility(EditText.GONE);
            textInputLayoutOfColor.setVisibility(TextInputLayout.GONE);
        }
        if (spinner.equals("Potion")) {
            mlQuantity.setVisibility(EditText.VISIBLE);
            textInputLayoutOfMlQuantity.setVisibility(TextInputLayout.VISIBLE);
            effects.setVisibility(EditText.VISIBLE);
            textInputLayoutOfEffects.setVisibility(TextInputLayout.VISIBLE);
        }
        else {
            mlQuantity.setVisibility(EditText.GONE);
            textInputLayoutOfMlQuantity.setVisibility(TextInputLayout.GONE);
            effects.setVisibility(EditText.GONE);
            textInputLayoutOfEffects.setVisibility(TextInputLayout.GONE);
        }

        if (spinner.equals("Book")) {
            pages.setVisibility(EditText.VISIBLE);
            publisher.setVisibility(EditText.VISIBLE);
            author.setVisibility(EditText.VISIBLE);
            linearLayoutOfPagesAndPublisher.setVisibility(LinearLayout.VISIBLE);
            textInputLayoutOfAuthor.setVisibility(TextInputLayout.VISIBLE);
        }
        else {
            pages.setVisibility(EditText.GONE);
            publisher.setVisibility(EditText.GONE);
            author.setVisibility(EditText.GONE);
            linearLayoutOfPagesAndPublisher.setVisibility(LinearLayout.GONE);
            textInputLayoutOfAuthor.setVisibility(TextInputLayout.GONE);
        }
    }

    private void loadViewIds(View view) {
        fragmentComunicator = (FragmentComunicator) getActivity();
        imageProduct = view.findViewById(R.id.ivAddProductFragment);
        spinnerCategory = view.findViewById(R.id.spinnerFragAddProductCategory);
        title = view.findViewById(R.id.etFragAddProductTitle);
        description = view.findViewById(R.id.etFragAddProductDescription);
        pages = view.findViewById(R.id.etFragAddProductPages);
        publisher = view.findViewById(R.id.etFragAddProductPublisher);
        author = view.findViewById(R.id.etFragAddProductAuthor);
        maxSpeed = view.findViewById(R.id.etFragAddProductMaxSpeed);
        size = view.findViewById(R.id.etFragAddProductSize);
        color = view.findViewById(R.id.etFragAddProductColor);
        mlQuantity = view.findViewById(R.id.etFragAddProductMlQuantity);
        effects = view.findViewById(R.id.etFragAddProductEffects);
        galleon = view.findViewById(R.id.etFragAddPriceGalleon);
        sickle = view.findViewById(R.id.etFragAddPriceSickle);
        knut = view.findViewById(R.id.etFragAddPriceKnut);
        textInputLayoutOfPriceGalleon = view.findViewById(R.id.textInputLayoutOfPriceGalleon);
        textInputLayoutOfPriceKnut = view.findViewById(R.id.textInputLayoutOfPriceKnut);
        textInputLayoutOfPriceSickle = view.findViewById(R.id.textInputLayoutOfPriceSickle);
        textInputLayoutOfAuthor = view.findViewById(R.id.textInputLayoutOfAuthor);
        textInputLayoutOfEffects = view.findViewById(R.id.textInputLayoutOfEffects);
        textInputLayoutOfMlQuantity = view.findViewById(R.id.textInputLayoutOfMlQuantity);
        textInputLayoutOfColor = view.findViewById(R.id.textInputLayoutOfColor);
        textInputLayoutOfMaxSpeed = view.findViewById(R.id.textInputLayoutOfMaxSpeed);
        textInputLayoutOfSize = view.findViewById(R.id.textInputLayoutOfSize);
        linearLayoutOfPagesAndPublisher = view.findViewById(R.id.linearLayoutOfPagesAndPublisher);
        spinnerCategories = getResources().getStringArray(R.array.categories);
        imageProduct.setTag(false);
    }

    private void loadSpinnerValues() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getContext(), android.R.layout.simple_spinner_item, spinnerCategories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
         spinnerCategory.setAdapter(adapter);

    }

    public void pickImage(int requestCode) {
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, requestCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        for (int permissionResult : grantResults) {
            if (permissionResult == PackageManager.PERMISSION_DENIED) {
                alertPermissionValidation();
            }
        }
    }

    private void alertPermissionValidation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Permissões negadas");
        builder.setMessage("Para adicionar foto, é necessário aceitar permissão");
        builder.setCancelable(false);
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                fragmentComunicator.fragmentChange("fAddProductFragment",null);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public static void activityResultImagePick(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            //recover image
            Uri imagePicked = data.getData();
            String imagePath = imagePicked.toString();
            imageProduct.setImageURI(imagePicked);
            imageProduct.setTag(true);
        }
    }


}
