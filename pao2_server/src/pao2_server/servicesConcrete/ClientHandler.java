package pao2_server.servicesConcrete;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;
import networkUtils.Match;
import networkUtils.MatchMakingSettings;
import networkUtils.MatchedClients;
import networkUtils.UserCredentials;
import services.IClientHandler;

/**
 *
 * @author SHerbocopter
 */
public class ClientHandler extends UnicastRemoteObject implements IClientHandler {
    private int matchMakingId;
    private Generator generator;
    public boolean inQueue = false;
    public UserCredentials uc = null;
    public MatchMakingSettings mms = null;
    public String ipAddress;
    
    private static int portMatchServer = 9091;
    private static int portMatchClient = 9092;
    
    public ClientHandler(int matchMakingId, Generator generator) throws Exception {
        super();
        
        this.matchMakingId = matchMakingId;
        this.generator = generator;
        ipAddress = getClientHost();
    }
    
    @Override
    public void login(UserCredentials uc) throws RemoteException {
        this.uc = uc;
    }

    @Override
    public Match wantsToPlay(MatchMakingSettings settings) {
        System.out.format(">Player %s(%d) wants to MM (%d, %d)\n",
                uc.username, uc.level, settings.minLevel, settings.maxLevel);
        
        this.mms = settings;
        ClientHandler othermm = generator.findMatch(this);
        Match match = null;
        
        if (othermm == null) {
            MatchedClients mc = generator.addClientInQueue(this);
            
            while (mc.client2 == null) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) { }
            }
            
            
            match = new Match(mc.client2.uc, portMatchServer, mc.client2.ipAddress, portMatchClient);
            
            generator.removeClientFromQueue(mc);
        }
        else {
            match = new Match(othermm.uc, portMatchClient, othermm.ipAddress, portMatchServer);
        }
        
        return match;
    }
}
