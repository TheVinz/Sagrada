package TestGUI.model.boards.windowframe;
import TestGUI.model.boards.Cell;
import TestGUI.model.dice.Dice;
import TestGUI.model.exceptions.InvalidMoveException;
import TestGUI.model.utilities.CellObserver;
import javafx.scene.paint.Color;

public class WindowFrameCell implements Cell {
    //Dado presente nella casella
    private Dice dice;
    private Color color;
    private int shade;

    private CellObserver observer;
    private final int x;
    private final int y;

    WindowFrameCell(int x, int y){
        this.color=null;
        this.shade=0;
        this.x=x;
        this.y=y;
    }

/*    WindowFrameCell(Color color){
        this.color=color;
        this.shade=0;
    }

    WindowFrameCell(int shade){
        this.color=null;
        this.shade=shade;
    }*/

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String asImage(){
        if(!isEmpty()){
            return dice.asImage();
        }
        else{
            return "File:resources/icons/void.png";
        }
    }

    @Override
    public void addObserver(CellObserver observer) {
        this.observer=observer;
    }

    @Override
    public void notifyObserver() {
        if(observer != null) observer.updateWindowFrameCell(this.x, this.y);
    }

    public int getShade() {
        return shade;
    }

    public Color getColor() {
        return color;
    }

    public Dice getDice(){
        return this.dice;
    }

    public void move(Cell target) throws InvalidMoveException {
        if(this.isEmpty()) throw new InvalidMoveException("Empty cell");
        else{
            target.put(dice);
            dice=null;
            notifyObserver();
        }
    }

    public void put(Dice dice) throws InvalidMoveException{
        if(!this.isEmpty()) throw new InvalidMoveException("Already filled cell");
        else {
            this.dice=dice;
            notifyObserver();
        }
    }

    public boolean isEmpty(){
        return dice==null;
    }
}
