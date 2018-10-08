package ir.sweetsoft.wordbox.wordbox;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.activeandroid.query.Select;


import common.SweetFonts;
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
                    w.place=0;
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
        txtWord.setTypeface(SweetFonts.getFont(ManageActivity.this,SweetFonts.IRANSans));
        txtTranslation.setTypeface(SweetFonts.getFont(ManageActivity.this,SweetFonts.IRANSans));
        txtDescription.setTypeface(SweetFonts.getFont(ManageActivity.this,SweetFonts.IRANSans));
        txtSentence.setTypeface(SweetFonts.getFont(ManageActivity.this,SweetFonts.GOOGLESansRegular));
        btnSave.setTypeface(SweetFonts.getFont(ManageActivity.this,SweetFonts.GOOGLESansMedium));
        btnDelete.setTypeface(SweetFonts.getFont(ManageActivity.this,SweetFonts.GOOGLESansMedium));
        btnRestart.setTypeface(SweetFonts.getFont(ManageActivity.this,SweetFonts.GOOGLESansMedium));
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
