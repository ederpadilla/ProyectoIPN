package homecomingalpha.ederpadilla.example.com.proyectoipn.activitys;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import homecomingalpha.ederpadilla.example.com.proyectoipn.R;
import homecomingalpha.ederpadilla.example.com.proyectoipn.fragments.AlguienMasRecogeraFragment;
import homecomingalpha.ederpadilla.example.com.proyectoipn.fragments.AlumnoRecogidoFragment;
import homecomingalpha.ederpadilla.example.com.proyectoipn.fragments.BuscarEstudianteFragment;
import homecomingalpha.ederpadilla.example.com.proyectoipn.fragments.CheckOutFragment;
import homecomingalpha.ederpadilla.example.com.proyectoipn.fragments.EnCaminoFragment;
import homecomingalpha.ederpadilla.example.com.proyectoipn.fragments.ExternoRecogeraFragment;
import homecomingalpha.ederpadilla.example.com.proyectoipn.fragments.RetrasoFragment;
import homecomingalpha.ederpadilla.example.com.proyectoipn.models.Alumnos;
import homecomingalpha.ederpadilla.example.com.proyectoipn.models.User;
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

    public Alumnos alumnoPerfil;
    Bundle bundle;

    private User user;
    public FirebaseDatabase database;

    private List<String> permissions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumno_perfil);
        ButterKnife.bind(this);
        user = Util.getUserInSharedPreferences(getApplicationContext());
        String codigo = getIntent().getExtras().getString(Constantes.FIREBASE_DB_STUDENTS_CODE);
        getBoyInFirebase(codigo);
        database = FirebaseDatabase.getInstance();
        checkForUserType();
    }

    public void getBoyInFirebase(String codigo) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference(Constantes.FIREBASE_DB_STUDENTS).child(codigo);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                alumnoPerfil = dataSnapshot.getValue(Alumnos.class);
                Util.showLog(dataSnapshot + "");
                asignarValoresDelAlumno(alumnoPerfil);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Util.showToast(getApplicationContext(),getString(R.string.problemas));

            }
        });

    }

    private void asignarValoresDelAlumno(Alumnos alumno) {
        tv_alumno_perfil_nombre.append(" " + alumno.getNombreCompletoAlumno());
        tv_alumno_perfil_edad.append(" " + alumno.getEdadAlumno() + " años");
        tv_alumno_perfil_tipo_de_sangre.append(" " + alumno.getTipoDeSangreAlumno());
        tv_alumno_perfil_fecha.append(" " + alumno.getFechaNacimientoAlumno());
        tv_alumno_perfil_telefono.append(" " + alumno.getTelefonoAlumno());
        tv_alumno_perfil_grupo.append(" " + alumno.getGrupoAlumno());
        et_alumno_perfil_codigo.setText(" " + alumno.getCodigoAlumno());
        Glide.with(this).load(alumno.getFotoAlumnoUrl()).into(foto_alumno);


    }


    @OnClick(R.id.img_arrow_back)
    public void goBack() {
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
        if (Util.getSharerPreferencesUserType(getApplicationContext()) == Constantes.USUARIO_PADRE_MADRE) {
            btn_perfil_alumno_checkout.setVisibility(View.GONE);
            esUnPadre();

        } else {
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
    public void makeCall() {
        if (Util.hasPermissions(getApplicationContext(), permissions.toArray(new String[permissions.size()]))) {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + alumnoPerfil.getTelefonoAlumno()));
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            startActivity(intent);
        }else{
            Util.showLog("No tiene permisos");
            String[] PERMISSIONS = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
            if(!Util.hasPermissions(this, PERMISSIONS)){
                Util.showToast(getApplicationContext(),"No ah brindado permisos de acceso a la aplicación");
            }
        }

    }
    @OnClick(R.id.img_inform)
    public void checkState(){
        switch (alumnoPerfil.getEstado()){
            case Constantes.STUDENT_STATE_NOT_IN_SCHOOL:
                recogidoFragment();
                break;
            case Constantes.STUDET_STATE_SOMEONE_ELSE:
                alguienMasFragment();
                break;
            case Constantes.STUDENT_STATE_LATE:
                retardoFraggment();
                break;
            case Constantes.STUDENT_STATE_ON_MY_WAY:
                enCaminoFragment();
                break;
            case Constantes.STUDENT_STATE_NOT_YET:
                Util.showToast(getApplicationContext(),getString(R.string.sin_definir));
                break;
        }
    }
    public void setEstadoAlumno(int edo){
        DatabaseReference referenciaEstadoAlumno = database.getReference(Constantes.FIREBASE_DB_STUDENTS).child(alumnoPerfil.getCodigoAlumno()).child(Constantes.FIREBASE_DB_STUDENTS_STATE);
        referenciaEstadoAlumno.setValue(edo);
        DatabaseReference referenciaFechalumno = database.getReference(Constantes.FIREBASE_DB_STUDENTS).child(alumnoPerfil.getCodigoAlumno()).child(Constantes.FIREBASE_DB_STUDENTS_DATE);
        referenciaFechalumno.setValue(getDate());
        DatabaseReference referenciaHoraAlumno = database.getReference(Constantes.FIREBASE_DB_STUDENTS).child(alumnoPerfil.getCodigoAlumno()).child(Constantes.FIREBASE_DB_STUDENTS_HOUR);
        referenciaHoraAlumno.setValue(getHour());
        DatabaseReference referenciaPersonaRecogeraAlumno = database.getReference(Constantes.FIREBASE_DB_STUDENTS).child(alumnoPerfil.getCodigoAlumno()).child(Constantes.FIREBASE_DB_STUDENTS_PERSON_WHOS_GONNA_GET_IT);
        referenciaPersonaRecogeraAlumno.setValue(user.getNombre());
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
    @OnClick(R.id.btn_perfil_alumno_alguien_mas_recogera)
    public void alguienMasRecogera(){
        externoRecogera();
     //setEstadoAlumno(Constantes.STUDET_STATE_SOMEONE_ELSE);
    }
    @OnClick(R.id.btn_perfil_alumno_retardo)
    public void retardo(){
    setEstadoAlumno(Constantes.STUDENT_STATE_LATE);
        Util.showToast(getApplicationContext(),getString(R.string.se_le_aviso_al_profesor_que_va_tarde));
    }
    @OnClick(R.id.btn_perfil_alumno_encamino)
    public void enCamino(){
    setEstadoAlumno(Constantes.STUDENT_STATE_ON_MY_WAY);
        Util.showToast(getApplicationContext(),getString(R.string.se_le_aviso_al_profesor_que_va_en_camino));
    }
    @OnClick(R.id.btn_perfil_alumno_checkout)
    public void checkOut(){
    checkOutFragment();
    }
    public void setTheBundle(){
        bundle= new Bundle();
        bundle.putString(Constantes.FIREBASE_DB_STUDENTS_HOUR,getHour());
        bundle.putString(Constantes.FIREBASE_DB_STUDENTS_DATE,getDate());
        bundle.putString(Constantes.FIREBASE_DB_STUDENTS_NAME,alumnoPerfil.getNombreCompletoAlumno());
        bundle.putString(Constantes.FIREBASE_DB_STUDENTS_WHO_GET_IT,alumnoPerfil.getPersonaQueRecogio());
        bundle.putString(Constantes.FIREBASE_DB_STUDENTS_PERSON_WHOS_GONNA_GET_IT,alumnoPerfil.getPersonaQueRecogera());

    }
    public void retardoFraggment(){
        RetrasoFragment newFragment = RetrasoFragment.newInstance();
        newFragment.show(getSupportFragmentManager(),"retraso fragment");
    }
    public void enCaminoFragment(){
        setTheBundle();
        DialogFragment newFragment = EnCaminoFragment.newInstance();
        newFragment.setArguments(bundle);
        newFragment.show(getSupportFragmentManager(),"en camino fragment");
    }
    public void alguienMasFragment(){
        setTheBundle();
        DialogFragment newFragment = AlguienMasRecogeraFragment.newInstance();
        newFragment.setArguments(bundle);
        newFragment.show(getSupportFragmentManager(),"alguien mas fragment");
    }
    public void checkOutFragment(){
        setTheBundle();
        DialogFragment newFragment = CheckOutFragment.newInstance();
        newFragment.setArguments(bundle);
        newFragment.show(getSupportFragmentManager(),"check out fragment");
    }
    public void recogidoFragment(){
        setTheBundle();
        DialogFragment newFragment = AlumnoRecogidoFragment.newInstance();
        newFragment.setArguments(bundle);
        newFragment.show(getSupportFragmentManager(),"recogido fragment");
    }
    public void externoRecogera(){
        setTheBundle();
        DialogFragment newFragment = ExternoRecogeraFragment.newInstance();
        newFragment.setArguments(bundle);
        newFragment.show(getSupportFragmentManager(),"externo fragment");
    }
}
