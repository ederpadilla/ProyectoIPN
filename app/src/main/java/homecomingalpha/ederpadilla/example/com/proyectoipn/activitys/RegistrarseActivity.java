package homecomingalpha.ederpadilla.example.com.proyectoipn.activitys;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import homecomingalpha.ederpadilla.example.com.proyectoipn.R;
import homecomingalpha.ederpadilla.example.com.proyectoipn.models.User;
import homecomingalpha.ederpadilla.example.com.proyectoipn.util.Constantes;
import homecomingalpha.ederpadilla.example.com.proyectoipn.util.Util;
import io.realm.Realm;

public class RegistrarseActivity extends AppCompatActivity {
    @BindView(R.id.img_photo)
    public CircularImageView img_photo;
    @BindView(R.id.register_et_nombre)
    TextInputEditText et_nombre;
    @BindView(R.id.register_et_telefono)
    TextInputEditText et_telefono;
    @BindView(R.id.register_et_mail)
    TextInputEditText et_mail;
    @BindView(R.id.register_et_password)
    TextInputEditText et_password;
    @BindView(R.id.spinner)
    MaterialSpinner spinner;
    @BindView(R.id.btn_crearcuenta)
    Button btn_crearcuenta;
    private FirebaseAuth mAuth;
    private DatabaseReference mFirebaseDatabase;
    private int tipoDeUsuario=3;
    private String userType="";
    private Realm realm;
    User userRegistrate;
    int PERMISSION_ALL = 1;
    private List<String> permissions= new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);
        ButterKnife.bind(this);
        spinner.setVisibility(View.GONE);
        mFirebaseDatabase =FirebaseDatabase.getInstance().getReference();
        realm=Realm.getDefaultInstance();
        checkForUserLogIn();
    }



    private void checkForUserLogIn() {
        SharedPreferences sharedPreferences =this.getSharedPreferences(Constantes.LLAVE_LOGIN,0);
        if (sharedPreferences.contains(Constantes.LLAVE_NOMBRE)){
           // editarPerfil(buscarUsuario());
           // btn_crearcuenta.setText(getString(R.string.actualizar));
           // if (buscarUsuario().getTipoDeUuario()==3){
           //     spinner.setVisibility(View.VISIBLE);
           //     spinnerAdapter();
           //     mAuth = FirebaseAuth.getInstance();
           // }

        }else{

            spinner.setVisibility(View.VISIBLE);
            spinnerAdapter();
            mAuth = FirebaseAuth.getInstance();
        }

    }

    @OnClick(R.id.btn_crearcuenta)
    public void entrar(){
        SharedPreferences sharedPreferences =this.getSharedPreferences(Constantes.LLAVE_LOGIN,0);
        if (sharedPreferences.contains(Constantes.LLAVE_NOMBRE)){

            Intent intent = new Intent(RegistrarseActivity.this,
                    PerfilActivity.class);
            startActivity(intent);
            finish();

        }else{
            validateEmptyFields();
        }

    }

    private void editarPerfil(User user) {
        et_nombre.setText(user.getNombre());
        et_mail.setText(user.getEmail());
        et_telefono.setText(user.getTelefono());
    }

    private void validateEmptyFields() {
        if (Util.emptyField(et_nombre)==0&&Util.emptyField(et_mail)==0&&Util.emptyField(et_password)==0
               && Util.emptyField(et_telefono)==0){
            Util.showToast(getApplicationContext(),getString(R.string.camposvacios));
        }else if(Util.emptyField(et_nombre)==0){
        et_nombre.setError(getString(R.string.campo_vacio));
        }else if(Util.emptyField(et_mail)==0){
        et_mail.setError(getString(R.string.campo_vacio));
        }else if(Util.emptyField(et_password)==0){
        et_password.setError(getString(R.string.campo_vacio));
        }else if(Util.emptyField(et_telefono)==0){
        et_telefono.setError(getString(R.string.campo_vacio));
        }else if (tipoDeUsuario==3) {
            Util.showToast(getApplicationContext(),"Falta especificar el tipo de usuario");
        }else {
            if (Util.isValidEmail(et_mail.getText().toString())!=1||Util.isValidPhoneNumber(et_telefono.getText().toString())!=1 ){
                if (Util.isValidEmail(et_mail.getText().toString())!=1){
                    Util.showToast(getApplicationContext(),getString(R.string.email_invalid));
                }else if (Util.isValidPhoneNumber(et_telefono.getText().toString())!=1){
                    Util.showToast(getApplicationContext(),getString(R.string.telefono_invalido));
                }

            }else {
                mAuth.createUserWithEmailAndPassword(et_mail.getText().toString(), et_password.getText().toString())
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                
                                //mFirebaseDatabase.child(userRegistrate.getId()).setValue(userRegistrate);
                                //writeNewUser(userRegistrate.getId(),userRegistrate);
                                //mDatabase.child("users").child(userId).setValue(user);
                                //Util.saveSharedPreferences(getApplicationContext(),userRegistrate);


                                if (!task.isSuccessful()) {
                                   Util.showToast(getApplicationContext(),"Estamos teniendo problemas intente mas tarde");
                                }else{
                                    Util.showToast(getApplicationContext(),"Se registro correctamente");
                                    userRegistrate=usuarioConParametros();
                                    Intent intent = new Intent(RegistrarseActivity.this,
                                            FaceboolLoginActivity.class);
                                    intent.putExtra(Constantes.LLAVE_USUARIO_ID, userRegistrate.getId());
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });

            }

        }
    }

    private void spinnerAdapter() {
        final String profesor=getString(R.string.profesor);
        final String padre=getString(R.string.padre);
        final String madre=getString(R.string.madre);

        spinner.setItems(getString(R.string.tipo_de_usuario),madre,padre,profesor);
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {

                if (item.equals(profesor)) {
                Util.showLog("El item es profesor");
                    tipoDeUsuario=Constantes.USUARIO_PROFESOR;
                }else if (item.equals(padre)){
                    tipoDeUsuario=Constantes.USUARIO_PADRE_MADRE;
                }else if (item.equals(madre)){
                    tipoDeUsuario=Constantes.USUARIO_PADRE_MADRE;
                }else{
                    tipoDeUsuario=3;
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        checkTogoBack();
    }


    private User usuarioConParametros(){
        String id = Util.randomInt(0,1000)+"";
        String nombre=et_nombre.getText().toString();
        String telefono= et_telefono.getText().toString();
        String email=et_mail.getText().toString();
        String pass=et_password.getText().toString();
        User user = new User(nombre,telefono,email,pass,tipoDeUsuario,id);
        Util.showLog("EL usuario que se crea "+user.toString());
        return user;
    }

    @OnClick(R.id.img_photo)
    public void cameraPic(){
        if (Util.hasPermissions(getApplicationContext(),permissions.toArray(new String[permissions.size()]))){
            selectImage();
        }else{
            Util.showLog("No tiene permisos");
            String[] PERMISSIONS = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
            if(!Util.hasPermissions(this, PERMISSIONS)){
                ActivityCompat.requestPermissions(RegistrarseActivity.this, PERMISSIONS, PERMISSION_ALL);
            }
        }
    }

    private void checkTogoBack(){
        SharedPreferences sharedPreferences =this.getSharedPreferences(Constantes.LLAVE_LOGIN,0);
        if (sharedPreferences.contains(Constantes.LLAVE_NOMBRE)){
            Intent intent = new Intent(RegistrarseActivity.this,
                    PerfilActivity.class);
            startActivity(intent);
            //finish();

        }else{
            Intent intent = new Intent(RegistrarseActivity.this,
                    FaceboolLoginActivity.class);
            startActivity(intent);
            //finish();
        }
    }
    private void selectImage() {
        final CharSequence[] items = { "Cámara", "Galería"};

        AlertDialog.Builder builder = new AlertDialog.Builder(RegistrarseActivity.this);
        builder.setTitle("Obtener fotografía");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Cámara")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(android.os.Environment
                            .getExternalStorageDirectory(), "temp.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    Util.showLog("antes del start de camara");
                    startActivityForResult(intent, Constantes.REQUEST_CAMERA);
                    Util.showLog("despues del start de camara");
                } else if (items[item].equals("Galería")) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    Util.showLog("antes del start de galeria");
                    startActivityForResult(
                            Intent.createChooser(intent, "Select File"),
                            Constantes.SELECT_FILE);
                    Util.showLog("depsues del start de galeria");
                }
            }
        });
        builder.show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Util.showLog("Entra al activityresult");
        super.onActivityResult(requestCode, resultCode, data);
        Util.showLog("Entra despues del super");
            if (requestCode == Constantes.REQUEST_CAMERA) {
                if (resultCode==RESULT_OK){
                File f = new File(Environment.getExternalStorageDirectory()
                        .toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("temp.jpg")) {
                        f = temp;
                        break;
                    }
                }
                try {
                    Bitmap bm;
                    BitmapFactory.Options btmapOptions = new BitmapFactory.Options();

                    bm = BitmapFactory.decodeFile(f.getAbsolutePath(),
                            btmapOptions);

                    // bm = Bitmap.createScaledBitmap(bm, 70, 70, true);
                    img_photo.setImageBitmap(bm);

                    String path = android.os.Environment
                            .getExternalStorageDirectory()
                            + File.separator
                            + "Phoenix" + File.separator + "default";
                    f.delete();
                    OutputStream fOut = null;
                    File file = new File(path, String.valueOf(System
                            .currentTimeMillis()) + ".jpg");
                    try {
                        fOut = new FileOutputStream(file);
                        bm.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
                        fOut.flush();
                        fOut.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                }
            } else if (requestCode == Constantes.SELECT_FILE) {
                Uri selectedImageUri = data.getData();

                String tempPath = getPath(selectedImageUri, RegistrarseActivity.this);
                Bitmap bm;
                BitmapFactory.Options btmapOptions = new BitmapFactory.Options();
                bm = BitmapFactory.decodeFile(tempPath, btmapOptions);
                img_photo.setImageBitmap(bm);
            }


    }
    public String getPath(Uri uri, Activity activity) {
        String[] projection = { MediaStore.MediaColumns.DATA };
        Cursor cursor = activity
                .managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }


}
