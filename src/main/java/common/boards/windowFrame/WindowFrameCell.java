package common.boards.windowFrame;
import common.boards.*;
import common.utilities.Color;
import common.dice.Dice;

public class WindowFrameCell implements Cell {
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
