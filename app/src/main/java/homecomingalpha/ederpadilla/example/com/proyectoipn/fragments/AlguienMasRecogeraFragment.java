package homecomingalpha.ederpadilla.example.com.proyectoipn.fragments;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import homecomingalpha.ederpadilla.example.com.proyectoipn.R;
import homecomingalpha.ederpadilla.example.com.proyectoipn.activitys.AlumnoPerfilActivity;
import homecomingalpha.ederpadilla.example.com.proyectoipn.models.Alumnos;

/**
 * Created by ederpadilla on 09/12/16.
 */

public class AlguienMasRecogeraFragment extends DialogFragment {
    @BindView(R.id.fragment_alguien_mas_recogera_a_nombre)
    TextView nombre_alumno;
    @BindView(R.id.fragment_alguien_mas_recogera_recogido_por)
    TextView recogido_por;
    @BindView(R.id.fragment_alguien_mas_recogera_fecha)
    TextView fecha;
    @BindView(R.id.fragment_alguien_mas_recogera_hora)
    TextView hora;
    //fragment_algu

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /** Inflamos nuestra vista */
        View view = inflater.inflate(R.layout.fragment_alguien_mas_lo_recogera, container, false);
        ButterKnife.bind(this, view);
        setValues();
        return view;
    }
    public void setValues(){
        nombre_alumno.setText(getString(R.string.alguien_mas_recogera)+" "+((AlumnoPerfilActivity)getActivity()).alumnoPerfil.getNombreCompletoAlumno());
        recogido_por.setText(((AlumnoPerfilActivity)getActivity()).alumnoPerfil.getPersonaQueRecogera());
        fecha.setText(((AlumnoPerfilActivity)getActivity()).alumnoPerfil.getFecha());
        hora.setText(((AlumnoPerfilActivity)getActivity()).alumnoPerfil.getHora());
    }
    public static AlguienMasRecogeraFragment newInstance() {
        AlguienMasRecogeraFragment alguienMasRecogeraFragment = new AlguienMasRecogeraFragment();
        return alguienMasRecogeraFragment;
    }
}
