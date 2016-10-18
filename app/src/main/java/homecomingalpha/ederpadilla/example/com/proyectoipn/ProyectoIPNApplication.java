package homecomingalpha.ederpadilla.example.com.proyectoipn;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by ederpadilla on 12/10/16.
 */

public class ProyectoIPNApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        RealmConfiguration config = new RealmConfiguration.Builder(this)
                .deleteRealmIfMigrationNeeded()
                .build();

        Realm.setDefaultConfiguration(config);
    }
}
