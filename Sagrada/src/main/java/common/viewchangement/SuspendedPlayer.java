package common.viewchangement;

import common.Changer;

public class SuspendedPlayer extends Changement {
    private final int playerId;

    public SuspendedPlayer(int playerId){

        this.playerId = playerId;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void change(Changer changer){
        changer.change(this);
    }
}
