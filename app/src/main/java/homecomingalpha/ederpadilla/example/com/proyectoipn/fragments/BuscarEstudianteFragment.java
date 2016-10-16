package homecomingalpha.ederpadilla.example.com.proyectoipn.fragments;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import homecomingalpha.ederpadilla.example.com.proyectoipn.R;

/**
 * Created by ederpadilla on 13/10/16.
 */

public class BuscarEstudianteFragment extends DialogFragment {
    @BindView(R.id.btn_fragment_buscar)
    Button btn_fragment_buscar;
    @BindView(R.id.fragment_et_codigo_buscar)
    TextInputEditText fragment_et_codigo_buscar;
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
    public void buscarAlumno()
    {

    }
}
