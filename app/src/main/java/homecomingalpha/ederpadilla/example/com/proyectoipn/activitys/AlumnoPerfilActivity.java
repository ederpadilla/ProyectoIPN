package homecomingalpha.ederpadilla.example.com.proyectoipn.activitys;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import homecomingalpha.ederpadilla.example.com.proyectoipn.R;
import homecomingalpha.ederpadilla.example.com.proyectoipn.models.Alumnos;
import homecomingalpha.ederpadilla.example.com.proyectoipn.util.Constantes;
import homecomingalpha.ederpadilla.example.com.proyectoipn.util.Util;
import io.realm.Realm;

public class AlumnoPerfilActivity extends AppCompatActivity {
    @BindView(R.id.tv_alumno_perfil_nombre)
    TextView tv_alumno_perfil_nombre;
    @BindView(R.id.tv_alumno_perfil_edad)
    TextView tv_alumno_perfil_edad;
    @BindView(R.id.tv_alumno_perfil_tipo_de_sangre)
    TextView tv_alumno_perfil_tipo_de_sangre;
    @BindView(R.id.tv_alumno_perfil_fecha)
    TextView tv_alumno_perfil_fecha;
    @BindView(R.id.tv_alumno_perfil_telefono)
    TextView tv_alumno_perfil_telefono;
    @BindView(R.id.tv_alumno_perfil_grupo)
    TextView tv_alumno_perfil_grupo;
    @BindView(R.id.et_alumno_perfil_codigo)
    TextInputEditText et_alumno_perfil_codigo;
    @BindView(R.id.btn_perfil_alumno_checkout)
    Button btn_perfil_alumno_checkout;
    @BindView(R.id.btn_perfil_alumno_encamino)
    Button btn_perfil_alumno_encamino;
    @BindView(R.id.btn_perfil_alumno_retardo)
    Button btn_perfil_alumno_retardo;
    @BindView(R.id.btn_perfil_alumno_alguien_mas_recogera)
    Button btn_perfil_alumno_alguien_mas_recogera;
    private Bundle bundle;
    private String code;
    private Alumnos alumnoPerfil;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumno_perfil);
        ButterKnife.bind(this);
        realm=Realm.getDefaultInstance();
        bundle = getIntent().getExtras();
        code=bundle.getString(Constantes.LLAVE_ALUMNO_CODIGO);
        alumnoPerfil=conseguirAlumnos(code);
        asignarValoresDelAlumno(alumnoPerfil);
        checkForUserType();
    }

    public Alumnos conseguirAlumnos(String code) {
        return realm.where(Alumnos.class).equalTo(Constantes.LLAVE_ALUMNO_CODIGO,code).findFirst();
    }

    private void asignarValoresDelAlumno(Alumnos alumno) {
        tv_alumno_perfil_nombre.append(alumno.getNombreCompletoAlumno());
        tv_alumno_perfil_edad.append(alumno.getEdadAlumno());
        tv_alumno_perfil_tipo_de_sangre.append(alumno.getTipoDeSangreAlumno());
        tv_alumno_perfil_fecha.append(alumno.getFechaNacimientoAlumno());
        tv_alumno_perfil_telefono.append(alumno.getTelefonoAlumno());
        tv_alumno_perfil_grupo.append(alumno.getGrupoAlumno());
        et_alumno_perfil_codigo.setText(alumno.getCodigoAlumno());

    }


    @OnClick(R.id.img_arrow_back)
    public void goBack(){
        Intent intent = new Intent(AlumnoPerfilActivity.this,
                PerfilActivity.class);
        startActivity(intent);
        AlumnoPerfilActivity.this.finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(AlumnoPerfilActivity.this,
                PerfilActivity.class);
        startActivity(intent);
        AlumnoPerfilActivity.this.finish();
    }
    private void checkForUserType() {
    if (Util.getSharerPreferencesUserType(getApplicationContext())== Constantes.USUARIO_PADRE_MADRE){
        btn_perfil_alumno_checkout.setVisibility(View.GONE);
        esUnPadre();

    }else {
        btn_perfil_alumno_alguien_mas_recogera.setVisibility(View.GONE);
        btn_perfil_alumno_encamino.setVisibility(View.GONE);
        btn_perfil_alumno_retardo.setVisibility(View.GONE);
        esUnProfesor();
    }
    }

    private void esUnProfesor() {
    }

    private void esUnPadre() {
    }

}
