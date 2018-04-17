package TestGUI.common;


import TestGUI.common.viewchangement.Changement;

public interface Observer {
    void update();
    void update(Changement change);

}
