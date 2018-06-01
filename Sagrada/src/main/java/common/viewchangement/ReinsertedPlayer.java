package common.viewchangement;

import client.view.Changer;

public class ReinsertedPlayer extends Changement {
    private final int idPlayer;

    public ReinsertedPlayer(int idPlayer){

        this.idPlayer = idPlayer;
    }

    public int getIdPlayer() {
        return idPlayer;
    }

    public void change(Changer changer){
        changer.change(this);
    }
}
