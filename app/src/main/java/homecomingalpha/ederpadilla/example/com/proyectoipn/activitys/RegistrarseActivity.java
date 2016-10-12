package homecomingalpha.ederpadilla.example.com.proyectoipn.activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.OnClick;
import homecomingalpha.ederpadilla.example.com.proyectoipn.R;
import homecomingalpha.ederpadilla.example.com.proyectoipn.util.Util;

public class RegistrarseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);
        ButterKnife.bind(this);
    }
    @OnClick(R.id.btn_entrar)
    public void entrar(){
        Intent intent = new Intent(RegistrarseActivity.this,
                PerfilActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(RegistrarseActivity.this,
                MainActivity.class);
        startActivity(intent);
        finish();
    }
}
