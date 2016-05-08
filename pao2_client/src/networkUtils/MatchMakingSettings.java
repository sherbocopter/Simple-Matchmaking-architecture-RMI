package networkUtils;

import java.io.Serializable;

/**
 *
 * @author SHerbocopter
 */
public class MatchMakingSettings implements Serializable {
    public int minLevel = 0;
    public int maxLevel = 0;
    
    public MatchMakingSettings() {
        
    }
    
    public MatchMakingSettings(int minLevel, int maxLevel) {
        this.minLevel = minLevel;
        this.maxLevel = maxLevel;
    }
}
