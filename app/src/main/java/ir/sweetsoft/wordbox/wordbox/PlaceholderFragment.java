package ir.sweetsoft.wordbox.wordbox;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import common.SweetDisplayScaler;
import common.SweetFonts;
import ir.sweetsoft.wordbox.wordbox.Model.Word;

import calendar.CivilDate;
import calendar.DateConverter;
import calendar.PersianDate;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private TextView lblLastAddDate, lblWordCount, lblLastSeen, lblTotalViewCount,LblStartReview;
    private ImageView ImgStartReview;

    public PlaceholderFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PlaceholderFragment newInstance(int sectionNumber) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        int SectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);
        View rootView = null;
        switch (SectionNumber) {
            case 1:
                rootView = inflater.inflate(R.layout.fragment_list, container, false);
                ShowListSection(rootView, getActivity());
                break;
            case 2:
                rootView = inflater.inflate(R.layout.fragment_word, container, false);
                ShowWordSection(rootView, getActivity());
        }
        return rootView;
    }

    public void ShowListSection(final View rootView, final Activity activity) {
        SweetDisplayScaler scaler = new SweetDisplayScaler(activity);
        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity, ManageActivity.class);
                activity.startActivityForResult(i, Constants.REQUEST_ADDWORD);
            }
        });
        List<CardView> Words = new ArrayList<CardView>();

        RelativeLayout WordList = (RelativeLayout) rootView.findViewById(R.id.wordlist);
        WordList.removeAllViews();
        List<Word> wl = new Select().from(Word.class).orderBy("place ASC").execute();
        if(wl.size()<=0)
        {
            WordList.getLayoutParams().height=scaler.HeightPercentToPixel(70);
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            lp.addRule(RelativeLayout.CENTER_HORIZONTAL,1);
            lp.addRule(RelativeLayout.CENTER_VERTICAL,1);
            TextView Message=new TextView(getContext());
            Message.setText("Please Add A Word!");
            Message.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
            Message.setTypeface(SweetFonts.getFont(getContext(), SweetFonts.GOOGLESansMedium));
            WordList.addView(Message, lp);
            Log.d("No Word","No Word");
        }

        for (int i = 0; i < wl.size(); i++) {

            final long wlID = wl.get(i).getId();
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(scaler.WidthPercentToPixel(45), scaler.HeightPercentToPixel(20));
            lp.setMargins(scaler.WidthPercentToPixel(1),scaler.WidthPercentToPixel(1),scaler.WidthPercentToPixel(1),scaler.WidthPercentToPixel(1));

            CardView Word = new CardView(activity);
            Word.setRadius(50);

            Word.setPadding(scaler.WidthPercentToPixel(1), scaler.HeightPercentToPixel(5), scaler.WidthPercentToPixel(1), scaler.HeightPercentToPixel(5));


            CardView.LayoutParams contentLP = new CardView.LayoutParams(CardView.LayoutParams.MATCH_PARENT, CardView.LayoutParams.WRAP_CONTENT);
            contentLP.gravity=Gravity.CENTER;
            RelativeLayout CardContent=new RelativeLayout(activity);

            TextView TitleLabel = new TextView(activity);
            TitleLabel.setTypeface(SweetFonts.getFont(getContext(), SweetFonts.GOOGLESansMedium));
            TitleLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            TitleLabel.setText(wl.get(i).wordtext);
            TitleLabel.setId(TitleLabel.generateViewId());

            RelativeLayout.LayoutParams TitleLabelParams=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
            TitleLabelParams.addRule(RelativeLayout.CENTER_HORIZONTAL,1);

            RelativeLayout.LayoutParams actionbarLP = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            RelativeLayout ActionBar=new RelativeLayout(activity);
            actionbarLP.addRule(RelativeLayout.BELOW,TitleLabel.getId());
            actionbarLP.addRule(RelativeLayout.CENTER_HORIZONTAL,1);

            RelativeLayout.LayoutParams editLP = new RelativeLayout.LayoutParams(scaler.WidthPercentToPixel(10), scaler.WidthPercentToPixel(10));
            ImageView editIcon=new ImageView(activity);
            editIcon.setImageResource(R.drawable.pencil);
            editIcon.setId(editIcon.generateViewId());
            editIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(rootView.getContext(), ManageActivity.class);
                    i.putExtra("id", wlID);
                    activity.startActivityForResult(i, Constants.REQUEST_EDITWORD);
                }
            });

            RelativeLayout.LayoutParams deleteLP = new RelativeLayout.LayoutParams(scaler.WidthPercentToPixel(10), scaler.WidthPercentToPixel(10));
            deleteLP.addRule(RelativeLayout.RIGHT_OF,editIcon.getId());
            deleteLP.setMarginStart(scaler.WidthPercentToPixel(1));
            deleteLP.setMarginEnd(scaler.WidthPercentToPixel(1));
            ImageView deleteIcon=new ImageView(activity);
            deleteIcon.setImageResource(R.drawable.delete);
            deleteIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new Select().from(Word.class).where("Id="+String.valueOf(wlID)).executeSingle().delete();
                    new LovelyStandardDialog(activity, LovelyStandardDialog.ButtonLayout.VERTICAL)
                            .setTopColorRes(R.color.colorRed)
                            .setButtonsColorRes(R.color.colorBlue)
                            .setIcon(R.drawable.deletewhite)
                            .setTitle(R.string.deletesuccessfulltitle)
                            .setMessage(R.string.deletesuccessfull)
                            .setPositiveButton(R.string.ok, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ShowListSection(rootView,activity);
                                }
                            })
                            .show();
                }
            });

            ActionBar.addView(editIcon, editLP);
            ActionBar.addView(deleteIcon, deleteLP);



            CardContent.addView(TitleLabel, TitleLabelParams);
            CardContent.addView(ActionBar, actionbarLP);
            Word.addView(CardContent, contentLP);
