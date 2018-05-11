package com.example.mauri.intergrami.Utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Toast;

import com.example.mauri.intergrami.R;

public class ValidaCampos {
    private static boolean isValidEmail(String email){//Valida el email
        return !TextUtils.isEmpty(email)&& Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    private static  boolean isValidPassword(String password){ //Valída la contraseña
        return password.length()>4;
    }
    public static boolean isValidData(String email, String password, Context context){
        if(!isValidEmail(email)){
            Toast.makeText(context,context.getResources().getString(R.string.correo_invalido),
                    Toast.LENGTH_SHORT).show();
            return false;
        }else if(!isValidPassword(password)){
            Toast.makeText(context,context.getResources().getString(R.string.contraseña_invalida),
                    Toast.LENGTH_SHORT).show();
            return false;
        }else{
            return true;
        }
    }
}
