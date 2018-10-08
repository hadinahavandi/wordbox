package ir.sweetsoft.wordbox.wordbox.Model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

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
}
