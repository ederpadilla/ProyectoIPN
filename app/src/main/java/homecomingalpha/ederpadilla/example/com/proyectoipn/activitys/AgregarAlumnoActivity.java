package homecomingalpha.ederpadilla.example.com.proyectoipn.activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.ButterKnife;
import homecomingalpha.ederpadilla.example.com.proyectoipn.R;

public class AgregarAlumnoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_alumno);
        ButterKnife.bind(this);
    }
}
