package common.RemoteMVC;

import common.Notification;
import common.response.Response;
import common.viewchangement.Changement;
import server.model.state.ModelObject.ModelType;

import java.rmi.Remote;
import java.rmi.RemoteException;

/*I vari param rappresentano le coordinate:
*   DraftPoolCell  ( index )
*   RoundTrackCell ( round , index )
*   WindowFrameCell ( row , column )
*Rigorosamente nell' ordine descritto
*   */

public interface RemoteView extends Remote {
    void change(Changement changement) throws RemoteException;
    void send(Response response) throws RemoteException;
    void notify(Notification notification) throws RemoteException;
    void ping() throws RemoteException;
}
