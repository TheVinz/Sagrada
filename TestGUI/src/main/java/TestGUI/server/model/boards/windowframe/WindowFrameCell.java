package TestGUI.server.model.boards.windowframe;
import TestGUI.server.model.boards.Cell;
import TestGUI.server.model.dice.Dice;
import TestGUI.server.model.exceptions.InvalidMoveException;
import javafx.scene.paint.Color;

public class WindowFrameCell implements Cell {
    //Dado presente nella casella
    private Dice dice;
    private Color color;
    private int shade;

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
    public Dice removeDice() throws InvalidMoveException {
        if(this.dice==null) throw new InvalidMoveException("Empty cell.");
        Dice dice=this.dice;
        this.dice=null;
        return dice;
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
        }
    }

    public void put(Dice dice) throws InvalidMoveException{
        if(!this.isEmpty()) throw new InvalidMoveException("Already filled cell");
        else {
            this.dice=dice;
        }
    }

    public boolean isEmpty(){
        return dice==null;
    }
}
