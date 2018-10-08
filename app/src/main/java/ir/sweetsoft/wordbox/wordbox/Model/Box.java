package ir.sweetsoft.wordbox.wordbox.Model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by Hadi on 10/03/2017.
 */

@Table(name = "box")
public class Box extends Model {
    @Column(name = "title")
    public String title;
    @Column(name = "adddate")
    public String adddate;
    @Column(name = "deletetime")
    public String deletetime;
    public Box()
    {
        super();
    }
}
