package ir.sweetsoft.wordbox.wordbox;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

import com.activeandroid.query.Select;

import java.util.List;

import common.SweetDisplayScaler;
import ir.sweetsoft.wordbox.wordbox.Model.EngToFa;
import ir.sweetsoft.wordbox.wordbox.Model.Parameter;
import ir.sweetsoft.wordbox.wordbox.Model.Word;

public class SplashActivity extends Activity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;

    private ImageView imgLogo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        imgLogo=findViewById(R.id.imgLogo);
        SweetDisplayScaler scaler=new SweetDisplayScaler(this);
        imgLogo.getLayoutParams().width=scaler.WidthPercentToPixel(50);
        EngToFa EngToFas=new Select().from(EngToFa.class).executeSingle();
        if(EngToFas==null || EngToFas.English.trim().length()<=0)
        {
            Log.d("Importing","Database");
            DBTools.ImportFromAssets("initialdb.db",getApplicationContext(),Constants.DBNAME);

        }
//        imgLogo.getLayoutParams().width=scaler.WidthPercentToPixel(50);
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(i);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

}