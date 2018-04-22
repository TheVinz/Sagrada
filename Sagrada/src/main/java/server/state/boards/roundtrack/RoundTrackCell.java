package server.state.boards.roundtrack;

import server.state.dice.Dice;
import server.state.boards.Cell;
import common.exceptions.InvalidMoveException;

public class RoundTrackCell implements Cell {
    //Dado presente nella casella
    private Dice dice;
    public RoundTrackCell(){
        super();
    }
    public RoundTrackCell(Dice dice){
        this.dice=dice;
    }

    public Dice getDice(){
        return this.dice;
    }

    public void move(Cell target) throws InvalidMoveException{
        if(this.isEmpty()) throw new InvalidMoveException("Empty cell");
        else{
            target.put(removeDice());
        }
    }

    public void put(Dice dice) throws InvalidMoveException{
        if(!this.isEmpty()) throw new InvalidMoveException("Already filled cell");
        else{
            this.dice=dice;
        }
    }

    public boolean isEmpty(){
        return this.dice==null;
    }

    @Override
    public Dice removeDice() {
        Dice result=this.dice;
        this.dice=null;
        return result;
    }
}
