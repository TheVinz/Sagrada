package common.viewchangement;

public class StartTurn implements Changement{
    @Override
    public int getType() {
        return ChangementTypes.START_TURN;
    }
}
