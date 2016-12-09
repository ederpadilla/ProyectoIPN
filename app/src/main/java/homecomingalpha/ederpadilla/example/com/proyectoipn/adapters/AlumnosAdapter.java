package homecomingalpha.ederpadilla.example.com.proyectoipn.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import homecomingalpha.ederpadilla.example.com.proyectoipn.R;
import homecomingalpha.ederpadilla.example.com.proyectoipn.models.Alumnos;

/**
 * Created by ederpadilla on 10/10/16.
 */

public class AlumnosAdapter extends RecyclerView.Adapter<AlumnosAdapter.TitularesViewHolder>
        implements View.OnClickListener {

    private View.OnClickListener listener;
    private List<Alumnos> alumnosList;
    private Context context;

    public AlumnosAdapter(List<Alumnos> alumnosList, Context context) {
        this.alumnosList = alumnosList;
        this.context = context;
    }
    /**Aqui definimos el item que vamos a inyectar al recycler view
     * le implementamos un onclick listener */
    @Override
    public TitularesViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_estudiante, viewGroup, false);
        context=viewGroup.getContext();
        itemView.setOnClickListener(this);
        TitularesViewHolder tvh = new TitularesViewHolder(itemView);
        return tvh;
    }

    View roootView;

    @Override
    public void onBindViewHolder(TitularesViewHolder viewHolder, int pos) {
        Alumnos item = alumnosList.get(pos);
        viewHolder.bindTitular(item,context);
        viewHolder.getAdapterPosition();

    }

    @Override
    public int getItemCount() {
        return alumnosList.size();

    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }
    /**Aqui es donde entra el onclick
     * y se encarga de aparecer o desaparecer el contenido*/
    @Override
    public void onClick(View view) {
        if (listener != null) {
            TitularesViewHolder titularesViewHolder = new TitularesViewHolder(view);
            listener.onClick(view);
        }




    }
    /**En esta clase definimos los objetos dentro del item
     * y definimos lo que se le va a asignar y su comportamiento
     * */

    public class TitularesViewHolder
            extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_nombre)
        TextView tv_nombre;
        @BindView(R.id.tv_edad)
        TextView tv_edad;
        @BindView(R.id.tv_fecha_de_nacimiento)
        TextView tv_fecha_de_nacimiento;
        @BindView(R.id.cimv_estudiante_perfil)
        CircularImageView cimv_estudiante_perfil;

        public TitularesViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindTitular(Alumnos alumno,Context context) {

                tv_nombre.append(" "+alumno.getNombreCompletoAlumno());
                tv_edad.append(" "+alumno.getEdadAlumno()+" años");
                tv_fecha_de_nacimiento.append(" "+alumno.getFechaNacimientoAlumno());
            Glide
                    .with(context)
                    .load(alumno.getFotoAlumnoUrl())
                    .into(cimv_estudiante_perfil);
        }


    }
}
