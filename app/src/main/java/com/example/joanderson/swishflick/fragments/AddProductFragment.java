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
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.example.joanderson.swishflick.R;
import com.example.joanderson.swishflick.helpers.Permissions;
import com.example.joanderson.swishflick.interfaces.FragmentComunicator;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddProductFragment extends Fragment {

    FragmentComunicator comunicator;
    private ImageView imageProduct;
    private FragmentComunicator fragmentComunicator;
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

        fragmentComunicator = (FragmentComunicator) getActivity();
        imageProduct = view.findViewById(R.id.ivAddProductFragment);
        comunicator = (FragmentComunicator) getActivity();
        if (getArguments() != null) {
            //todo: continuar daqui
        }
        imageProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImage(1);
            }
        });

        //validate permissions:
        Permissions.validatePermissions(permissions,getActivity(),1);

        return view;
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {

            //recover image
            Uri imagePicked = data.getData();
            String imagePath = imagePicked.toString();
            imageProduct.setImageURI(imagePicked);
        }
    }


}
