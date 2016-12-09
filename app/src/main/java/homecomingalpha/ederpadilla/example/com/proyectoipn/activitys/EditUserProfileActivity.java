package homecomingalpha.ederpadilla.example.com.proyectoipn.activitys;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.github.silvestrpredko.dotprogressbar.DotProgressBar;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.mikhaellopez.circularimageview.CircularImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import homecomingalpha.ederpadilla.example.com.proyectoipn.R;
import homecomingalpha.ederpadilla.example.com.proyectoipn.util.Util;

public class EditUserProfileActivity extends AppCompatActivity {
    @BindView(R.id.img_photo_update)
    CircularImageView img_photo;

    @BindView(R.id.register_et_nombre_update)
    TextInputEditText et_nombre;

    @BindView(R.id.register_et_telefono_update)
    TextInputEditText et_telefono;

    @BindView(R.id.register_et_password_update)
    TextInputEditText et_password;

    @BindView(R.id.btn_update)
    Button btn_crearcuenta;

    @BindView(R.id.dot_progress_bar)
    DotProgressBar dot_progress_bar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_profile);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_update)
    public void update(){
        if (et_nombre.getText().toString().isEmpty()||et_telefono.getText().toString().isEmpty()){
            Util.showToast(getApplicationContext(),getString(R.string.camposvacios));
        }else{

        }
    }

    @OnClick(R.id.img_arrow_back)
    public void goBack(){
        Intent intent = new Intent(EditUserProfileActivity.this,
                PerfilActivity.class);
        startActivity(intent);
        EditUserProfileActivity.this.finish();
    }
}
