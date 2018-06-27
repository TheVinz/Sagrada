package server.model.state.toolcards;

import common.exceptions.InvalidMoveException;
import server.model.Model;
import server.model.state.boards.Cell;
import server.model.state.dice.Dice;
import server.model.state.player.Player;
import server.model.state.utilities.Color;

public class Martelletto extends ToolCard {
    public Martelletto(Model model) {
        super(model);

    }

    @Override
    public int getNumber() {
        return 7;
    }

    @Override
    public Color getColor() {
        return Color.BLUE;
    }

    @Override
    public void start(Player player) throws InvalidMoveException {
        if(model.getState().getDraftPool().isEmpty())
            throw new InvalidMoveException("Draft pool is empty");
        if(!player.isSecondTurn())
            throw new InvalidMoveException("Can be used only on second move");
        if(player.isDiceMoved())
            throw new InvalidMoveException("Can be used only before placing a dice");
        this.player=player;
        doAbility();
    }

    @Override
    public boolean hasNext(){
        return false;
    }

    @Override
    void doAbility() throws InvalidMoveException {
        for(Cell cell : model.getState().getDraftPool().getDraftPool()){
            if(!cell.isEmpty()){
                Dice dice=cell.removeDice();
                model.putDice(player, new Dice(dice.getColor()), cell);
            }
        }
        model.toolCardUsed(player, this);
    }
}
