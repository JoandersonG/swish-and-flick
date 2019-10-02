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
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
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
import com.example.joanderson.swishflick.helpers.Validations;
import com.example.joanderson.swishflick.interfaces.FragmentComunicator;
import com.example.joanderson.swishflick.models.Cash;
import com.example.joanderson.swishflick.models.product.Book;

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
                String current = getResources().getStringArray(R.array.categoriesEN)[position];
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

    public void validateData (View view) {
        //todo: implementar stock amount
        String category = spinnerCategory.getSelectedItem().toString();
        switch (category) {
            case "Book":
                //String name, String description, Cash price, int stockAmount,
                //                int pagesAmount, String author, String publisher
                Book book = new Book(
                        title.getText().toString(),
                        description.getText().toString(),
                        new Cash(Integer.getInteger(galleon.getText().toString()),
                                Integer.getInteger(sickle.getText().toString()),
                                        Integer.getInteger(knut.getText().toString())),
                        10,
                        Integer.getInteger(pages.getText().toString()),
                        author.getText().toString(),
                        publisher.getText().toString()

                );
                int resultMessage = Validations.validateBook(book);
                if (resultMessage != 0) {
                    printErrorMessage(getString(resultMessage));
                }
                else {
                    //tudo certo, posso enviar para o banco
                }
        }
        /////////////////////////////////////////////////////
        if ( !(boolean) imageProduct.getTag() ) {
            printErrorMessage(getString(R.string.validation_error_empty_image));
        }
        if (title.getText().toString().isEmpty()) {
            printErrorMessage(getString(R.string.validation_error_empty_title));
        }
        if (description.getText().toString().isEmpty()) {
            printErrorMessage(getString(R.string.validation_error_empty_description));
        }
        if (galleon.getText().toString().isEmpty()
                && sickle.getText().toString().isEmpty()
                && knut.getText().toString().isEmpty()) {
            printErrorMessage(getString(R.string.validation_error_empty_price));
        }
        //todo: try and catch for this(?):
        if (Integer.getInteger(galleon.getText().toString()) < 0
                || Integer.getInteger(sickle.getText().toString()) < 0
                || Integer.getInteger(knut.getText().toString()) < 0) {
            printErrorMessage(getString(R.string.validation_error_invalid_price));
        }

        //agora vêm as especificidades

        switch (category) {
            // Integer.getInteger(pages.getText().toString()),
            //                        author.getText().toString(),
            //                        publisher.getText().toString()
            case "Book":
                if (pages.getText().toString().isEmpty() || Integer.getInteger(pages.getText().toString())  <= 0) {
                    printErrorMessage(getString(R.string.validation_error_empty_pages));
                }
                break;

            case "Broomstick":
                //todo: continuar daqui
        }


        String title = this.title.getText().toString();
        String description = this.description.toString();
        int galleon = Integer.valueOf(this.galleon.toString());
        int sickle = Integer.valueOf(this.sickle.toString());
        int knut = Integer.valueOf(this.knut.toString());

        if ( (boolean) imageProduct.getTag() ) {
            if( !title.isEmpty()) {
                if( !description.isEmpty()) {
                    if(galleon != 0 || sickle != 0 || knut != 0) {
                        //agora vem as validações específicas
                        if (category.equals("Book")) {
                            String pages = this.pages.toString();
                            String publisher = this.publisher.toString();
                            String author = this.author.toString();

                            if (!pages.isEmpty()) {
                                if (!publisher.isEmpty()) {
                                    if (!author.isEmpty()) {
                                        //tudo certo, envia para a nuvem
                                    }
                                    else {
                                        printErrorMessage("Por favor, insira o autor");
                                    }
                                }
                                else {
                                    printErrorMessage("Por favor, insira a editora");
                                }
                            }
                            else {
                                printErrorMessage("Por favor, insira o número de páginas");
                            }
                        }
                        //TODO: continuar as validações aqui
                    }
                    else {
                        printErrorMessage("Por favor, insira o preço");
                    }
                }
                else {
                    printErrorMessage("Por favor, insira a descrição");
                }
            }
            else {
                printErrorMessage("Por favor, insira o título");
            }
        }
        else {
            printErrorMessage("Por Favor, insira a imagem");
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
