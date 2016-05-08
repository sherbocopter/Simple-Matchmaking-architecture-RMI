package networkUtils;

import java.io.Serializable;

/**
 *
 * @author SHerbocopter
 */
public class Match implements Serializable {
    public UserCredentials uc;
    public String address;
    public int portClient; //says to player which port should be used as
    public int portServer; //client or, respectively, server

    public Match(UserCredentials uc, int portClient, String address, int portServer) {
        this.uc = uc;
        this.portClient = portClient;
        this.portServer = portServer;
        this.address = address;
    }
}
