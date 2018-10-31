package ir.sweetsoft.wordbox.wordbox;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.support.v7.app.AlertDialog;
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
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import calendar.CivilDate;
import calendar.DateConverter;
import calendar.PersianDate;
import common.SweetDisplayScaler;
import common.SweetFonts;
import ir.sweetsoft.wordbox.wordbox.Model.Word;

public class ReviewActivity extends BaseWordDisplayActivity{

    ImageView imgOK,imgRefresh,imgSpeak;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word);
        init();
        lblWord=(TextView)findViewById(R.id.lblWord);
        lblAddDate=(TextView)findViewById(R.id.lblAddDate);
        lblAddDate.setGravity(Gravity.LEFT);
        lblAddDate.setTypeface(SweetFonts.getFont(getApplicationContext(),SweetFonts.GOOGLESansRegular));
        lblAddDate.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);
        lblPlace=(TextView)findViewById(R.id.lblPlace);
        lblPlace.setGravity(Gravity.RIGHT);
        lblPlace.setTypeface(SweetFonts.getFont(getApplicationContext(),SweetFonts.GOOGLESansRegular));
        lblPlace.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
        ShowWordSection();
    }
    public void ShowWordSection()
    {
        SweetDisplayScaler scaler=new SweetDisplayScaler(this);
        imgOK=(ImageView)findViewById(R.id.imgOK);
        imgOK.getLayoutParams().width=scaler.WidthPercentToPixel(20);
        imgRefresh=(ImageView)findViewById(R.id.imgReferesh);
        imgRefresh.getLayoutParams().width=scaler.WidthPercentToPixel(20);
        imgSpeak=(ImageView)findViewById(R.id.imgSpeak);
        imgSpeak.getLayoutParams().width=scaler.WidthPercentToPixel(20);
        List<Word> words=Word.getTodayWords();
        Collections.shuffle(words);
        loadWord(words,0);
    }
    private void shiftAllRemainingWords()
    {
        Word.shiftAllRemainingWords();
        showAlert("All Words Shifted to corresponding places", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                imgRefresh.setVisibility(View.GONE);
                imgOK.setVisibility(View.GONE);
                finish();
            }
        },false);
    }
    private void loadWord(final List<Word> words, final int wordIndex)
    {
        lblWord=(TextView)findViewById(R.id.lblWord);

        lblWord.setTypeface(SweetFonts.getFont(getApplicationContext(),SweetFonts.GOOGLESansMedium));
        lblWord.setTextSize(TypedValue.COMPLEX_UNIT_SP,40);
        lblWord.setGravity(Gravity.CENTER);
        lblAddDate=(TextView)findViewById(R.id.lblAddDate);
        lblAddDate.setGravity(Gravity.LEFT);
        lblAddDate.setTypeface(SweetFonts.getFont(getApplicationContext(),SweetFonts.GOOGLESansMedium));
        lblAddDate.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
        lblPlace=(TextView)findViewById(R.id.lblPlace);

        lblPlace.setGravity(Gravity.RIGHT);
        lblPlace.setTypeface(SweetFonts.getFont(getApplicationContext(),SweetFonts.GOOGLESansMedium));
        lblPlace.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
        if(wordIndex<words.size()) {
            lblWord.setText(words.get(wordIndex).wordtext);
            CivilDate StartCDate = GetDateFromTime(Long.valueOf(words.get(wordIndex).addtime));
            lblAddDate.setText("Added On:"+Date2String(StartCDate));
            lblPlace.setText("Place:"+words.get(wordIndex).place);
            wd = words.get(wordIndex);
            wd.lastseen=String.valueOf(System.currentTimeMillis());
            lblWord.setOnClickListener(onWordClickListener);
            lblWord.setTypeface(SweetFonts.getFont(getApplicationContext(),SweetFonts.GOOGLESansMedium));

            imgOK.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    loadWord(words,wordIndex+1);
                }
            });
            imgSpeak.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    speakWords(wd.wordtext);
                }
            });
            imgRefresh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    wd.place=0;
                    wd.save();
                    loadWord(words,wordIndex+1);
                }
            });
        }
        else
        {
            shiftAllRemainingWords();

        }

    }

}
