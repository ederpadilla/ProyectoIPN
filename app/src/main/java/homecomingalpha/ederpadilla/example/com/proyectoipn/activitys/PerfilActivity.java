package homecomingalpha.ederpadilla.example.com.proyectoipn.activitys;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import homecomingalpha.ederpadilla.example.com.proyectoipn.R;
import homecomingalpha.ederpadilla.example.com.proyectoipn.adapters.AlumnosAdapter;
import homecomingalpha.ederpadilla.example.com.proyectoipn.fragments.BuscarEstudianteFragment;
import homecomingalpha.ederpadilla.example.com.proyectoipn.models.Alumnos;
import homecomingalpha.ederpadilla.example.com.proyectoipn.models.User;
import homecomingalpha.ederpadilla.example.com.proyectoipn.util.Constantes;
import homecomingalpha.ederpadilla.example.com.proyectoipn.util.Util;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;

public class PerfilActivity extends AppCompatActivity {
    @BindView(R.id.cimgv_profile)
    CircularImageView cimgv_profile;
    @BindView(R.id.recView)
    RecyclerView recyclerView;
    @BindView(R.id.fab)
    FloatingActionButton floatingActionButton;
    @BindView(R.id.tv_perfil_name)
    TextView tv_perfil_name;
    @BindView(R.id.tv_cerrarsion)
    TextView tv_cerrarSesion;
    @BindView(R.id.tv_tipo)
    TextView tv_tipo;
    public List<Alumnos> alumnosList;
    public AlumnosAdapter alumnosAdapter;
    public User user;
    private Realm realm;
    private String idObtenido;
    public RealmList<Alumnos> alumnosRealmList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        ButterKnife.bind(this);
        recViewInit();
        realm = Realm.getDefaultInstance();
        idObtenido=Util.getSharerPreferencesUserId(getApplicationContext());
        user=conseguirUsuario(idObtenido);
        checkForUserType(user);
        setTextViews();
    }

    private User conseguirUsuario(String idd) {
        return realm.where(User.class).equalTo(Constantes.LLAVE_USUARIO_ID,idd).findFirst();
    }

    private void checkForUserType(User user) {
        if (user.getTipoDeUuario()==Constantes.USUARIO_PROFESOR){
            usuarioTipoProfesor();
        }else {
            usuarioTipoPadreMadre();
        }
    }

    private void usuarioTipoProfesor() {
    getAllAlumnosinRealm();

    }

    private void usuarioTipoPadreMadre() {
        alumnosAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.tv_editar_perfil)
    public void editPerfil(){
        super.onBackPressed();
        Intent intent = new Intent(PerfilActivity.this,
                RegistrarseActivity.class);
        startActivity(intent);
        finish();
    }

    /**Iniciamos el reciclerview*/
    private void recViewInit() {
        alumnosList= new ArrayList<Alumnos>();
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new SlideInLeftAnimator());
        recyclerView.getItemAnimator().setAddDuration(800);
        recyclerView.getItemAnimator().setRemoveDuration(800);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        alumnosAdapter= new AlumnosAdapter(alumnosList,this);
        recyclerView.setAdapter(alumnosAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy){
                if (dy > 0 ||dy<0 && floatingActionButton.isShown())
                    floatingActionButton.hide();
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                if (newState == RecyclerView.SCROLL_STATE_IDLE){
                    floatingActionButton.show();
                }
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
        alumnosAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Perfil activity", "Pulsado el elemento " + recyclerView.getChildAdapterPosition(v));
                Alumnos alumno = alumnosList.get(recyclerView.getChildAdapterPosition(v));
                Intent intent = new Intent(PerfilActivity.this,
                            AlumnoPerfilActivity.class);
                intent.putExtra(Constantes.LLAVE_ALUMNO_CODIGO,alumno.getCodigoAlumno());
                startActivity(intent);
                PerfilActivity.this.finish();
            }
        });
    }
    @OnClick(R.id.tv_cerrarsion)
    public void cerrarSesion(){
        Util.borrarSharedPreferences(getApplicationContext());
        Intent intent = new Intent(PerfilActivity.this,
                SplashActivity.class);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.fab)
    public void add_alumns(){
        switch (user.getTipoDeUuario()){
            case Constantes.USUARIO_PADRE_MADRE:
                showDialog();
                break;
            case Constantes.USUARIO_PROFESOR:
                Intent intent = new Intent(PerfilActivity.this,
                        AgregarAlumnoActivity.class);
                startActivity(intent);
                finish();
                break;
              //  Alumnos alumnos = new Alumnos("Brenda Morales Peña", "40 años","2/02/1894");
              //  alumnosList.add(0,alumnos);
              //  alumnosAdapter.notifyDataSetChanged();
              //  break;
        }

    }
    private void showDialog() {
        DialogFragment newFragment = BuscarEstudianteFragment.newInstance();
        newFragment.show(getSupportFragmentManager(),"buscar niño");

    }
    public void getAllAlumnosinRealm(){
        RealmResults<Alumnos> todosLosAlumnos = realm.where(Alumnos.class).findAll();
        for (Alumnos alumnos : todosLosAlumnos) {
            alumnosList.add(alumnos);
        }
        if (alumnosList.size()<1){
        Util.showLog("no esta agarrando ni madres");
        }else {
            Util.showLog("Esta entrando pa aca");
            alumnosRealmList =
                    alumnosRealmList = new RealmList<>();

            for (int i = 0; i < alumnosList.size(); i++) {
                alumnosRealmList.add(alumnosList.get(i));
                Util.showLog("Lista"+alumnosList.get(i));
            }
            alumnosAdapter.notifyDataSetChanged();
            realm.beginTransaction();
            user.setAlumnosRealmList(alumnosRealmList);
            realm.copyToRealmOrUpdate(user);
            realm.commitTransaction();
        }
    }

    /**   private void actualizarUsuario(User usuarioActualizado){
     usuarioActualizado=realm.where(User.class)
     .equalTo(Constantes.LLAVE_USUARIO_ID,buscarUsuario().getId())
     .findFirst();
     String nuevoNombre =et_nombre.getText().toString();
     String nuevoTelefono=et_telefono.getText().toString();
     String nuevoMail=et_mail.getText().toString();
     String nuevaContraseña=et_mail.getText().toString();
     realm.beginTransaction();
     usuarioActualizado.setNombre(nuevoNombre);
     usuarioActualizado.setTelefono(nuevoTelefono);
     usuarioActualizado.setEmail(nuevoMail);
     usuarioActualizado.setContraseña(nuevaContraseña);
     realm.copyToRealmOrUpdate(usuarioActualizado);
     realm.commitTransaction();
     }*/
    private void setTextViews(){
        /**  Bitmap bitmap = BitmapFactory.decodeByteArray(allEmpleados.get(i).getBytes(), 0, allEmpleados.get(i).getBytes().length);
         botondeempleados.setImageBitmap(bitmap);*/
        //Bitmap bitmap = BitmapFactory.decodeByteArray(user.getBytes(),0,user.getBytes().length);
        //cimgv_profile.setImageBitmap(bitmap);
        tv_perfil_name.setText(user.getNombre());
        String tipoEnString="";

        for (int i =0 ;i<user.getAlumnosRealmList().size();i++)
            alumnosList.add(user.getAlumnosRealmList().get(i));
        Util.showLog("Lista es "+user.getAlumnosRealmList());
        switch (user.getTipoDeUuario()){
            case Constantes.USUARIO_PADRE_MADRE:
                tipoEnString="Padre o madre de alumno";
                tv_tipo.setText(tipoEnString);
                break;
            case Constantes.USUARIO_PROFESOR:
                tv_tipo.setText(getString(R.string.profesor));
                break;
        }
    }


}
