package common.viewchangement;

import common.Changer;

import java.io.Serializable;

public abstract class Changement implements Serializable {

    public abstract void change(Changer changer);
}
