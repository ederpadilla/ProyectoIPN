package homecomingalpha.ederpadilla.example.com.proyectoipn.activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import homecomingalpha.ederpadilla.example.com.proyectoipn.R;
import homecomingalpha.ederpadilla.example.com.proyectoipn.util.Util;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btn_crearcuenta)
    Button cbtn_crearcuenta;
    @BindView(R.id.btn_entrar)
    Button btn_entrar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


    }

    @OnClick(R.id.btn_entrar)
    public void entrar(){
        Intent intent = new Intent(MainActivity.this,
                EntrarActivity.class);
        startActivity(intent);
        MainActivity.this.finish();
         }
    @OnClick(R.id.btn_crearcuenta)
    public void crearCuenta(){
        Intent intent = new Intent(MainActivity.this,
                RegistrarseActivity.class);
        startActivity(intent);
        MainActivity.this.finish();
        }
}
