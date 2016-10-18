package homecomingalpha.ederpadilla.example.com.proyectoipn.activitys;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import homecomingalpha.ederpadilla.example.com.proyectoipn.R;
import homecomingalpha.ederpadilla.example.com.proyectoipn.models.User;
import homecomingalpha.ederpadilla.example.com.proyectoipn.util.Constantes;
import homecomingalpha.ederpadilla.example.com.proyectoipn.util.Util;
import io.realm.Realm;

public class EntrarActivity extends AppCompatActivity {
    @BindView(R.id.login_et_mail)
    TextInputEditText et_mail;
    @BindView(R.id.login_et_password)
    TextInputEditText et_password;
    Realm realm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrar);
        ButterKnife.bind(this);
        realm=Realm.getDefaultInstance();
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
                Util.saveSharedPreferences(getApplicationContext(),buscarUsuario());
                Intent intent = new Intent(EntrarActivity.this,
                        PerfilActivity.class);
                intent.putExtra(Constantes.LLAVE_USUARIO_ID,buscarUsuario().getId());
                startActivity(intent);
                finish();
            }
        }
    }

    private User buscarUsuario() {
        User userFound = realm.where(User.class).equalTo(Constantes.LLAVE_LOGIN_MAIL,et_mail.getText().toString()).findFirst();
       return userFound;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(EntrarActivity.this,
                MainActivity.class);
        startActivity(intent);
        finish();
    }
}
