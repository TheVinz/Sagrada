package common.remotemvc;

import common.viewchangement.Changement;

public interface RemoteModel {
    void receiveChangement(Changement change);
}
