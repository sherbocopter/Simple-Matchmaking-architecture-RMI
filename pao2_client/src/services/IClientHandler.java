package services;

import java.rmi.Remote;
import java.rmi.RemoteException;
import networkUtils.Match;
import networkUtils.MatchMakingSettings;
import networkUtils.UserCredentials;

/**
 *
 * @author SHerbocopter
 */
public interface IClientHandler extends Remote {
    public void login(UserCredentials uc) throws RemoteException;
    public Match wantsToPlay(MatchMakingSettings settings) throws RemoteException;
}
