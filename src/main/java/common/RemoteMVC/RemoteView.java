package common.RemoteMVC;

import common.Notification;
import common.response.Response;
import common.viewchangement.Changement;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface RemoteView extends Remote {
    void change(Changement changement) throws RemoteException;
    void send(Response response) throws RemoteException;
    void notify(Notification notification) throws RemoteException;
    void ping() throws RemoteException;
}
