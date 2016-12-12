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
import homecomingalpha.ederpadilla.example.com.proyectoipn.models.Alumnos;

/**
 * Created by ederpadilla on 09/12/16.
 */

public class EnCaminoFragment extends DialogFragment {
    @BindView(R.id.fragment_en_camino_nombre)
    TextView nombrePersonQuerecogera;
    @BindView(R.id.fragment_en_camino_fecha)
    TextView fecha;
    @BindView(R.id.fragment_en_camino_hora)
    TextView hora;
    Alumnos alumno;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /** Inflamos nuestra vista */
        View view = inflater.inflate(R.layout.fragment_en_camino, container, false);
        ButterKnife.bind(this, view);
        return view;
    }
    public void setValues(){
        nombrePersonQuerecogera.setText(alumno.getPersonaQueRecogera());
        fecha.setText(alumno.getFecha());
        hora.setText(alumno.getHora());
    }
    public static EnCaminoFragment newInstance() {
        EnCaminoFragment enCaminoFragment = new EnCaminoFragment();
        return enCaminoFragment;
    }
}