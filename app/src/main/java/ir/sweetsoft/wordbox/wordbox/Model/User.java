package ir.sweetsoft.wordbox.wordbox.Model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by Hadi on 10/03/2017.
 */

@Table(name = "user")
public class User extends Model {
    @Column(name = "username")
    public String username;
    @Column(name = "password")
    public String password;
    @Column(name = "deletetime")
    public String deletetime;
    public User()
    {
        super();
    }
}
