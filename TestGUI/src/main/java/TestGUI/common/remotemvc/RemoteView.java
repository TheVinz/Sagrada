package TestGUI.common.remotemvc;

import TestGUI.common.Observer;
import TestGUI.common.viewchangement.Changement;

public interface RemoteView {
    void sendChangement(Changement c);
}
