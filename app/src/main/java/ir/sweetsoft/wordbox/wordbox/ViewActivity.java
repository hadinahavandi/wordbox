package ir.sweetsoft.wordbox.wordbox;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.activeandroid.query.Select;

import java.util.Calendar;
import java.util.List;

import calendar.CivilDate;
import calendar.DateConverter;
import calendar.PersianDate;
import common.SweetFonts;
import ir.sweetsoft.wordbox.wordbox.Model.Word;

public class ViewActivity extends BaseWordDisplayActivity {

    private TextView lblLastSeen,lblViewCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        init();
        lblWord=(TextView)findViewById(R.id.lblWord);
        lblAddDate=(TextView)findViewById(R.id.lblAddDate);
        lblAddDate.setGravity(Gravity.LEFT);
        lblAddDate.setTypeface(SweetFonts.getFont(getApplicationContext(),SweetFonts.GOOGLESansMedium));
        lblAddDate.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
        lblPlace=(TextView)findViewById(R.id.lblPlace);
        lblPlace.setGravity(Gravity.RIGHT);
        lblPlace.setTypeface(SweetFonts.getFont(getApplicationContext(),SweetFonts.GOOGLESansMedium));
        lblPlace.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
        lblLastSeen=(TextView)findViewById(R.id.lblLastSeen);
        lblLastSeen.setGravity(Gravity.LEFT);
        lblLastSeen.setTypeface(SweetFonts.getFont(getApplicationContext(),SweetFonts.GOOGLESansMedium));
        lblLastSeen.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
        lblViewCount=(TextView)findViewById(R.id.lblViewCount);
        lblViewCount.setGravity(Gravity.RIGHT);
        lblViewCount.setTypeface(SweetFonts.getFont(getApplicationContext(),SweetFonts.GOOGLESansMedium));
        lblViewCount.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);


        if(wordID>0)
        {
            wd=new Select().from(Word.class).where("Id="+String.valueOf(wordID)).executeSingle();
            try {
                lblWord.setText(wd.wordtext);
            CivilDate StartCDate = GetDateFromTime(Long.valueOf(wd.addtime));
            lblAddDate.setText("Added On:" + Date2String(StartCDate));
            lblPlace.setText("Place:" + wd.place);
            lblViewCount.setText("Displayed " + wd.viewcount+" times");
                if(wd.lastseen!=null) {
                    CivilDate LastCDate = GetDateFromTime(Long.valueOf(wd.lastseen));
                    lblLastSeen.setText("Last Review:" + Date2String(LastCDate));
                }
                else
                {
                    lblLastSeen.setText("Last Review:not yet!");
                }
            final Word theWord = wd;
            lblWord.setOnClickListener(onWordClickListener);
                lblWord.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        speakWords(theWord.wordtext);
                        return false;
                    }
                });
            lblWord.setTypeface(SweetFonts.getFont(getApplicationContext(), SweetFonts.GOOGLESansMedium));

            }
            catch (Exception ex)
            {

            }
        }


        
    }

}
