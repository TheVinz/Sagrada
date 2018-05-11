package client.view.network;

import common.ChangementVisitor;
import common.CommandVisitor;

import java.rmi.RemoteException;

public class ClientConnectionFactory {

    ClientConnection clientConnection;

    public ClientConnection getClientConnection(int i, ChangementVisitor changementVisitor){
        if(i == 0)
        {
            clientConnection = new RMIClientConnection("localhost", changementVisitor);
        }
        else{
           // clientConnection = new SocketClientConnection("localhost");
        }
        return clientConnection;
    }
}
