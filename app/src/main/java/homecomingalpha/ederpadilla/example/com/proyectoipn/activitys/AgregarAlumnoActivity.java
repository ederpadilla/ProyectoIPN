package homecomingalpha.ederpadilla.example.com.proyectoipn.activitys;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import homecomingalpha.ederpadilla.example.com.proyectoipn.R;
import homecomingalpha.ederpadilla.example.com.proyectoipn.models.Alumnos;
import homecomingalpha.ederpadilla.example.com.proyectoipn.util.Constantes;
import homecomingalpha.ederpadilla.example.com.proyectoipn.util.Util;
import io.realm.Realm;

public class AgregarAlumnoActivity extends AppCompatActivity {
    @BindView(R.id.cimv_estudiante_perfil)
    CircularImageView cimv_estudiante_perfil;
    @BindView(R.id.et_agregar_alumno_nombre)
    TextInputEditText et_agregar_alumno_nombre;
    @BindView(R.id.et_agregar_alumno_edad)
    TextInputEditText et_agregar_alumno_edad;
    @BindView(R.id.et_agregar_alumno_fecha)
    TextInputEditText et_agregar_alumno_fecha;
    @BindView(R.id.agregar_alumno_spinner_sangre)
    MaterialSpinner spinner;
    @BindView(R.id.et_agregar_alumno_telefono)
    TextInputEditText et_agregar_alumno_telefono;
    @BindView(R.id.et_agregar_alumno_grupo)
    TextInputEditText et_agregar_alumno_grupo;
    private String tipoDeSangre="";
    private Realm realm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_alumno);
        ButterKnife.bind(this);
        spinnerAdapter();
        realm= Realm.getDefaultInstance();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(AgregarAlumnoActivity.this,PerfilActivity.class);
        startActivity(intent);
        finish();
    }
    @OnClick(R.id.img_arrow_back)
    public void goBack(){
        Intent intent = new Intent(AgregarAlumnoActivity.this,PerfilActivity.class);
        startActivity(intent);
        finish();
    }
    @OnClick(R.id.btn_agregar_estudiante)
    public void agregarAlumno(){
        validarCampor();
    }

    private void validarCampor() {

        String nombre,edad,fecha,telefono,grupo;
            if (
                Util.emptyField(et_agregar_alumno_nombre)==0&&
                Util.emptyField(et_agregar_alumno_edad)==0&&
                Util.emptyField(et_agregar_alumno_fecha)==0&&
                Util.emptyField(et_agregar_alumno_grupo)==0&&
                Util.emptyField(et_agregar_alumno_telefono)==0){
                Util.showToast(getApplicationContext(),getString(R.string.camposvacios));

            }else{
               if (tipoDeSangre.equals("")){
                   Util.showToast(getApplicationContext(),"Falta especificar el tipo de sangre");
               }else {
                 crearAlumno(asignarValoresRealesAlumno());
                   Intent intent = new Intent(AgregarAlumnoActivity.this,
                           PerfilActivity.class);
                   startActivity(intent);
                   finish();
               }
               }
    }

    private void spinnerAdapter() {
        final String A_POSITIVO=getString(R.string.A_POSITIVO_TIPODESANGRE);
        final String B_POSITIVO=getString(R.string.B_POSITIVO_TIPODESANGRE);
        final String AB_POSITIVO=getString(R.string.AB_POSITIVO_TIPODESANGRE);
        final String O_POSITIVO=getString(R.string.O_POSITIVO_TIPODESANGRE);
        final String A_NEGATIVO=getString(R.string.A_NEGATIVO_TIPODESANGRE);
        final String B_NEGATIVO=getString(R.string.B_NEGATIVO_TIPODESANGRE);
        final String AB_NEGATIVO=getString(R.string.AB_NEGATIVO_TIPODESANGRE);
        final String O_NEGATIVO=getString(R.string.O_NEGATIVO_TIPODESANGRE);


        spinner.setItems(getString(R.string.tipo_de_sangre),A_POSITIVO,A_NEGATIVO,B_POSITIVO,B_NEGATIVO,O_POSITIVO,O_NEGATIVO,AB_POSITIVO,AB_NEGATIVO);
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {

              if (item.equals(A_POSITIVO)) {
                  tipoDeSangre= getString(R.string.A_POSITIVO_TIPODESANGRE);
              }else if (item.equals(A_NEGATIVO)){
                  tipoDeSangre= getString(R.string.A_NEGATIVO_TIPODESANGRE);
              }else if (item.equals(B_POSITIVO)){
                  tipoDeSangre= getString(R.string.B_POSITIVO_TIPODESANGRE);
              }else if(item.equals(B_NEGATIVO)){
                  tipoDeSangre= getString(R.string.B_NEGATIVO_TIPODESANGRE);
              }else if(item.equals(O_NEGATIVO)){
                  tipoDeSangre= getString(R.string.O_NEGATIVO_TIPODESANGRE);
              }else if(item.equals(O_POSITIVO)){
                  tipoDeSangre= getString(R.string.O_POSITIVO_TIPODESANGRE);
              }else if(item.equals(AB_NEGATIVO)){
                  tipoDeSangre= getString(R.string.AB_NEGATIVO_TIPODESANGRE);
              }else if(item.equals(AB_POSITIVO)){
                  tipoDeSangre= getString(R.string.AB_POSITIVO_TIPODESANGRE);
              }else {
                  tipoDeSangre="";
              }
            }
        });
    }
    private String generarCodigo(){
        String codigo=null;
        Random r = new Random();
        char penultimaLetra = (char)(r.nextInt(26) + 'a');
        char ultimaLetra = (char)(r.nextInt(26) + 'a');
        char letrapenultima= Character.toUpperCase(penultimaLetra);
        char letraUltima= Character.toUpperCase(ultimaLetra);
        String nom= et_agregar_alumno_nombre.getText().toString().substring(0,2).toUpperCase();
        int numeroRandom= Util.randomInt(0,1000);
        codigo=nom+numeroRandom+letrapenultima+letraUltima;
        Util.showLog(codigo);
    return codigo;
    }
    private Alumnos asignarValoresRealesAlumno(){
        String nombre,edad,fecha,telefono,grupo;
        nombre=et_agregar_alumno_nombre.getText().toString();
        edad=et_agregar_alumno_edad.getText().toString();
        fecha=et_agregar_alumno_fecha.getText().toString();
        telefono=et_agregar_alumno_telefono.getText().toString();
        grupo=et_agregar_alumno_grupo.getText().toString();
        Alumnos alumnoCreado= new Alumnos(nombre,edad,fecha,tipoDeSangre,telefono,grupo,generarCodigo());
        return alumnoCreado;
    }
    private void crearAlumno(Alumnos alumnos){
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(alumnos);
        realm.commitTransaction();
    }
}
