package homecomingalpha.ederpadilla.example.com.proyectoipn.activitys;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
    CircularImageView img_photo;
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
    private int tipoDeUsuario=3;
    private String userType="";
    private Realm realm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);
        ButterKnife.bind(this);
        spinner.setVisibility(View.GONE);
        realm=Realm.getDefaultInstance();
        checkForUserLogIn();
    }

    private void checkForUserLogIn() {
        SharedPreferences sharedPreferences =this.getSharedPreferences(Constantes.LLAVE_LOGIN,0);
        if (sharedPreferences.contains(Constantes.LLAVE_NOMBRE)){
            editarPerfil(buscarUsuario());
            btn_crearcuenta.setText(getString(R.string.actualizar));
            if (buscarUsuario().getTipoDeUuario()==3){
                spinner.setVisibility(View.VISIBLE);
                spinnerAdapter();
            }

        }else{
            spinner.setVisibility(View.VISIBLE);
            spinnerAdapter();
        }

    }

    @OnClick(R.id.btn_crearcuenta)
    public void entrar(){
        SharedPreferences sharedPreferences =this.getSharedPreferences(Constantes.LLAVE_LOGIN,0);
        if (sharedPreferences.contains(Constantes.LLAVE_NOMBRE)){
            actualizarUsuario(buscarUsuario());
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
        et_password.setText(user.getContraseña());
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
                createUser(usuarioConParametros());
                Intent intent = new Intent(RegistrarseActivity.this,
                        EntrarActivity.class);
                startActivity(intent);
                finish();
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
    private void createUser(User user){
    Realm realm =Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(user);
        realm.commitTransaction();
    }
    private void actualizarUsuario(User usuarioActualizado){
        usuarioActualizado=realm.where(User.class)
                .equalTo(Constantes.LLAVE_USUARIO_ID,buscarUsuario().getId())
                .findFirst();
        String nuevoNombre =et_nombre.getText().toString();
        String nuevoTelefono=et_telefono.getText().toString();
        String nuevoMail=et_mail.getText().toString();
        String nuevaContraseña=et_password.getText().toString();
        realm.beginTransaction();
        usuarioActualizado.setNombre(nuevoNombre);
        usuarioActualizado.setTelefono(nuevoTelefono);
        usuarioActualizado.setEmail(nuevoMail);
        usuarioActualizado.setContraseña(nuevaContraseña);
        usuarioActualizado.setTipoDeUuario(tipoDeUsuario);
        realm.copyToRealmOrUpdate(usuarioActualizado);
        realm.commitTransaction();
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
    private User buscarUsuario() {
        User userFound = realm.where(User.class).
                equalTo(Constantes.LLAVE_USUARIO_ID,Util.getSharerPreferencesUserId(getApplicationContext()))
                .findFirst();
        return userFound;
    }
    @OnClick(R.id.img_photo)
    public void takePic(){
        if (requestPermission()) {
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            startActivityForResult(intent, Constantes.REQUEST_IMAGE_CAPTURE);
        }
    }

    private boolean requestPermission() {
        int locationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        int cameraPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);

        List<String> listPermissionsNeeded = new ArrayList<>();
        if (locationPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }

        if (cameraPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), Constantes.REQUEST_PERMMISSION_STATUS);
            return false;
        }
        return true;
    }

    private void checkTogoBack(){
        SharedPreferences sharedPreferences =this.getSharedPreferences(Constantes.LLAVE_LOGIN,0);
        if (sharedPreferences.contains(Constantes.LLAVE_NOMBRE)){
            Intent intent = new Intent(RegistrarseActivity.this,
                    PerfilActivity.class);
            startActivity(intent);
            finish();

        }else{
            Intent intent = new Intent(RegistrarseActivity.this,
                    FaceboolLoginActivity.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        switch (requestCode) {
            case Constantes.REQUEST_PERMMISSION_STATUS: {

                Map<String, Integer> perms = new HashMap<>();
                // Initialize the map with both permissions

                perms.put(Manifest.permission.CAMERA, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.ACCESS_FINE_LOCATION, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.RECORD_AUDIO, PackageManager.PERMISSION_GRANTED);

                // Fill with actual results from user
                if (grantResults.length > 0) {
                    for (int i = 0; i < permissions.length; i++)
                        perms.put(permissions[i], grantResults[i]);
                    // Check for all permissions
                    if (perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {

                        // process the normal flow
                        //else any one or both the permissions are not granted
                    } else {

                        //permission is denied (this is the first time, when "never ask again" is not checked) so ask again explaining the usage of permission
                        //show the dialog or snackbar saying its necessary and try again otherwise proceed with setup.
                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECORD_AUDIO)
                                || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)
                                || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                            // request again
                            requestPermission();
                        } else {
                            //permission is denied (and never ask again is  checked)
                            //shouldShowRequestPermissionRationale will return false
                            Util.showToast(getApplicationContext(),"Permisos negados");
                            //proceed with denied access app.
                        }
                        // shouldShowRequestPermissionRationale will return true
                        return;
                    }
                }
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constantes.REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            img_photo.setImageBitmap(imageBitmap);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
            byte[] bitmapdata = bos.toByteArray();
            ByteArrayInputStream bs = new ByteArrayInputStream(bitmapdata);


        }
    }
}
