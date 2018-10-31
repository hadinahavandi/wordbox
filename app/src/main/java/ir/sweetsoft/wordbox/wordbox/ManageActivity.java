package ir.sweetsoft.wordbox.wordbox;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.activeandroid.query.Select;


import java.util.List;

import common.SweetFonts;
import ir.sweetsoft.wordbox.wordbox.Model.EngToEng;
import ir.sweetsoft.wordbox.wordbox.Model.EngToFa;
import ir.sweetsoft.wordbox.wordbox.Model.Word;

public class ManageActivity extends BaseWordBoxActivity {

    EditText txtWord,txtTranslation,txtDescription,txtSentence;
    Button btnSave,btnDelete,btnRestart;
    long wordID;
    Word w;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);
        init();
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(w==null) {
                    w = new Word();
                    int place;
                    boolean found=false;
                    for (place=0;(!found) && place>-100;place--)
                    {
                        List<Word> PlaceWords=Word.getPlaceWords(place);
                        if(PlaceWords==null || PlaceWords.isEmpty())
                            found=true;
                        else if(PlaceWords.size()<Constants.MAX_WORDS_IN_PLACE)
                            found=true;
                    }
                    w.place=place;
                    w.addtime=String.valueOf(System.currentTimeMillis());
                }
                if(txtWord.getText().toString().length()>0) {
                    w.wordtext = txtWord.getText().toString();
                    w.translation = txtTranslation.getText().toString();
                    w.description = txtDescription.getText().toString();
                    w.sentence = txtSentence.getText().toString();
                    w.save();

                    if (wordID < 0) {
                        showAlert("the word saved successfully!", new AlertDialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                txtWord.setText("");
                                txtSentence.setText("");
                                txtDescription.setText("");
                                txtTranslation.setText("");
                                finish();
                            }
                        }, false);
                    } else {
                        showAlert("the word changed successfully!", new AlertDialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();

                            }
                        }, false);
                    }
                }
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                w.delete();
                showAlert("the word removed successfully!", new AlertDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                },false);
            }
        });

        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                w.place=0;
                w.save();
                showAlert("the word moved into first place successfully!", new AlertDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                },false);
            }
        });
    }

    private void init()
    {
        wordID=getIntent().getLongExtra("id",-1);
        txtWord=(EditText)findViewById(R.id.txtWord);
        txtTranslation=(EditText)findViewById(R.id.txtTranslation);
        txtDescription=(EditText)findViewById(R.id.txtDescription);
        txtSentence=(EditText)findViewById(R.id.txtSentence);
        btnSave=(Button)findViewById(R.id.btnSave);
        btnDelete=(Button)findViewById(R.id.btnDelete);
        btnRestart=(Button)findViewById(R.id.btnRestart);
        txtWord.setTypeface(SweetFonts.getFont(ManageActivity.this,SweetFonts.GOOGLESansRegular));
        txtTranslation.setTypeface(SweetFonts.getFont(ManageActivity.this,SweetFonts.IRANSans));
        txtDescription.setTypeface(SweetFonts.getFont(ManageActivity.this,SweetFonts.GOOGLESansRegular));

//        txtWord.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
//        txtTranslation.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
//        txtDescription.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
//        txtSentence.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        txtSentence.setTypeface(SweetFonts.getFont(ManageActivity.this,SweetFonts.GOOGLESansRegular));
        btnSave.setTypeface(SweetFonts.getFont(ManageActivity.this,SweetFonts.GOOGLESansMedium));
        btnDelete.setTypeface(SweetFonts.getFont(ManageActivity.this,SweetFonts.GOOGLESansMedium));
        btnRestart.setTypeface(SweetFonts.getFont(ManageActivity.this,SweetFonts.GOOGLESansMedium));
        txtTranslation.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(txtTranslation.isFocused())
                    if(txtWord.getText().toString().trim().length()!=0)
                        if(txtTranslation.getText().toString().trim().length()==0)
                        {
                            Log.d("loading","The Translation");
                            List<EngToFa> translations=new Select().from(EngToFa.class).where("en='"+txtWord.getText().toString().toLowerCase().trim()+"'").execute();
                            if(translations!=null && translations.size()>0)
                            {
                                txtTranslation.setText(translations.get(0).Persian);
                            }
                            else
                            {
                                Log.e("Translation","No TranslationFound");
                            }
                        }
            }
        });
        txtDescription.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(txtDescription.isFocused())
                    if(txtWord.getText().toString().trim().length()!=0)
                        if(txtDescription.getText().toString().trim().length()==0)
                        {
                            Log.d("loading","The Description");
                            List<EngToEng> Description=new Select().from(EngToEng.class).where("word='"+txtWord.getText().toString().toLowerCase().trim()+"'").execute();
                            if(Description!=null && Description.size()>0) {

                                txtDescription.setText(txtDescription.getText().toString() + Description.get(0).Definition);
                            }
                            else
                            {
                                Log.e("Description","No DescriptionFound");
                            }
                        }
            }
        });
        if(wordID>0)
        {
            w=new Select().from(Word.class).where("Id="+String.valueOf(wordID)).executeSingle();
            txtWord.setText(w.wordtext);
            txtTranslation.setText(w.translation);
            txtSentence.setText(w.sentence);
            txtDescription.setText(w.description);
            btnRestart.setVisibility(View.VISIBLE);
            btnDelete.setVisibility(View.VISIBLE);
        }
        else
        {
            txtWord.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    Log.d("B",String.valueOf(b));

                }
            });
        }
    }
}
