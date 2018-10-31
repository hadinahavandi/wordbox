package ir.sweetsoft.wordbox.wordbox.Model;

import android.content.DialogInterface;
import android.view.View;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by Hadi on 10/03/2017.
 */

@Table(name = "word")
public class Word extends Model {
    @Column(name = "wordtext")
    public String wordtext;
    @Column(name = "translation")
    public String translation;
    @Column(name = "description")
    public String description;
    @Column(name = "photourl")
    public String photourl;
    @Column(name = "viewcount")
    public int viewcount;
    @Column(name = "sentence")
    public String sentence;
    @Column(name = "addtime")
    public String addtime;
    @Column(name = "lastseen")
    public String lastseen;
    @Column(name = "place")
    public int place;
    @Column(name = "user")
    public User user;
    @Column(name = "box")
    public Box box;
    public Word()
    {
        super();
    }

    public static void shiftAllRemainingWords()
    {
        List<Word> allwords=new Select().from(Word.class).where("place<33").execute();
        for (int i=0;i<allwords.size();i++) {
            allwords.get(i).place++;
            allwords.get(i).save();
        }

    }
    public static List<Word> getTodayWords()
    {
        return new Select().from(Word.class).where("place=1 OR place=2 OR place=4 OR place=8 OR place=16 OR place=32").execute();
    }
    public static List<Word> getPlaceWords(int PlaceID)
    {
        return new Select().from(Word.class).where("place="+PlaceID).execute();
    }
}
