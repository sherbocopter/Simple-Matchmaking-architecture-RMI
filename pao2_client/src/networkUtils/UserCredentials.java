package networkUtils;

import java.io.Serializable;

/**
 *
 * @author SHerbocopter
 */
public class UserCredentials implements Serializable {
    public String username = "";
    public int level = 0;
    
    public UserCredentials() {
        
    }
    
    public UserCredentials(String username, int level) {
        this.username = username;
        this.level = level;
    }
}
