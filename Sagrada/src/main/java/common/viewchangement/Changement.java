package common.viewchangement;

import client.view.Changer;
import common.RemoteMVC.RemoteView;

import java.io.Serializable;
import java.rmi.RemoteException;

public abstract class Changement implements Serializable {

    public abstract void change(Changer changer);
}
