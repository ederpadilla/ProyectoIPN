package homecomingalpha.ederpadilla.example.com.proyectoipn.fragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import homecomingalpha.ederpadilla.example.com.proyectoipn.R;
import homecomingalpha.ederpadilla.example.com.proyectoipn.activitys.AlumnoPerfilActivity;
import homecomingalpha.ederpadilla.example.com.proyectoipn.models.Alumnos;

/**
 * Created by ederpadilla on 09/12/16.
 */

public class RetrasoFragment extends DialogFragment {
    @BindView(R.id.fragment_retrasado_nombre)
    TextView nombrePersonQuerecogera;
    @BindView(R.id.fragment_retrasado_fecha)
    TextView fecha;
    @BindView(R.id.fragment_retrasado_hora)
    TextView hora;
    Alumnos alumno;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /** Inflamos nuestra vista */
        View view = inflater.inflate(R.layout.fragment_retrasado, container, false);
        ButterKnife.bind(this, view);
        setValues();
        return view;
    }
    public void setValues(){
        nombrePersonQuerecogera.setText(((AlumnoPerfilActivity)getActivity()).alumnoPerfil.getPersonaQueRecogera());
        fecha.setText(((AlumnoPerfilActivity)getActivity()).alumnoPerfil.getFecha());
        hora.setText(((AlumnoPerfilActivity)getActivity()).alumnoPerfil.getHora());
    }
    public static RetrasoFragment newInstance() {
        RetrasoFragment retrasoFragment = new RetrasoFragment();
        return retrasoFragment;
    }
}
