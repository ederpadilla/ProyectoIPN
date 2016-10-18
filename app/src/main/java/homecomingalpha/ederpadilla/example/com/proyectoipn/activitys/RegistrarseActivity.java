package homecomingalpha.ederpadilla.example.com.proyectoipn.activitys;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.mikhaellopez.circularimageview.CircularImageView;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);
        ButterKnife.bind(this);
        spinner.setVisibility(View.GONE);
        checkForUserLogIn();
    }

    private void checkForUserLogIn() {
        SharedPreferences sharedPreferences =this.getSharedPreferences(Constantes.LLAVE_LOGIN,0);
        if (sharedPreferences.contains(Constantes.LLAVE_NOMBRE)){
            et_nombre.setText(Util.getSharerPreferencesUserName(getApplicationContext()));
            et_telefono.setText(Util.getSharerPreferencesUsePhone(getApplicationContext()));
            et_mail.setText(Util.getSharerPreferencesUserMail(getApplicationContext()));
            et_password.setText(Util.getSharerPreferencesUserPass(getApplicationContext()));
            btn_crearcuenta.setText(getString(R.string.actualizar));
        }else{
            spinner.setVisibility(View.VISIBLE);
            spinnerAdapter();
        }

    }

    @OnClick(R.id.btn_crearcuenta)
    public void entrar(){ SharedPreferences sharedPreferences =this.getSharedPreferences(Constantes.LLAVE_LOGIN,0);
        if (sharedPreferences.contains(Constantes.LLAVE_NOMBRE)){

        }else{
            validateEmptyFields();
        }

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
                        PerfilActivity.class);
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
        Intent intent = new Intent(RegistrarseActivity.this,
                MainActivity.class);
        startActivity(intent);
        finish();
    }
    private void createUser(User user){
    Realm realm =Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(user);
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
}
