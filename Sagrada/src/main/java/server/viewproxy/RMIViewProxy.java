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

    @Override
    public synchronized void change(Changement changement) {
        try {
            if(!player.isSuspended()) remoteView.change(changement);
        } catch (RemoteException e) {
            System.out.print(player.getName() + " disconnected.\n>>>");
            super.suspendPlayer();
        }
    }

    @Override
    public synchronized void notify(Notification notification) {
        try {
            if(!player.isSuspended()) remoteView.notify(notification);
        } catch (RemoteException e) {
            System.out.println(player.getName() + " disconnected.\n>>>");
            super.suspendPlayer();
        }
    }

    @Override
    public synchronized void send(Response response) {
        try {
            if(!player.isSuspended()) remoteView.send(response);
        } catch (RemoteException e) {
            System.out.println(player.getName() + " disconnected.\n>>>");
            super.suspendPlayer();
        }
    }

    @Override
    public void closeConnection(){
        try {
            System.out.println("Closed");
            UnicastRemoteObject.unexportObject(this, true);
        } catch (NoSuchObjectException e) {
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void ping(){
        while(ping) {
            try {
                remoteView.ping();
            } catch (RemoteException e) {
                System.out.println(player.getName() + " disconnected.\n>>>");
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