package server.state.toolcards;

import server.Model;
import server.state.ModelObject;
import server.state.boards.draftpool.DraftPoolCell;
import server.state.boards.windowframe.WindowFrame;
import server.state.boards.windowframe.WindowFrameCell;
import server.state.dice.Dice;
import common.exceptions.InvalidMoveException;
import server.state.player.Player;
import server.state.utilities.GameRules;

import java.util.*;

@SuppressWarnings("Duplicates")
public class PennelloperPastaSalda extends ToolCard {

    private boolean playable;
    private boolean rerollDone;
    private DraftPoolCell poolCell;

    public PennelloperPastaSalda(Model model) {
        super(model);
    }

    @Override
    public void start(Player player) {
        expectedParameters=new ArrayDeque<>(3);
        parameters=new ArrayList<>(3);
        expectedParameters.add("WindowFrame");
        expectedParameters.add("WindowFrameCell");
        this.player=player;
        playable=false;
        rerollDone=false;
    }

    @Override
    public void setParameter(ModelObject o) throws InvalidMoveException {
        if(!rerollDone) {
            if(o.getType()!="DraftPoolCell") throw new InvalidMoveException("Wrong parameter");
            else {
                parameters.add(o);
                doAbility();
            }
        }
        else {
            if(o.getType()==expectedParameters.peek()){
                expectedParameters.poll();
                parameters.add(o);
                if(expectedParameters.isEmpty()) doAbility();
            }
        }
    }


    @Override
    void doAbility() throws InvalidMoveException {
        if(!rerollDone) {
            poolCell = (DraftPoolCell) parameters.get(0);
            Dice dice = poolCell.removeDice();
            dice = new Dice(dice.getColor());
            model.putDice(player, dice, poolCell);
            playable = verify(dice);
            rerollDone=true;
        }
        else{
            WindowFrame frame= (WindowFrame) parameters.get(1);
            WindowFrameCell cell= (WindowFrameCell) parameters.get(2);
            try {
                if (frame != player.getWindowFrame() || !GameRules.validAllCellRestriction(poolCell.getDice(), cell)
                        || !GameRules.validAllDiceRestriction(frame, poolCell.getDice(), cell)) {
                    parameters.remove(frame);
                    parameters.remove(cell);
                    expectedParameters.add("WindowFrame");
                    expectedParameters.add("WindowFrameCell");
                } else {
                    model.move(player, poolCell, cell);
                    playable=false;
                    model.toolCardUsed(player, this);
                }
            }
            catch (InvalidMoveException e){
                parameters.remove(frame);
                parameters.remove(cell);
                expectedParameters.add("WindowFrame");
                expectedParameters.add("WindowFrameCell");
            }
        }
    }

    @Override
    public boolean hasNext(){
        return rerollDone && playable;
    }

    private boolean verify(Dice dice) {
        for(int i=0; i<WindowFrame.ROWS; i++) {
            for(int j=0; j<WindowFrame.COLUMNS; j++){
                if (GameRules.validAllDiceRestriction(player.getWindowFrame(), dice, player.getWindowFrame().getCell(i,j))
                        && GameRules.validAllCellRestriction(dice, player.getWindowFrame().getCell(i,j)))
                    return true;
            }
        }
        return false;
    }


}