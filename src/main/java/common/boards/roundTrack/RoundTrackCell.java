package common.boards.roundTrack;
import common.dice.Dice;
import common.boards.Cell;
import common.utilities.Color;

public class RoundTrackCell implements Cell {
    //Dado presente nella casella
    private Dice dice;
    private Color color;
    private int shade;
    @Override
    public Dice getDice(){}
    @Override
    public void move(Cell target) throws InvalidMoveException{}
    @Override
    public void put(Dice dice) throws InvalidMoveException{}
}
