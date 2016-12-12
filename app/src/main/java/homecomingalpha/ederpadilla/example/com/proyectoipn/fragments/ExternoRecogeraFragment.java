package homecomingalpha.ederpadilla.example.com.proyectoipn.fragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import homecomingalpha.ederpadilla.example.com.proyectoipn.R;
import homecomingalpha.ederpadilla.example.com.proyectoipn.activitys.AlumnoPerfilActivity;
import homecomingalpha.ederpadilla.example.com.proyectoipn.models.Alumnos;
import homecomingalpha.ederpadilla.example.com.proyectoipn.util.Constantes;
import homecomingalpha.ederpadilla.example.com.proyectoipn.util.Util;

/**
 * Created by ederpadilla on 11/12/16.
 */

public class ExternoRecogeraFragment extends DialogFragment {
    @BindView(R.id.fragment_et__personaexterna)
    EditText nombrePersonQuerecogera;
    @BindView(R.id.tv_quien)
    TextView tv_quien;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /** Inflamos nuestra vista */
        View view = inflater.inflate(R.layout.fragment_persona_externa, container, false);
        ButterKnife.bind(this, view);
        tv_quien.setText(getString(R.string.nombre_de_la_persona_que_ira)+((AlumnoPerfilActivity)getActivity()).alumnoPerfil.getNombreCompletoAlumno()+"?");
        return view;
    }
    @OnClick(R.id.btn_enviar)
    public void enviar(){
        if (nombrePersonQuerecogera.getText().toString()==""){
            Util.showToast(getActivity(),getString(R.string.importante_decir_quien));
        }else{
            DatabaseReference referenciaEstadoAlumno = ((AlumnoPerfilActivity)getActivity()).database.getReference(Constantes.FIREBASE_DB_STUDENTS).child(((AlumnoPerfilActivity)getActivity()).alumnoPerfil.getCodigoAlumno()).child(Constantes.FIREBASE_DB_STUDENTS_STATE);
            referenciaEstadoAlumno.setValue(Constantes.STUDET_STATE_SOMEONE_ELSE);
            DatabaseReference referenciaFechalumno = ((AlumnoPerfilActivity)getActivity()).database.getReference(Constantes.FIREBASE_DB_STUDENTS).child(((AlumnoPerfilActivity)getActivity()).alumnoPerfil.getCodigoAlumno()).child(Constantes.FIREBASE_DB_STUDENTS_DATE);
            referenciaFechalumno.setValue(((AlumnoPerfilActivity)getActivity()).getDate());
            DatabaseReference referenciaHoraAlumno = ((AlumnoPerfilActivity)getActivity()).database.getReference(Constantes.FIREBASE_DB_STUDENTS).child(((AlumnoPerfilActivity)getActivity()).alumnoPerfil.getCodigoAlumno()).child(Constantes.FIREBASE_DB_STUDENTS_HOUR);
            referenciaHoraAlumno.setValue(((AlumnoPerfilActivity)getActivity()).getHour());
            DatabaseReference referenciaPersonaRecogeraAlumno = ((AlumnoPerfilActivity)getActivity()).database.getReference(Constantes.FIREBASE_DB_STUDENTS).child(((AlumnoPerfilActivity)getActivity()).alumnoPerfil.getCodigoAlumno()).child(Constantes.FIREBASE_DB_STUDENTS_PERSON_WHOS_GONNA_GET_IT);
            referenciaPersonaRecogeraAlumno.setValue(nombrePersonQuerecogera.getText().toString());
            Util.showToast(getActivity(),getString(R.string.se_le_aviso_al_profesor_que_alguien_mas_ira));
            dismiss();
        }

    }
    public static ExternoRecogeraFragment newInstance() {
        ExternoRecogeraFragment externoRecogeraFragment = new ExternoRecogeraFragment();
        return externoRecogeraFragment;
    }
}