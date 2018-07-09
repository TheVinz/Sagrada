package common.RemoteMVC;

import common.Notification;
import common.response.Response;
import common.viewchangement.Changement;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * The <tt>RemoteView</tt> interface is implemented by all the classes the server uses to send data to the client. Each <tt>RemoteView</tt>
 * implements different methods to translate server's message into message to the user depending if the user is playing with a GUI client or CLI.
 */
public interface RemoteView extends Remote {
    /**
     * Applies the changes described into the given {@link Changement}.
     * @param changement the server's Changement.
     * @throws RemoteException if a connection error occurs.
     */
    void change(Changement changement) throws RemoteException;

    /**
     * Notifies the user of a message sent by the server's {@link server.controller.Controller}, mainly used for input requests.
     * @param response the response sent by the server.
     * @throws RemoteException if a connection error occurs.
     */
    void send(Response response) throws RemoteException;

    /**
     * Notifies the user of a message sent by the server's {@link server.controller.Controller}, mainly used for error messages.
     * @param notification the Notification sent by the server.
     * @throws RemoteException if a connection error occurs.
     */
    void notify(Notification notification) throws RemoteException;

    /**
     * Ping method used to spot an RMI disconnection.
     * @throws RemoteException if a connection error occurs.
     */
    void ping() throws RemoteException;
}
