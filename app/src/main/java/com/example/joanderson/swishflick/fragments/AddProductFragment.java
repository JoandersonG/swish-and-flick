package com.example.joanderson.swishflick.fragments;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
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
import android.widget.ImageButton;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InvalidClassException;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddProductFragment extends Fragment {

    private StorageReference storageReference;
    private LinearLayout  linearLayoutOfPagesAndPublisher;
    private TextInputLayout textInputLayoutOfAuthor, textInputLayoutOfMaxSpeed,
            textInputLayoutOfSize, textInputLayoutOfColor, textInputLayoutOfMlQuantity,
            textInputLayoutOfEffects, textInputLayoutOfPriceKnut, textInputLayoutOfPriceSickle,
            textInputLayoutOfPriceGalleon;
    private Spinner spinnerCategory;
    private EditText title, description, pages, publisher, author, maxSpeed, size, color,
            mlQuantity, effects, galleon, sickle, knut, stock;
    //todo: resolver isso (tirar static)
    private static ImageView imageProduct1, imageProduct2, imageProduct3;
    private static ImageButton ibCloseImage1, ibCloseImage2, ibCloseImage3;
    private FragmentComunicator fragmentComunicator;
    private static String[] imagesUri;
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
        imagesUri = new String[3];
        loadSpinnerValues();
        loadLayoutSpinnerBased("Book");

        imageProduct1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageProduct1.setTag(true);
                pickImage(1);
            }
        });
        ibCloseImage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageProduct1.setSelected(false);
                imageProduct1.setImageResource(R.drawable.produto_sem_imagem);
                ibCloseImage1.setVisibility(ImageButton.GONE);

            }
        });
        imageProduct2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageProduct2.setTag(true);
                pickImage(2);

            }
        });
        ibCloseImage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageProduct2.setSelected(false);
                imageProduct2.setImageResource(R.drawable.produto_sem_imagem);
                ibCloseImage2.setVisibility(ImageButton.GONE);
            }
        });
        imageProduct3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageProduct3.setTag(true);
                pickImage(3);
            }
        });
        ibCloseImage3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageProduct3.setSelected(false);
                imageProduct3.setImageResource(R.drawable.produto_sem_imagem);
                ibCloseImage3.setVisibility(ImageButton.GONE);

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
    public Product getProduct () {
        String category = spinnerCategory.getSelectedItem().toString();
        Product product = null;
        int validation = 0;
        try {
            switch (category) {
                case "Book":
                    return new Book(
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

                case "Broomstick":
                    return new Broomstick(
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

                case "Clothing":

                    return new Clothing(
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

                case "Potion":
                    ArrayList<String> effectList = new ArrayList<>();
                    effectList.add(effects.getText().toString());

                    return new Potion(
                            title.getText().toString(),
                            description.getText().toString(),
                            new Cash(Integer.parseInt(galleon.getText().toString()),
                                    Integer.parseInt(sickle.getText().toString()),
                                    Integer.parseInt(knut.getText().toString())),
                            10,
                            Integer.parseInt(mlQuantity.getText().toString()),
                            effectList

                    );

                    //todo: efeitos corretamente

                default:
                    System.out.println("categoria inexistente: " + category);
                    throw new InvalidClassException(category);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean testValidation (View view) {
        //todo: implementar stock amount
        String category = spinnerCategory.getSelectedItem().toString();
        Product product = null;
        int validation = 0;


    //todo agora: colocar x nas imagens selecionadas para o produto

        if (galleon.getText().toString().isEmpty()) galleon.setText("0");
        if (sickle.getText().toString().isEmpty()) sickle.setText("0");
        if (knut.getText().toString().isEmpty()) knut.setText("0");
        if (stock.getText().toString().isEmpty()) stock.setText("0");

        //testar estoque:
        if (Integer.parseInt(stock.getText().toString()) < 0) {
            validation = R.string.validation_error_stock;
            printErrorMessage(getString(validation));
            return false;
        }

        try {
            //testa imagens: precisa de ao menos uma
            if (!imageProduct1.isSelected() && !imageProduct2.isSelected() && !imageProduct3.isSelected()) {
                validation = R.string.validation_error_images;
            }
            else {
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
                        //todo: efeitos corretamente
                        break;
                    default:
                        System.out.println("categoria inexistente: " + category);
                        throw new InvalidClassException(category);
                }
            }
            //validation = ProductValidation.validateProduct(product);
            if (validation == 0) {
                //tudo certo
                return true;
            } else {
                printErrorMessage(getString(validation));
            }
        }
        catch (NullPointerException e) {

            e.printStackTrace();
        }
        catch (Exception e) {
            //todo: especificar mais os exceptions possíveis;
            printErrorMessage("Erro desconhecido");
            e.printStackTrace();
        }
        return false;
    }

    private void printErrorMessage(String mensagem) {
        Toast.makeText(getContext() , mensagem, Toast.LENGTH_SHORT).show();
    }

    private void loadLayoutSpinnerBased(String spinner) {

        ibCloseImage1.setVisibility(ImageButton.GONE);
        ibCloseImage2.setVisibility(ImageButton.GONE);
        ibCloseImage3.setVisibility(ImageButton.GONE);

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
        imageProduct1 = view.findViewById(R.id.ivAddProductFragment1);
        imageProduct2 = view.findViewById(R.id.ivAddProductFragment2);
        imageProduct3 = view.findViewById(R.id.ivAddProductFragment3);
        ibCloseImage1 = view.findViewById(R.id.ibCloseImage1);
        ibCloseImage2 = view.findViewById(R.id.ibCloseImage2);
        ibCloseImage3 = view.findViewById(R.id.ibCloseImage3);
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
        stock = view.findViewById(R.id.etFragAddProductStock);
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
        imageProduct1.setTag(false);
        imageProduct1.setSelected(false);
        imageProduct2.setTag(false);
        imageProduct2.setSelected(false);
        imageProduct3.setTag(false);
        imageProduct3.setSelected(false);
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

    public ArrayList<byte[]> getImageByte() {

        ArrayList<byte[]> images = new ArrayList<>();

        //testar se imagem é "vazia"
        Bitmap bitmap;
        ByteArrayOutputStream baos;
        if (imageProduct1.isSelected()) {
            bitmap = ((BitmapDrawable) imageProduct1.getDrawable()).getBitmap();
            baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            images.add(baos.toByteArray());
        }
        if (imageProduct2.isSelected()) {
            bitmap = ((BitmapDrawable) imageProduct2.getDrawable()).getBitmap();
            baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            images.add(baos.toByteArray());
        }
        if (imageProduct3.isSelected()) {
            bitmap = ((BitmapDrawable) imageProduct3.getDrawable()).getBitmap();
            baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            images.add(baos.toByteArray());
        }

        return images;
    }

    public static void activityResultImagePick(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            //recover image
            Uri imagePicked = data.getData();
            String imagePath = imagePicked.toString();//todo: salvar URI para melhorar desempenho?
            System.out.println("RequestCode: " + requestCode);
            System.out.println("ResultCode: " + resultCode);
            if (imageProduct1.getTag().equals(true)) {
                //imagesUri[0] = imagePath;
                imageProduct1.setImageURI(imagePicked);
                imageProduct1.setTag(false);
                imageProduct1.setSelected(true);
                ibCloseImage1.setVisibility(ImageButton.VISIBLE);
                //imageProduct1.setEnabled(true);
            }

            else if (imageProduct2.getTag().equals(true)) {
                //imagesUri[1] = imagePath;
                imageProduct2.setImageURI(imagePicked);
                imageProduct2.setTag(false);
                imageProduct2.setSelected(true);
                ibCloseImage2.setVisibility(ImageButton.VISIBLE);
            }
            else {
                //imagesUri[2] = imagePath;
                imageProduct3.setImageURI(imagePicked);
                imageProduct3.setTag(false);
                imageProduct3.setSelected(true);
                ibCloseImage3.setVisibility(ImageButton.VISIBLE);
            }
        }
    }

//    public static String[] getImagesUri() {
//        return imagesUri;
//    }
}
