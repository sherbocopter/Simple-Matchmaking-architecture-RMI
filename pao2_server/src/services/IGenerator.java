package services;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author SHerbocopter
 */
public interface IGenerator extends Remote {
    public IClientHandler getMyHandler() throws RemoteException;
}
