package homecomingalpha.ederpadilla.example.com.proyectoipn.activitys;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import homecomingalpha.ederpadilla.example.com.proyectoipn.R;
import homecomingalpha.ederpadilla.example.com.proyectoipn.models.User;
import homecomingalpha.ederpadilla.example.com.proyectoipn.util.Constantes;
import homecomingalpha.ederpadilla.example.com.proyectoipn.util.Util;

public class SplashActivity extends Activity {
    private Thread splashTread;
    @BindView(R.id.lilnear)
    LinearLayout linearLayout;
    @BindView(R.id.splash)
    ImageView logo;
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }
    /**
     * Called when the activity is first created.
     */
    SharedPreferences sharedPreferences;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        StartAnimations();

    }
    private void checkForUserLogIn(){
        if (sharedPreferences.contains(Constantes.LLAVE_NOMBRE)){
            Intent intent = new Intent(SplashActivity.this,PerfilActivity.class);
            startActivity(intent);
            SplashActivity.this.finish();
        }else {
            StartAnimations();
        }
    }
    /** Here we star the animation. */
    private void StartAnimations() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim.reset();
        linearLayout.clearAnimation();
        linearLayout.startAnimation(anim);
        /** We call the animation that says form where to where its gonna move. */
        anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim.reset();
        /** The image ots gona move.**/
        logo.clearAnimation();
        logo.startAnimation(anim);

        splashTread = new Thread() {
            @Override
            public void run() {
                try {

                    /** Pause the execution of the code for 3.5 secs. **/
                    sleep(1500);
                    User uTest = new User("Test","1","eeef@qd","111",3);
                    Util.saveSharedPreferences(getApplicationContext(),uTest);
                    Intent intent = new Intent(SplashActivity.this,
                            FaceboolLoginActivity.class);
                    startActivity(intent);
                    SplashActivity.this.finish();
                } catch (InterruptedException e) {
                    // do nothing
                } finally {
                    //SplashActivity.this.finish();
                }
            }
        };
        splashTread.start();

    }


}