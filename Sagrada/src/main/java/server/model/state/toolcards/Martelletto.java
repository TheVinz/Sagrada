package server.model.state.toolcards;

import common.exceptions.InvalidMoveException;
import server.model.Model;
import server.model.state.boards.Cell;
import server.model.state.dice.Dice;
import server.model.state.player.Player;

public class Martelletto extends ToolCard {
    public Martelletto(Model model) {
        super(model);
    }

    @Override
    public int getNumber() {
        return 7;
    }

    @Override
    public void start(Player player) throws InvalidMoveException {
        this.player=player;
        if(!player.isSecondTurn()) throw new InvalidMoveException("Can be used only on second move");
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
