package homecomingalpha.ederpadilla.example.com.proyectoipn.activitys;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.mikhaellopez.circularimageview.CircularImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import homecomingalpha.ederpadilla.example.com.proyectoipn.R;
import homecomingalpha.ederpadilla.example.com.proyectoipn.util.Constantes;
import homecomingalpha.ederpadilla.example.com.proyectoipn.util.Util;

public class AgregarAlumnoActivity extends AppCompatActivity {
    @BindView(R.id.cimv_estudiante_perfil)
    CircularImageView cimv_estudiante_perfil;
    @BindView(R.id.et_agregar_alumno_edad)
    TextInputEditText et_agregar_alumno_edad;
    @BindView(R.id.et_agregar_alumno_fecha)
    TextInputEditText et_agregar_alumno_fecha;
    @BindView(R.id.spinner)
    MaterialSpinner spinner;
    @BindView(R.id.et_agregar_alumno_telefono)
    TextInputEditText et_agregar_alumno_telefono;
    @BindView(R.id.et_agregar_alumno_grupo)
    TextInputEditText et_agregar_alumno_grupo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_alumno);
        ButterKnife.bind(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    @OnClick(R.id.img_arrow_back)
    public void goBack(){
        super.onBackPressed();
    }
    @OnClick(R.id.btn_agregar_estudiante)
    public void agregarAlumno(){
        validarCampor();
    }

    private void validarCampor() {
        String nombre,edad,fecha,telefono,grupo;
    }
    private void spinnerAdapter() {
        String A_POSITIVO=getString(R.string.A_POSITIVO_TIPODESANGRE);
        String B_POSITIVO=getString(R.string.B_POSITIVO_TIPODESANGRE);
        String AB_POSITIVO=getString(R.string.AB_POSITIVO_TIPODESANGRE);
        String O_POSITIVO=getString(R.string.O_POSITIVO_TIPODESANGRE);
        String A_NEGATIVO=getString(R.string.A_NEGATIVO_TIPODESANGRE);
        String B_NEGATIVO=getString(R.string.B_NEGATIVO_TIPODESANGRE);
        String AB_NEGATIVO=getString(R.string.AB_NEGATIVO_TIPODESANGRE);
        String O_NEGATIVO=getString(R.string.O_NEGATIVO_TIPODESANGRE);


        spinner.setItems(A_POSITIVO,A_NEGATIVO,B_POSITIVO,B_NEGATIVO,O_POSITIVO,O_NEGATIVO,AB_POSITIVO,AB_NEGATIVO);
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {

             //   if (item.equals(profesor)) {
             //       Util.showLog("El item es profesor");
             //       tipoDeUsuario= Constantes.USUARIO_PROFESOR;
             //   }else if (item.equals(padre)){
             //       tipoDeUsuario=Constantes.USUARIO_PADRE_MADRE;
             //   }else if (item.equals(madre)){
             //       tipoDeUsuario=Constantes.USUARIO_PADRE_MADRE;
             //   }else{
             //       tipoDeUsuario=3;
             //   }
            }
        });
    }
}
