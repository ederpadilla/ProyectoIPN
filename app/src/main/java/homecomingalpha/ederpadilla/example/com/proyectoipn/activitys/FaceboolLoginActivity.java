package homecomingalpha.ederpadilla.example.com.proyectoipn.activitys;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
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
import com.google.gson.Gson;

import org.json.JSONObject;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebool_login);
        ButterKnife.bind(this);
        callbackManager= CallbackManager.Factory.create();
        facebook();
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
                if (buscarUsuario()==null){
                    Util.showToast(getApplicationContext(),getString(R.string.user_not_found));
                }else {
                    if (buscarUsuario().getContraseña().equals(et_password.getText().toString())){
                        Util.saveSharedPreferences(getApplicationContext(), buscarUsuario());
                        Intent intent = new Intent(FaceboolLoginActivity.this,
                                PerfilActivity.class);
                        intent.putExtra(Constantes.LLAVE_USUARIO_ID, buscarUsuario().getId());
                        startActivity(intent);
                        finish();}else {
                        Util.showToast(getApplicationContext(),"Contraseña incorrecta");}
                }
            }
        }


    }
    private User buscarUsuario() {
        User userFound = realm.where(User.class).equalTo(
                Constantes.LLAVE_LOGIN_MAIL,et_mail.getText().toString()).findFirst();
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
        Intent intent = new Intent(FaceboolLoginActivity.this,RegistrarseActivity.class);
        startActivity(intent);
        finish();
    }
}
