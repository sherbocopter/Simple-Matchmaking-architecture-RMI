package pao2_server.servicesConcrete;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import networkUtils.Match;
import networkUtils.MatchedClients;
import services.IClientHandler;
import services.IGenerator;

/**
 *
 * @author SHerbocopter
 */
public class Generator extends UnicastRemoteObject implements IGenerator {
    public static ArrayList<ClientHandler> clients = new ArrayList<>();
    public static ArrayList<MatchedClients> queue = new ArrayList<>();
    private int matchMakingCount = 0;
    
    private final Object queueLock = new Object();
    
    public Generator() throws RemoteException {
        super();
    }
    
    @Override
    public IClientHandler getMyHandler() {
        try {
            ClientHandler ch = new ClientHandler(matchMakingCount++, this);
            clients.add(ch);
            return ch;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }
    
    public MatchedClients addClientInQueue(ClientHandler ch) {
        synchronized (queueLock) {
            ch.inQueue = true;
            MatchedClients mc = new MatchedClients();
            mc.client1 = ch;
            queue.add(mc);
            return mc;
        }
    }
    
    public void removeClientFromQueue(MatchedClients mc) {
        synchronized (queueLock) {
            mc.client1.inQueue = false;
            mc.client2.inQueue = false;
            queue.remove(mc);
        }
    }

    public ClientHandler findMatch(ClientHandler ch) {
        synchronized (queueLock) {
            
            for (int i = 0; i < queue.size(); ++i) {
                MatchedClients mp = queue.get(i);
                if (mp.client2 != null) continue;
                
                if (playersMatch(ch, mp.client1)) {
                    mp.client2 = ch;
                    return mp.client1;
                }
            }
            
            return null;
        }
    }
    
    private static boolean playersMatch(ClientHandler mm1, ClientHandler mm2) {
        int level1 = mm1.uc.level;
        int min1 = mm1.mms.minLevel;
        int max1 = mm1.mms.maxLevel;
        
        int level2 = mm2.uc.level;
        int min2 = mm2.mms.minLevel;
        int max2 = mm2.mms.maxLevel;
        
        return (inInterval(level1, min2, max2) && inInterval(level2, min1, max1));
    }
    
    private static boolean inInterval(int a, int l, int r) {
        return (l <= a && a <= r);
    }
}
