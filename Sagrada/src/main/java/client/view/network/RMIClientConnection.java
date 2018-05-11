package client.view.network;

import common.ChangementVisitor;
import common.CommandVisitor;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIClientConnection implements ClientConnection {
    private final int PORT = 111;
    private CommandVisitor commandVisitor;

    public RMIClientConnection(String host, ChangementVisitor changementVisitor) {
        try {
            Registry registry = LocateRegistry.getRegistry(host, PORT);
            registry.rebind("changementVisitor", changementVisitor);
            commandVisitor = (CommandVisitor) registry.lookup("serverController");
        }
        catch(RemoteException e){

        }
        catch (NotBoundException e){

        }


    }

    @Override
    public CommandVisitor getCommandVisitor() {
        return commandVisitor;
    }
}
