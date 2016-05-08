package pao2_client;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import networkUtils.Match;
import networkUtils.MatchMakingSettings;
import networkUtils.UserCredentials;
import p2pUtils.P2PGame;
import services.IGenerator;
import services.IClientHandler;

/**
 *
 * @author SHerbocopter
 */
public class Pao2_client {
    private static final int port = 63000;
    private static final String IP = "localhost";
    
    public static void main(String[] args) throws Exception {
        Registry reg = LocateRegistry.getRegistry(IP, port);
        IGenerator srv = (IGenerator)reg.lookup("MatchMaking");
        IClientHandler mm = srv.getMyHandler();
        
        UserCredentials uc = readCredentials();
        mm.login(uc);
        Match match = sendMMRequest(mm);
        
        P2PGame game = new P2PGame(match);
    }
    
    public static UserCredentials readCredentials() {
        UserCredentials uc = new UserCredentials();
        Scanner scan = new Scanner(System.in);
        String line;
        
        System.out.println(">Enter credentials");
        
        System.out.print("\tUsername: ");
        line = scan.nextLine();
        uc.username = line;
        
        System.out.print("\tLevel: ");
        line = scan.nextLine();
        uc.level = Integer.parseInt(line);
        
        return uc;
    }
    
    public static Match sendMMRequest(IClientHandler mm) throws Exception {
        System.out.println(">Enter MM specs");
        
        Scanner scan = new Scanner(System.in);
        String line;
        int minLevel;
        int maxLevel;
        
        System.out.print("\tminLevel: ");
        line = scan.nextLine();
        minLevel = Integer.parseInt(line);
        
        System.out.print("\tmaxLevel: ");
        line = scan.nextLine();
        maxLevel = Integer.parseInt(line);
        
        System.out.println(">Attempting MM");
        
        MatchMakingSettings mms = new MatchMakingSettings(minLevel, maxLevel);
        
        System.out.println(">Queuing for match");
       
        Match match = mm.wantsToPlay(mms);
        System.out.format(">Matched with %s(%d)"
                        + "\n\tportClient: %d"
                        + "\n\tIP: %s"
                        + "\n\tportServer: %d\n",
                                match.uc.username, match.uc.level,
                                match.portClient,
                                match.address, match.portServer);
        
        return match;
    }
}
