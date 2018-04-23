package client;

import common.remotemvc.RemoteModel;
import common.viewchangement.Changement;

public class ModelProxy implements RemoteModel {
    View view;

    @Override
    public void receiveChangement(Changement change) {
    }
}
