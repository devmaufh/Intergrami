package com.example.mauri.intergrami.Activities;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mauri.intergrami.R;
import com.example.mauri.intergrami.Utils.ValidaCampos;

import de.hdodenhof.circleimageview.CircleImageView;
import pub.devrel.easypermissions.EasyPermissions;
import studio.carbonylgroup.textfieldboxes.ExtendedEditText;
import studio.carbonylgroup.textfieldboxes.SimpleTextChangedWatcher;
import studio.carbonylgroup.textfieldboxes.TextFieldBoxes;

public class Register extends AppCompatActivity {
    Button btnRegistro,btnAddPhoto;
    TextFieldBoxes tfbCurp, tfbNombre,tfbDireccion,tfbTelefono,tfbCorreo,tfbContraseña,tfbValidaContraseña;
    ExtendedEditText edCurp,edNombre,edDireccion,edTelefono,edCorreo,edContraseña,edValidaContraseña;
    int flagPassword2=0,flagPassword=0;
    //For image:
    private static int RESULT_LOAD_IMG = 1;
    String imgDecodableString;
    //
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setToolbar();
        bindUI();
        TextBoxesEvents();
        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(samePasswords("Nombre1","Nombre1")){
                    Snackbar.make(view,"Iguales",Snackbar.LENGTH_SHORT).show();
                }
                }
        });
        btnAddPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //loadImagefromGallery();
                permisos();
            }
        });
    }
    private void setToolbar(){
        Toolbar toolbar= (Toolbar)findViewById(R.id.registro_toolbar); //Muestra el toolbar como ActionBar
        setSupportActionBar(toolbar);//Muestra titulo de toolbar
        toolbar.setTitle("Intergrami");

    }
    private void bindUI(){
        tfbNombre=(TextFieldBoxes)findViewById(R.id.registro_textfieldboxes_nombre);
        edNombre=(ExtendedEditText)findViewById(R.id.registro_textfieldboxes_nombre_edittext);
        tfbCurp=(TextFieldBoxes)findViewById(R.id.registro_textfieldboxes_curp);
        edCurp=(ExtendedEditText)findViewById(R.id.registro_textfieldboxes_curp_edittext);
        tfbDireccion=(TextFieldBoxes)findViewById(R.id.registro_textfieldboxes_direccion);
        edDireccion=(ExtendedEditText)findViewById(R.id.registro_textfieldboxes_curp_edDireccion);
        tfbTelefono=(TextFieldBoxes)findViewById(R.id.registro_textfieldboxes_telefono);
        edTelefono=(ExtendedEditText)findViewById(R.id.registro_textfieldboxes_nombre_edittext);
        tfbCorreo=(TextFieldBoxes)findViewById(R.id.registro_textfieldboxes_correo);
        edCorreo=(ExtendedEditText)findViewById(R.id.registro_textfieldboxes_curp_edCorreo);
        tfbContraseña=(TextFieldBoxes)findViewById(R.id.registro_textfieldboxes_password);
        edContraseña=(ExtendedEditText)findViewById(R.id.registro_textfieldboxes_curp_edPassword);
        tfbValidaContraseña=(TextFieldBoxes)findViewById(R.id.registro_textfieldboxes_valida_password);
        edValidaContraseña=(ExtendedEditText)findViewById(R.id.registro_textfieldboxes_curp_edValidaPassword);
        btnRegistro=(Button)findViewById(R.id.registro_btnRegistrar);
        btnAddPhoto=(Button)findViewById(R.id.registro_btnAddPhoto);
    }
    private void TextBoxesEvents(){
        tfbCurp.setSimpleTextChangeWatcher(new SimpleTextChangedWatcher() {
            @Override
            public void onTextChanged(String s, boolean b) {
                if(edCurp.getText().toString().length()>18)
                    tfbCurp.setError(getResources().getString(R.string.registro_helper_curp),true);
            }
        });
        tfbNombre.setSimpleTextChangeWatcher(new SimpleTextChangedWatcher() {
            @Override
            public void onTextChanged(String s, boolean b) {
                if(edNombre.getText().toString().length()>50)
                    tfbNombre.setError(getResources().getString(R.string.registro_valida_nombre),true);
            }
        });
        tfbDireccion.setSimpleTextChangeWatcher(new SimpleTextChangedWatcher() {
            @Override
            public void onTextChanged(String s, boolean b) {
                if(edDireccion.getText().toString().length()>50)
                    tfbDireccion.setError(getResources().getString(R.string.registro_valida_nombre),true);
            }
        });
        tfbTelefono.setSimpleTextChangeWatcher(new SimpleTextChangedWatcher() {
            @Override
            public void onTextChanged(String s, boolean b) {
                if(edTelefono.getText().toString().length()>10)
                    tfbTelefono.setError(getResources().getString(R.string.registro_valida_nombre),true);
            }
        });
        tfbCorreo.setSimpleTextChangeWatcher(new SimpleTextChangedWatcher() {
            @Override
            public void onTextChanged(String s, boolean b) {
                if(edCorreo.getText().toString().length()>40)
                    tfbCorreo.setError(getResources().getString(R.string.valida_correo),true);
            }
        });
        tfbContraseña.setSimpleTextChangeWatcher(new SimpleTextChangedWatcher() {
            @Override
            public void onTextChanged(String s, boolean b) {
                if(edContraseña.getText().toString().length()>40||
                        edContraseña.getText().toString().length()<4)
                    tfbContraseña.setError(getResources().getString(R.string.contraseña_invalida),true);
            }
        });
        tfbContraseña.getEndIconImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flagPassword++;
                if(flagPassword%2==0)
                    edContraseña.setTransformationMethod(PasswordTransformationMethod.getInstance());//edContraseña.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                else
                    edContraseña.setTransformationMethod(HideReturnsTransformationMethod.getInstance());//edContraseña.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                edContraseña.setSelection(edContraseña.getText().length());
            }
        });
        tfbValidaContraseña.setSimpleTextChangeWatcher(new SimpleTextChangedWatcher() {
            @Override
            public void onTextChanged(String s, boolean b) {
                if(edValidaContraseña.getText().toString().length()>40||
                        edValidaContraseña.getText().toString().length()<4)
                    tfbValidaContraseña.setError(getResources().getString(R.string.contraseña_invalida),true);
            }
        });
        tfbValidaContraseña.getEndIconImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flagPassword2++;
                if(flagPassword%2==0)
                    edValidaContraseña.setTransformationMethod(PasswordTransformationMethod.getInstance());//edContraseña.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                else
                    edValidaContraseña.setTransformationMethod(HideReturnsTransformationMethod.getInstance());//edContraseña.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                edValidaContraseña.setSelection(edValidaContraseña.getText().length());
            }
        });
    }
    private boolean samePasswords(String pass1,String pass2){
        return pass1.equals(pass2);
    }
    public void loadImagefromGallery() {
        // Create intent to Open Image applications like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                    && null != data) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgDecodableString = cursor.getString(columnIndex);
                cursor.close();
                CircleImageView imgView = (CircleImageView) findViewById(R.id.registro_fotoperfil);
                imgView.setImageBitmap(BitmapFactory
                        .decodeFile(imgDecodableString));

            } else {
                Toast.makeText(this, getResources().getString(R.string.nombre),//Modificar String
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this,getResources().getString(R.string.nombre), Toast.LENGTH_LONG)//Modificar string
                    .show();
        }
    }
    private String[] galleryPermissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private void permisos(){
        if (EasyPermissions.hasPermissions(this, galleryPermissions)) {
            loadImagefromGallery();
        } else {
            EasyPermissions.requestPermissions(this, "Access for storage",
                    101, galleryPermissions);
        }
    }

}
