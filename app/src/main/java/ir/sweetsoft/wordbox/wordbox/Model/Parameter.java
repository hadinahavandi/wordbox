package ir.sweetsoft.wordbox.wordbox.Model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by Hadi on 10/03/2017.
 */

@Table(name = "parameter")
public class Parameter extends Model {
    @Column(name = "name")
    public String name;
    @Column(name = "value")
    public String value;
    public Parameter()
    {
        super();
    }
}
