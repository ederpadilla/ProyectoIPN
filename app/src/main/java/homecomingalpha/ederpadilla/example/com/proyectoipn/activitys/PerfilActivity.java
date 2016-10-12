package homecomingalpha.ederpadilla.example.com.proyectoipn.activitys;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import homecomingalpha.ederpadilla.example.com.proyectoipn.R;
import homecomingalpha.ederpadilla.example.com.proyectoipn.adapters.AlumnosAdapter;
import homecomingalpha.ederpadilla.example.com.proyectoipn.models.Alumnos;
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;

public class PerfilActivity extends AppCompatActivity {
    @BindView(R.id.cimgv_profile)
    CircularImageView cimgv_profile;
    @BindView(R.id.recView)
    RecyclerView recyclerView;
    @BindView(R.id.fab)
    FloatingActionButton floatingActionButton;
    private List<Alumnos> alumnosList;
    private AlumnosAdapter alumnosAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        ButterKnife.bind(this);
        recViewInit();
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
    }

    @OnClick(R.id.fab)
    public void add_alumns(){
        Alumnos alumnos = new Alumnos("Brenda Morales Peña", "40 años","2/02/1894");
        alumnosList.add(0,alumnos);
        alumnosAdapter.notifyDataSetChanged();
    }


}
