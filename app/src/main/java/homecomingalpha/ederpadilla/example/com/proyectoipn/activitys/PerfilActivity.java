package homecomingalpha.ederpadilla.example.com.proyectoipn.activitys;

import android.content.Intent;
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
    private List<Alumnos> alumnosList;
    private AlumnosAdapter alumnosAdapter;
    private User user;
    private Bundle bundle;
    private Realm realm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        ButterKnife.bind(this);
        recViewInit();
        realm = Realm.getDefaultInstance();
        //user = new User("Eder","5532453223","eder.padilla97@gmail.com","12341231",1);
        checkForUserType();
    }

    private void checkForUserType() {
        if (Util.getSharerPreferencesUserType(getApplicationContext())==Constantes.USUARIO_PROFESOR){
            usuarioTipoProfesor();
        }else {
            usuarioTipoPadreMadre();
        }
    }

    private void usuarioTipoProfesor() {
    getAllAlumnosinRealm();

    }

    private void usuarioTipoPadreMadre() {

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
        RealmResults<Alumnos> todosLosAlumnos=realm.where(Alumnos.class).findAll();
        for (Alumnos alumnos:todosLosAlumnos){
            alumnosList.add(alumnos);
        }
        alumnosAdapter.notifyDataSetChanged();
        RealmList<Alumnos> alumnosRealmList=
                alumnosRealmList= new RealmList<>();;
        for (int i =0;i<=alumnosList.size();i++){
            alumnosRealmList.add(alumnosList.get(i));
        }
        user.setAlumnosRealmList(alumnosRealmList);
        actulizarUsuario(user);

    }
    private void actulizarUsuario(User user){
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(user);
        realm.commitTransaction();

    }


}
