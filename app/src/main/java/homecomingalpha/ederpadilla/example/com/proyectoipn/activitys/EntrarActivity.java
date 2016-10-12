package homecomingalpha.ederpadilla.example.com.proyectoipn.activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import homecomingalpha.ederpadilla.example.com.proyectoipn.R;
import homecomingalpha.ederpadilla.example.com.proyectoipn.util.Util;

public class EntrarActivity extends AppCompatActivity {
    @BindView(R.id.login_et_mail)
    EditText et_mail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrar);
        ButterKnife.bind(this);
    }
    @OnClick(R.id.btn_login)
    public void entrar(){
        //validarCamposVacios();
        Intent intent = new Intent(EntrarActivity.this,
                PerfilActivity.class);
        startActivity(intent);
        finish();
    }

    private void validarCamposVacios() {
        if (Util.emptyField(et_mail)==0){
            Log.e("Hola LALO"," tu campo esta vacio");
        }
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
