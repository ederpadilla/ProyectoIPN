package homecomingalpha.ederpadilla.example.com.proyectoipn.activitys;

import android.app.Activity;
import android.content.Intent;
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
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        StartAnimations();

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
                    /** We check if there is a user log in or not. **/
                    //String unm= sharedPreferences.getString(getResources().getString(R.string.Shared_Preferences_User), null);
                    /**if(sharedPreferences.contains(getResources().getString(R.string.Shared_Preferences_User))){
                     Log.e("entra al if","if false");
                     Intent intent = new Intent(Splash.this,
                     CalendarActivity.class);intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                     startActivity(intent);
                     }else{
                     Log.e("entra al else","else");
                     Intent intent = new Intent(Splash.this,
                     LoginActivity.class);intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                     startActivity(intent);
                     }**/
                    Intent intent = new Intent(SplashActivity.this,
                            MainActivity.class);
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