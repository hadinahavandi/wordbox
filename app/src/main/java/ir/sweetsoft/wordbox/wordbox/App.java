package ir.sweetsoft.wordbox.wordbox;

import android.content.Context;
import android.support.multidex.MultiDexApplication;
import android.support.v4.app.ActivityCompat;

import com.activeandroid.ActiveAndroid;


/**
 * Created by Nahavandi on 8/17/2016.
 */
public class App extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();
            ActiveAndroid.initialize(this);
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }
}