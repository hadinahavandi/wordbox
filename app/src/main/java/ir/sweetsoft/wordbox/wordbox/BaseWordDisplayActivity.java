package ir.sweetsoft.wordbox.wordbox;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.activeandroid.query.Select;

import java.util.Calendar;
import java.util.List;

import calendar.CivilDate;
import calendar.DateConverter;
import calendar.PersianDate;
import common.SweetDisplayScaler;
import common.SweetFonts;
import ir.sweetsoft.wordbox.wordbox.Model.Word;

public class BaseWordDisplayActivity extends BaseSpeakerActivity{

    protected TextView lblWord,lblAddDate,lblPlace;
    protected View.OnClickListener onWordClickListener;
    protected long wordID;
    protected Word wd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onWordClickListener=new View.OnClickListener() {
            int clicks=0;
            boolean loaded=false;
            @Override
            public void onClick(View view) {
                if(clicks==0) {
                    if(!loaded)
                    {
                        wd.viewcount++;
                        wd.save();
                        loaded=true;
                    }
                    if(wd.sentence.length() > 0)
                    {
                        lblWord.setText(wd.sentence);
                        lblWord.setTypeface(SweetFonts.getFont(getApplicationContext(),SweetFonts.IRANSansLight));
                        lblWord.setTextSize(TypedValue.COMPLEX_UNIT_SP,17);
                        lblWord.setGravity(Gravity.LEFT);
                    }
                    else
                        clicks++;
                }
                if(clicks==1) {
                    if (wd.translation.length() > 0) {
                        lblWord.setTypeface(SweetFonts.getFont(getApplicationContext(),SweetFonts.IRANSans));
                        lblWord.setTextSize(TypedValue.COMPLEX_UNIT_SP,30);
                        lblWord.setGravity(Gravity.CENTER);
                        lblWord.setText(wd.translation);
                    }
                    else
                        clicks++;
                }
                if(clicks==2) {
                    if (wd.description.length() > 0) {
                        lblWord.setTypeface(SweetFonts.getFont(getApplicationContext(),SweetFonts.IRANSansLight));
                        lblWord.setTextSize(TypedValue.COMPLEX_UNIT_SP,17);
                        lblWord.setGravity(Gravity.CENTER);
                        lblWord.setText(wd.description);
                    }
                    else
                        clicks++;
                }
                if(clicks>2)
                {
                    clicks=-1;
                    lblWord.setText(wd.wordtext);

                    lblWord.setTypeface(SweetFonts.getFont(getApplicationContext(),SweetFonts.GOOGLESansMedium));
                    lblWord.setTextSize(TypedValue.COMPLEX_UNIT_SP,40);
                    lblWord.setGravity(Gravity.CENTER);
                }
                clicks++;

            }
        };


    }
    protected String Date2String(CivilDate cd)
    {
        PersianDate PDate=DateConverter.civilToPersian(cd);
        String DateSTR=String.valueOf(PDate.getYear())+"/"+String.valueOf(PDate.getMonth())+"/"+String.valueOf(PDate.getDayOfMonth());
        return DateSTR;
    }
    protected CivilDate GetDateFromTime(Long Time)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(Time);
        CivilDate cd=new CivilDate();
        String year = DateFormat.format("yyyy", cal).toString();
        String month = DateFormat.format("MM", cal).toString();
        String day = DateFormat.format("dd", cal).toString();
        cd.setDate(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
        return cd;
    }

    protected void init()
    {
        wordID=getIntent().getLongExtra("id",-1);
    }


}
