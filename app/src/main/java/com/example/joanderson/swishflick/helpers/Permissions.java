package com.example.joanderson.swishflick.helpers;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class Permissions {

    public static boolean validatePermissions(String[] permissions, Activity activity, int requestCode){

        if (Build.VERSION.SDK_INT >= 17 ){

            List<String> listPermissions = new ArrayList<>();

            /*Percorre as permissões passadas,
            verificando uma a uma
            * se já tem a permissao liberada */
            for ( String permission : permissions ){
                Boolean hasPermission = ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED;
                if ( !hasPermission ) listPermissions.add(permission);
            }

            /*Caso a lista esteja vazia, não é necessário solicitar permissão*/
            if ( listPermissions.isEmpty() ) return true;
            String[] newPermissions = new String[ listPermissions.size() ];
            listPermissions.toArray( newPermissions );

            //Solicita permissão
            ActivityCompat.requestPermissions(activity, newPermissions, requestCode );


        }

        return true;

    }

}
