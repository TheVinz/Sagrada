package server;

import common.Observable;
import common.Player;
import common.boards.windowframe.WindowFrameCell;
import common.dice.Dice;
import common.exceptions.InvalidMoveException;
import common.viewchangement.DiceChangement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Model extends Observable {
    private State state;
    private Map<Player,PlayerModel> playerStates = new HashMap<Player,PlayerModel>();
    public void pinzaSgrossatrice(List<Object> parameters) throws InvalidMoveException{
        Player player = (Player) parameters.get(0);
        WindowFrameCell windowFrameCell = (WindowFrameCell) parameters.get(1);
        Integer choice = (Integer) parameters.get(2);
        Dice dice = windowFrameCell.getDice();
        if(choice.equals(0))
            dice.increase();
        else
            dice.decrease();
        notifyObservers(new DiceChangement(player, windowFrameCell));



    }



}
