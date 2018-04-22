package server.state.toolcards;

import common.exceptions.InvalidMoveException;
import server.Model;
import server.state.boards.Cell;
import server.state.dice.Dice;
import server.state.player.Player;

public class Martelletto extends ToolCard {
    public Martelletto(Model model) {
        super(model);
    }

    @Override
    public void start(Player player) throws InvalidMoveException {
        this.player=player;
        if(!player.isFirstMoveDone()) throw new InvalidMoveException("Can be used only on second move");
        else doAbility();
    }

    @Override
    public boolean hasNext(){
        return false;
    }

    @Override
    void doAbility() throws InvalidMoveException {
        for(Cell cell : model.getState().getDraftPool().getDraftPool()){
            if(cell.getDice()!=null){
                Dice dice=cell.removeDice();
                model.putDice(player, new Dice(dice.getColor()), cell);
            }
        }
        model.toolCardUsed(player, this);
    }
}