//            Word.addView(editIcon, editLP);
            Word.setId(Word.generateViewId());
            if (wl.get(i).place > 32) {
                TitleLabel.setTextColor(Color.parseColor("#ffffff"));
                CardContent.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                CardContent.getLayoutParams().height=scaler.HeightPercentToPixel(6);
                TitleLabelParams.addRule(RelativeLayout.CENTER_IN_PARENT,1);
//                TitleLabel.setLayoutParams(TitleLabelParams);
                Word.setRadius(50);
                ActionBar.setVisibility(View.GONE);
            }
            lp.bottomMargin = scaler.HeightPercentToPixel(2);
            if (i % 2 == 0) {
                lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 1);
            } else if (i % 2 == 1) {
                lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 1);
            }
            if (i > 1) {
                lp.addRule(RelativeLayout.BELOW, Words.get(i - 2).getId());
            }
            Word.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(rootView.getContext(), ViewActivity.class);
                    i.putExtra("id", wlID);
                    activity.startActivity(i);
                }
            });
            Word.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
//                    Intent i = new Intent(rootView.getContext(), ManageActivity.class);
//                    i.putExtra("id", wlID);
//                    activity.startActivityForResult(i, Constants.REQUEST_EDITWORD);
                    return false;
                }
            });
            Words.add(Word);
            WordList.addView(Word, lp);
        }
        ;
    }

    public void ShowWordSection(final View rootView, final Activity activity) {
        ImgStartReview=(ImageView) rootView.findViewById(R.id.imgStartReview);
        ImgStartReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(rootView.getContext(), ReviewActivity.class);
                activity.startActivityForResult(i, Constants.REQUEST_REVIEW);
            }
        });
        LblStartReview = (TextView) rootView.findViewById(R.id.lblStartReview);
        lblLastAddDate = (TextView) rootView.findViewById(R.id.lblLastAddDate);
        lblWordCount = (TextView) rootView.findViewById(R.id.lblWordCount);
        lblLastSeen = (TextView) rootView.findViewById(R.id.lblLastSeen);
        lblTotalViewCount = (TextView) rootView.findViewById(R.id.lblTotalViewCount);
        LblStartReview.setTypeface(SweetFonts.getFont(rootView.getContext(),SweetFonts.GOOGLESansMedium));
        lblLastAddDate.setTypeface(SweetFonts.getFont(rootView.getContext(),SweetFonts.GOOGLESansMedium));
        lblWordCount.setTypeface(SweetFonts.getFont(rootView.getContext(),SweetFonts.GOOGLESansMedium));
        lblLastSeen.setTypeface(SweetFonts.getFont(rootView.getContext(),SweetFonts.GOOGLESansMedium));
        lblTotalViewCount.setTypeface(SweetFonts.getFont(rootView.getContext(),SweetFonts.GOOGLESansMedium));
        SweetDisplayScaler scaler = new SweetDisplayScaler(activity);
        Word w = new Select("max(addtime) as Id,sum(viewcount) as wordtext,count(*) as translation,max(lastseen) as description").from(Word.class).executeSingle();

        if (w.translation != null)
            lblWordCount.setText("Word Count: " + w.translation);
        if (w.wordtext != null)
            lblTotalViewCount.setText("Total View Count: " + w.wordtext);
        if (w.getId() != null && w.getId() > 0)
            lblLastAddDate.setText("Last Add Date: " + Date2String(GetDateFromTime(w.getId())));
        if (w.description != null)
            lblLastSeen.setText("Last Word Seen at: " + Date2String(GetDateFromTime(Long.valueOf(w.description))));
//        ImgStartReview.getLayoutParams().width = scaler.WidthPercentToPixel(40);
        ImgStartReview.getLayoutParams().height = scaler.WidthPercentToPixel(50);
    }

    private String Date2String(CivilDate cd) {
        PersianDate PDate = DateConverter.civilToPersian(cd);
        String DateSTR = String.valueOf(PDate.getYear()) + "/" + String.valueOf(PDate.getMonth()) + "/" + String.valueOf(PDate.getDayOfMonth());
        return DateSTR;
    }

    private CivilDate GetDateFromTime(Long Time) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(Time);
        CivilDate cd = new CivilDate();
        String year = DateFormat.format("yyyy", cal).toString();
        String month = DateFormat.format("MM", cal).toString();
        String day = DateFormat.format("dd", cal).toString();
        cd.setDate(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
        return cd;
    }
}