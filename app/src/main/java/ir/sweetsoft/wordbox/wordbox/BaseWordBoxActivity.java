package ir.sweetsoft.wordbox.wordbox;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Hadi on 30/03/2017.
 */

public class BaseWordBoxActivity extends AppCompatActivity {
    protected void showAlert(final String Message,final AlertDialog.OnClickListener OnClose,final boolean isCancelable)
    {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder AB = new AlertDialog.Builder(BaseWordBoxActivity.this);
                AB.setMessage(Message);
                AB.setPositiveButton("OK", OnClose);
                AB.setCancelable(isCancelable);
                AB.show();
            }
        });

    }
}
