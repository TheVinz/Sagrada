package server.viewproxy;

import common.Notification;
import common.RemoteMVC.RemoteView;
import common.response.Response;
import common.viewchangement.Changement;

import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


public class RMIViewProxy extends ViewProxy {

    private RemoteView remoteView;


    public RMIViewProxy() throws RemoteException {
        super();
    }

    public synchronized void bindRemoteView(RemoteView remoteView) {
        this.remoteView = remoteView;
    }

    /**
     * Sends a changement of the state to the client.
     * @param changement the changement to send to the client.
     */
    @Override
    public synchronized void change(Changement changement) {
        try {
            if(!player.isSuspended()) remoteView.change(changement);
        } catch (RemoteException e) {
            super.suspendPlayer();
        }
    }

    /**
     * Notifies the client about the mistake of his last action.
     * @param notification the notification to send to the client.
     */
    @Override
    public synchronized void notify(Notification notification) {
        try {
            if(!player.isSuspended()) remoteView.notify(notification);
        } catch (RemoteException e) {
            super.suspendPlayer();
        }
    }

    /**
     * Informs the client about his next expected action in reaction to his last action.
     * @param response the response to send to the client.
     */
    @Override
    public synchronized void send(Response response) {
        try {
            if(!player.isSuspended()) remoteView.send(response);
        } catch (RemoteException e) {
            super.suspendPlayer();
        }
    }

    /**
     * Informs the client that he has been suspended by unexporting the instance of this class from the RMI registry.
     */
    @Override
    public void closeConnection(){
        try {
            UnicastRemoteObject.unexportObject(this, true);
        } catch (NoSuchObjectException e) {
            e.printStackTrace();
        }
    }

    /**
     * Pings the client in order to know if he's still connected, otherwise he will be suspended.
     */
    @Override
    public synchronized void ping(){
        while(isPing()) {
            try {
                remoteView.ping();
            } catch (RemoteException e) {
                super.suspendPlayer();
            }
            try {
                wait(5000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }
}