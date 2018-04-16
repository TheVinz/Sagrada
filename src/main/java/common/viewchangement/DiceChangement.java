package common.viewchangement;

import common.Player;
import common.boards.windowframe.WindowFrameCell;
import common.dice.Dice;

public class DiceChangement implements Changement {
    private Player player;
    private WindowFrameCell windowFrameCell;
    public DiceChangement(Player player, WindowFrameCell windowFrameCell){
        this.windowFrameCell = windowFrameCell;
        this.player= player;



    }
}
