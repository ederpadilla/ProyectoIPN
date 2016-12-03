package homecomingalpha.ederpadilla.example.com.proyectoipn.fragments;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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
import io.realm.Realm;
import io.realm.RealmList;

/**
 * Created by ederpadilla on 13/10/16.
 */

public class BuscarEstudianteFragment extends DialogFragment {
    @BindView(R.id.btn_fragment_buscar)
    Button btn_fragment_buscar;
    @BindView(R.id.fragment_et_codigo_buscar)
    TextInputEditText fragment_et_codigo_buscar;
    private Realm realm;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /** Inflamos nuestra vista */
        View view = inflater.inflate(R.layout.fragment_buscar_alumno_codigo, container,false);
        ButterKnife.bind(this, view);
        realm=Realm.getDefaultInstance();
        return view;
    }
    public static BuscarEstudianteFragment newInstance(){
        BuscarEstudianteFragment buscarEstudianteFragment= new BuscarEstudianteFragment();
        return buscarEstudianteFragment;
    }
    @OnClick(R.id.btn_fragment_buscar)
    public void buscarAlumno()    {
      // if (conseguirAlumnos(fragment_et_codigo_buscar.getText().toString())==null){
      //         Util.showToast(getActivity().getApplicationContext(),getString(R.string.estudiante_no_encontrado));
      // }else {
      //     obtenerListaDeLaActividad().add(conseguirAlumnos(fragment_et_codigo_buscar.getText().toString().trim().toUpperCase()));
      // if (obtenerListaDeLaActividad().size()<1){

      // }else{
      //     for (int i=0;i<obtenerListaDeLaActividad().size();i++){
      //     }
      //     obtenerAdapterDeLaActividad().notifyDataSetChanged();

      // }
      // dismiss();
      //     }

    }

    public List<Alumnos> obtenerListaDeLaActividad(){
        return ((PerfilActivity)getActivity()).alumnosList;
    }
    public AlumnosAdapter obtenerAdapterDeLaActividad(){
        return ((PerfilActivity)getActivity()).alumnosAdapter;
    }
    public User obtenerUsuarioDelaActividad(){
        return ((PerfilActivity)getActivity()).user;
    }
}
