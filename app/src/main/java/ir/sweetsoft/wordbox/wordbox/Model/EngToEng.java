package ir.sweetsoft.wordbox.wordbox.Model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by Hadi on 10/03/2017.
 */

@Table(name = "entries")
public class EngToEng extends Model {
    @Column(name = "word")
    public String Word;
    @Column(name = "wordtype")
    public String WordType;
    @Column(name = "definition")
    public String Definition;
    public EngToEng()
    {
        super();
    }
}
