package pao2_server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import pao2_server.servicesConcrete.Generator;

/**
 *
 * @author SHerbocopter
 */
public class Pao2_server {
    private static final int port = 63000;
    
    public static void main(String[] args) throws RemoteException {
        Generator generator = new Generator();
        Registry reg = LocateRegistry.createRegistry(port);
        reg.rebind("MatchMaking", generator);
        System.out.println("Server is on!");
    }
}
