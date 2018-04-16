package common;

import common.viewchangement.Changement;

public interface Observer {
    void update();
    void update(Changement change);

}
