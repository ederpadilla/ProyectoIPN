package homecomingalpha.ederpadilla.example.com.proyectoipn.activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

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
    EditText et_alumno_perfil_codigo;

    @BindView(R.id.btn_perfil_alumno_checkout)
    Button btn_perfil_alumno_checkout;

    @BindView(R.id.btn_perfil_alumno_encamino)
    Button btn_perfil_alumno_encamino;

    @BindView(R.id.btn_perfil_alumno_retardo)
    Button btn_perfil_alumno_retardo;

    @BindView(R.id.btn_perfil_alumno_alguien_mas_recogera)
    Button btn_perfil_alumno_alguien_mas_recogera;

    @BindView(R.id.alumno_photo)
    CircularImageView foto_alumno;

    @BindView(R.id.img_inform)
    ImageView img_inform;

    private Alumnos alumnoPerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumno_perfil);
        ButterKnife.bind(this);
        String codigo = getIntent().getExtras().getString(Constantes.FIREBASE_DB_STUDENTS_CODE);
        getBoyInFirebase(codigo);
        checkForUserType();
    }

    public void getBoyInFirebase(String codigo){
        DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference(Constantes.FIREBASE_DB_STUDENTS).child(codigo);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                alumnoPerfil=dataSnapshot.getValue(Alumnos.class);
                Util.showLog(dataSnapshot+"");
                asignarValoresDelAlumno(alumnoPerfil);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Util.showToast(getApplicationContext(),getString(R.string.problemas));

            }
        });

    }

    private void asignarValoresDelAlumno(Alumnos alumno) {
        tv_alumno_perfil_nombre.append(" "+alumno.getNombreCompletoAlumno());
        tv_alumno_perfil_edad.append(" "+alumno.getEdadAlumno()+" años");
        tv_alumno_perfil_tipo_de_sangre.append(" "+alumno.getTipoDeSangreAlumno());
        tv_alumno_perfil_fecha.append(" "+alumno.getFechaNacimientoAlumno());
        tv_alumno_perfil_telefono.append(" "+alumno.getTelefonoAlumno());
        tv_alumno_perfil_grupo.append(" "+alumno.getGrupoAlumno());
        et_alumno_perfil_codigo.setText(" "+alumno.getCodigoAlumno());
        Glide.with(this).load(alumno.getFotoAlumnoUrl()).into(foto_alumno);


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
        btn_perfil_alumno_checkout.setVisibility(View.VISIBLE);
        esUnProfesor();
    }
    }
    private void esUnProfesor() {
    }

    private void esUnPadre() {
    }
    @OnClick(R.id.tv_alumno_perfil_telefono)
    public void makeCall(){
    }
    public String getDate(){
        String fecha = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
    return fecha;
    }
    public String getHour(){
        String hour;
        Calendar now = Calendar.getInstance();
        hour=now.get(Calendar.HOUR_OF_DAY) + ":" + now.get(Calendar.MINUTE);
        return hour;
    }
}
