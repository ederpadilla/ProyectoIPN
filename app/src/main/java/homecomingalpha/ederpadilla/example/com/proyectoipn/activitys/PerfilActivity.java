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

import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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
import homecomingalpha.ederpadilla.example.com.proyectoipn.util.DownloadImage;
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
    public List<Alumnos> alumnosRealmList;
    public FirebaseDatabase database;
    private DatabaseReference databaseReference;
    public FirebaseUser firebaseUser;
    public List<Alumnos> alumnosFiltrados;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        ButterKnife.bind(this);
        alumnosFiltrados = new ArrayList<>();
        recViewInit();
        Util.showLog("Usuario en perfil"+Util.getUserInSharedPreferences(getApplicationContext()).toString());
        user=Util.getUserInSharedPreferences(getApplicationContext());
        Glide.with(this).load(user.getImageUrl()).into(cimgv_profile);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance();
        alumnosList = new ArrayList<>();
        checkForUserType(user);
    }



    private void checkForUserType(User user) {
        switch (user.getTipoDeUuario()){
            case Constantes.USUARIO_PADRE_MADRE:
                usuarioTipoPadreMadre();
                break;
            case Constantes.USUARIO_PROFESOR:
                usuarioTipoProfesor();
                break;
            case Constantes.USUARIO_SIN_DEFINIR:
                usuarioDeFacebook();
                break;
        }

    }

    private void usuarioDeFacebook() {
        tv_tipo.setText("Necesitas editar tu perfil antes de todo");
       // new DownloadImage(cimgv_profile).execute(user.getImageUrl());
        floatingActionButton.setVisibility(View.GONE);
    }

    private void usuarioTipoProfesor() {
        DatabaseReference mDatabaseReference= FirebaseDatabase.getInstance().getReference(Constantes.FIREBASE_DB_STUDENTS);
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot child : dataSnapshot.getChildren()) {
                    String edadAlumno=child.child(Constantes.FIREBASE_DB_STUDENTS_AGE).getValue().toString();
                    String fechaNacimientoAlumo=child.child(Constantes.FIREBASE_DB_STUDENTS_BIRTHDATE).getValue().toString();
                    String tipoDeSangreAlumno=child.child(Constantes.FIREBASE_DB_STUDENTS_BLODTYPE).getValue().toString();
                    String telefonoAlumno=child.child(Constantes.FIREBASE_DB_STUDENTS_PHONE).getValue().toString();
                    String grupoAlumno=child.child(Constantes.FIREBASE_DB_STUDENTS_GROUP).getValue().toString();
                    String fotoAlumnoUrl=child.child(Constantes.FIREBASE_DB_STUDENTS_PHOTO_URL).getValue().toString();
                    String idDelProfesor=child.child(Constantes.FIREBASE_DB_STUDENTS_USERID).getValue().toString();
                    String codigoAlumno=child.child(Constantes.FIREBASE_DB_STUDENTS_CODE).getValue().toString();
                    String nombreCompleto = child.child(Constantes.FIREBASE_DB_STUDENTS_NAME).getValue().toString();
                    Alumnos alunmno = new Alumnos(nombreCompleto,edadAlumno,fechaNacimientoAlumo,tipoDeSangreAlumno,telefonoAlumno,grupoAlumno,fotoAlumnoUrl,idDelProfesor,codigoAlumno);
                    alumnosList.add(alunmno);
                }
                for (Alumnos alumnos: alumnosList){
                    if (alumnos.getIdDelProfesor().equals(firebaseUser.getUid())){
                        alumnosFiltrados.add(alumnos);
                    }
                }
                if (alumnosFiltrados.size()<1){

                }else{
                    DatabaseReference myRef = database.getReference(Constantes.FIREBASE_DB_USERS).child(firebaseUser.getUid()).child(Constantes.FIREBASE_DB_USER_LIST);
                    myRef.setValue(alumnosFiltrados);
                    alumnosAdapter.notifyDataSetChanged();
                }
                Util.showLog("Busqued filtrada"+alumnosFiltrados);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void usuarioTipoPadreMadre() {
        DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference(Constantes.FIREBASE_DB_USERS).child(firebaseUser.getUid());
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User userFoundInFireBase=dataSnapshot.getValue(User.class);
              try {
                  List<Alumnos> hijosEnFirebase= userFoundInFireBase.getAlumnosRealmList();
                  Util.showLog("Lista "+hijosEnFirebase.toString());
                  if (hijosEnFirebase.equals(null)){

                  }else{
                      for (int i = 0 ; i<hijosEnFirebase.size();i++)
                          alumnosFiltrados.add(hijosEnFirebase.get(i));
                  }
              }catch (Exception e){

              }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });
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
        alumnosAdapter= new AlumnosAdapter(alumnosFiltrados,this);
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
        if(AccessToken.getCurrentAccessToken()!=null){
            LoginManager.getInstance().logOut();
            Util.borrarSharedPreferences(getApplicationContext());
        }
        Util.borrarSharedPreferences(getApplicationContext());
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(PerfilActivity.this,
                FaceboolLoginActivity.class);
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
        }

    }
    private void showDialog() {
        DialogFragment newFragment = BuscarEstudianteFragment.newInstance();
        newFragment.show(getSupportFragmentManager(),"buscar niño");

    }
    private void setTextViews(){
            alumnosList.clear();
        alumnosAdapter.notifyDataSetChanged();
        tv_perfil_name.setText(user.getNombre());
        String tipoEnString="";
        for (int i =0 ;i<user.getAlumnosRealmList().size();i++)
            alumnosList.add(user.getAlumnosRealmList().get(i));
        alumnosAdapter.notifyDataSetChanged();
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
