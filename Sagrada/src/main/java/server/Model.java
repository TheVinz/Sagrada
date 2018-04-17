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
}
