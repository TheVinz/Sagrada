package common.viewchangement;

import client.view.ChangementVisitor;

public class StartTurn implements Changement{
    @Override
    public void change(ChangementVisitor changementVisitor) {
        changementVisitor.change(this);
    }
}
