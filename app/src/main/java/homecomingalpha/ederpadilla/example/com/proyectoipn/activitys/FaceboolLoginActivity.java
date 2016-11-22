package homecomingalpha.ederpadilla.example.com.proyectoipn.activitys;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import homecomingalpha.ederpadilla.example.com.proyectoipn.R;
import homecomingalpha.ederpadilla.example.com.proyectoipn.models.FacebookUserResponse;
import homecomingalpha.ederpadilla.example.com.proyectoipn.models.User;
import homecomingalpha.ederpadilla.example.com.proyectoipn.util.Constantes;
import homecomingalpha.ederpadilla.example.com.proyectoipn.util.Util;
import io.realm.Realm;

public class FaceboolLoginActivity extends AppCompatActivity {
    @BindView(R.id.login_et_mail)
    TextInputEditText et_mail;
    @BindView(R.id.login_et_password)
    TextInputEditText et_password;
    Realm realm;
    @BindView(R.id.fb_login_button)
    LoginButton loginButton;
    CallbackManager callbackManager;
    SharedPreferences sharedPreferences;
    int PERMISSION_ALL = 1;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebool_login);
        ButterKnife.bind(this);
        getPermition();
        sharedPreferences =this.getSharedPreferences(Constantes.LLAVE_LOGIN,0);
        checkForUserLogIn();
    }

    private void checkForUserLogIn(){
        mAuth = FirebaseAuth.getInstance();
        if (sharedPreferences.contains(Constantes.LLAVE_NOMBRE)){
            callbackManager= CallbackManager.Factory.create();
            facebook();
            Util.borrarSharedPreferences(getApplicationContext());
        }else {
            Intent intent = new Intent(FaceboolLoginActivity.this,SplashActivity.class);
            startActivity(intent);
            FaceboolLoginActivity.this.finish();
        }
    }

    private void facebook() {
        loginButton.setReadPermissions("public_profile","email");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Util.showLog("On succes!!!!!");
                AccessToken accessToken = loginResult.getAccessToken();
                GraphRequest graphRequest = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Gson gson = new Gson();
                        FacebookUserResponse facebookUserResponse =
                                gson.fromJson(object.toString(),FacebookUserResponse.class);
                         User usuario = new User(facebookUserResponse.getName(),facebookUserResponse.getId(),
                         facebookUserResponse.getPicture().getData().getUrl(),3);
                         createUser(usuario);
                         Intent intent = new Intent(FaceboolLoginActivity.this,PerfilActivity.class);
                         Util.saveSharedPreferences(getApplicationContext(),usuario);
                        startActivity(intent);
                        finish();
                    }
                });
                Bundle parametros = new Bundle();
                parametros.putString("fields","id,email,picture,name");
                graphRequest.setParameters(parametros);
                graphRequest.executeAsync();
            }

            @Override
            public void onCancel() {
                Util.showLog("Se Cancelo!!!!!");

            }

            @Override
            public void onError(FacebookException error) {
                Util.showLog("Bailo berta");

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
        Util.showLog("Entra a este result");
    }
    @OnClick(R.id.btn_login)
    public void entrar(){
        validarCamposVacios();
    }
    private void validarCamposVacios() {
        if (Util.emptyField(et_mail)==0&&Util.emptyField(et_password)==0){
            Util.showLog("Los dos");
            Util.showToast(getApplicationContext(),getString(R.string.camposvacios));
        }
        else if(Util.emptyField(et_mail)==0){
            Util.showLog("El mail");
            et_mail.setError(getString(R.string.campo_vacio));
        }
        else if(Util.emptyField(et_password)==0){
            Util.showLog("password");
            et_password.setError(getString(R.string.campo_vacio));
        }
        else {
            if (Util.isValidEmail(et_mail.getText().toString())!=1){
                Util.showToast(getApplicationContext(),getString(R.string.invalid_mail));
            }else{
                final String mail =et_mail.getText().toString();
                mAuth.signInWithEmailAndPassword(mail, et_password.getText().toString())
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (buscarUsuario(mail).equals(null)){
                                    Util.showLog("Usuario no esta en realm");
                                }
                                Util.saveSharedPreferences(getApplicationContext(),buscarUsuario(mail));
                                Intent intent = new Intent(FaceboolLoginActivity.this,
                                                              PerfilActivity.class);
                                intent.putExtra(Constantes.LLAVE_USUARIO_ID, buscarUsuario(mail).getId());
                                              startActivity(intent);
                                              finish();
                                if (!task.isSuccessful()) {
                                   Util.showToast(getApplicationContext(),"Usuario no registrado o contraseña incorrecta");
                                }

                                // ...
                            }
                        });
            }
          //      if (buscarUsuario()==null){
          //          Util.showToast(getApplicationContext(),getString(R.string.user_not_found));
          //      }else {
          //          if (buscarUsuario().getContraseña().equals(et_password.getText().toString())){
          //              Util.saveSharedPreferences(getApplicationContext(), buscarUsuario());
          //              Intent intent = new Intent(FaceboolLoginActivity.this,
          //                      PerfilActivity.class);
          //              intent.putExtra(Constantes.LLAVE_USUARIO_ID, buscarUsuario().getId());
          //              startActivity(intent);
          //              finish();}else {
          //              Util.showToast(getApplicationContext(),"Contraseña incorrecta");}
          //      }
          //  }
        }


    }
    private User buscarUsuario(String mail) {
        User userFound = realm.where(User.class).equalTo(
                Constantes.LLAVE_LOGIN_MAIL,mail).findFirst();
        Util.showLog("User found"+ userFound.toString());
        return userFound;
    }
    private void createUser(User user) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(user);
        realm.commitTransaction();
    }
    @OnClick(R.id.tv_registrarse)
    public void registrarse(){
        Intent intent = new Intent(FaceboolLoginActivity.this,RegisterActivity.class);
        startActivity(intent);
        finish();
    }

    public void getPermition() {
        List<String> permissions= new ArrayList<>();
        permissions.add(Manifest.permission.CAMERA);
        permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        if (hasPermissions(getApplicationContext(),permissions.toArray(new String[permissions.size()]))){
            Util.showLog("Tiene permisos");
        }else{
            Util.showLog("No tiene permisos");
            String[] PERMISSIONS = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
            if(!hasPermissions(this, PERMISSIONS)){
                ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
            }
        }
    }
    public static boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){

        }
    }
}
