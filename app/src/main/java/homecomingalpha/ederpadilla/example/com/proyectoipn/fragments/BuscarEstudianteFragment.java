package homecomingalpha.ederpadilla.example.com.proyectoipn.fragments;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import homecomingalpha.ederpadilla.example.com.proyectoipn.R;
import homecomingalpha.ederpadilla.example.com.proyectoipn.activitys.PerfilActivity;
import homecomingalpha.ederpadilla.example.com.proyectoipn.adapters.AlumnosAdapter;
import homecomingalpha.ederpadilla.example.com.proyectoipn.models.Alumnos;
import homecomingalpha.ederpadilla.example.com.proyectoipn.models.User;
import homecomingalpha.ederpadilla.example.com.proyectoipn.util.Constantes;
import homecomingalpha.ederpadilla.example.com.proyectoipn.util.Util;

/**
 * Created by ederpadilla on 13/10/16.
 */

public class BuscarEstudianteFragment extends DialogFragment {
    @BindView(R.id.btn_fragment_buscar)
    Button btn_fragment_buscar;
    @BindView(R.id.fragment_et_codigo_buscar)
    TextInputEditText fragment_et_codigo_buscar;
    int encontro=0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /** Inflamos nuestra vista */
        View view = inflater.inflate(R.layout.fragment_buscar_alumno_codigo, container,false);
        ButterKnife.bind(this, view);
        return view;
    }
    public static BuscarEstudianteFragment newInstance(){
        BuscarEstudianteFragment buscarEstudianteFragment= new BuscarEstudianteFragment();
        return buscarEstudianteFragment;
    }
    @OnClick(R.id.btn_fragment_buscar)
    public void buscarAlumno()    {

        final List<Alumnos> alumnos = new ArrayList<>();
        final List<Alumnos> hijos = new ArrayList<>();
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
                    alumnos.add(alunmno);
                }
                for (Alumnos alumnoBuscado: alumnos) {
                    if (fragment_et_codigo_buscar.getText().toString().equals(alumnoBuscado.getCodigoAlumno())){
                        hijos.add(alumnoBuscado);
                        obtenerListaDeLaActividad().add(alumnoBuscado);
                        obtenerAdapterDeLaActividad().notifyDataSetChanged();
                        DatabaseReference myRef = ((PerfilActivity)getActivity()).database.getReference(Constantes.FIREBASE_DB_USERS).child(((PerfilActivity)getActivity()).firebaseUser.getUid()).child(Constantes.FIREBASE_DB_USER_LIST);
                        myRef.setValue(obtenerListaDeLaActividad());
                        encontro=1;
                        dismiss();

                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        if (encontro!=1){Util.showToast(getActivity(),getString(R.string.estudiante_no_encontrado));}
    }

    public List<Alumnos> obtenerListaDeLaActividad(){
        return ((PerfilActivity)getActivity()).alumnosFiltrados;
    }
    public AlumnosAdapter obtenerAdapterDeLaActividad(){
        return ((PerfilActivity)getActivity()).alumnosAdapter;
    }
    public User obtenerUsuarioDelaActividad(){
        return ((PerfilActivity)getActivity()).user;
    }
}
