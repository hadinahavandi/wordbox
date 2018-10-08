package ir.sweetsoft.wordbox.wordbox;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.query.Select;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import calendar.CivilDate;
import calendar.DateConverter;
import calendar.PersianDate;
import common.SweetDisplayScaler;
import common.SweetFonts;
import ir.sweetsoft.wordbox.wordbox.Model.Word;

public class BaseSpeakerActivity extends BaseWordBoxActivity implements TextToSpeech.OnInitListener{

    private TextToSpeech myTTS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myTTS = new TextToSpeech(this, this);
    }
    protected void speakWords(String speech) {

        myTTS.speak(speech, TextToSpeech.QUEUE_FLUSH, null,"HI IAM");
    }
    @Override
    public void onInit(int initStatus) {

        if (initStatus == TextToSpeech.SUCCESS) {
            if (myTTS.isLanguageAvailable(Locale.US) == TextToSpeech.LANG_AVAILABLE)
                myTTS.setLanguage(Locale.US);
        } else if (initStatus == TextToSpeech.ERROR) {
            Toast.makeText(this, "Sorry! Text To Speech failed...",
                    Toast.LENGTH_LONG).show();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        myTTS.shutdown();
    }
}
