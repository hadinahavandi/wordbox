package ir.sweetsoft.wordbox.wordbox.Model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by Hadi on 10/03/2017.
 */

@Table(name = "engtofa")
public class EngToFa extends Model {
    @Column(name = "en")
    public String English;
    @Column(name = "fa")
    public String Persian;
    public EngToFa()
    {
        super();
    }
}
