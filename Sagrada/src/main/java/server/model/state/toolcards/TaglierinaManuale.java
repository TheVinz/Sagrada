package server.model.state.toolcards;

import client.view.cli.cliphasestate.WindowFrameChoice;
import common.exceptions.InvalidMoveException;
import common.response.Response;
import javafx.scene.layout.Pane;
import server.model.Model;
import server.model.state.ModelObject.ModelObject;
import server.model.state.boards.Cell;
import server.model.state.utilities.Choice;
import server.model.state.utilities.Color;
import server.model.state.utilities.GameRules;
import server.model.state.boards.roundtrack.RoundTrackCell;
import server.model.state.boards.windowframe.WindowFrame;
import server.model.state.boards.windowframe.WindowFrameCell;
import server.model.state.dice.Dice;
import server.model.state.player.Player;

import java.util.ArrayDeque;
import java.util.ArrayList;

import static server.model.state.ModelObject.ModelType.*;

@SuppressWarnings("Duplicates")
public class TaglierinaManuale extends ToolCard {

    public static final int TWO_MOVES=3;
    public static final int ONE_MOVE=2;

    private boolean firstMoveDone;
    private boolean playable;

    public TaglierinaManuale(Model model) {
        super(model);
    }

    @Override
    public int getNumber() {
        return 12;
    }

    @Override
    public void start(Player player) throws InvalidMoveException {
        expectedParameters=new ArrayDeque<>(10);
        parameters=new ArrayList<>(10);
        expectedParameters.add(ROUND_TRACK_CELL);
        expectedParameters.add(WINDOW_FRAME);
        expectedParameters.add(WINDOW_FRAME_CELL);
        expectedParameters.add(WINDOW_FRAME);
        expectedParameters.add(WINDOW_FRAME_CELL);
        expectedParameters.add(CHOICE);
        this.player=player;
        playable=true;
        firstMoveDone=false;
    }

    @Override
    public boolean hasNext(){
        if(!firstMoveDone) return true;
        else if(parameters.size() < 6) return playable;
        else if(parameters.size() == 6)
            if (((Choice) parameters.get(5)).getChoice() == TWO_MOVES){
                expectedParameters.add(WINDOW_FRAME);
                expectedParameters.add(WINDOW_FRAME_CELL);
                expectedParameters.add(WINDOW_FRAME);
                expectedParameters.add(WINDOW_FRAME_CELL);
                return playable;
            }
            else return false;
        else if(parameters.size()<10)
            return playable;
        else return false;
    }

    @Override
    public void setParameter(ModelObject o) throws InvalidMoveException {
        if(o.getType() != expectedParameters.poll())
            throw new InvalidMoveException("Wrong parameter");
        else parameters.add(o);
        if(o.getType()==ROUND_TRACK_CELL && !isPlayable())
            throw new InvalidMoveException("No available moves");
        if(expectedParameters.peek() == CHOICE)
            doAbility();
        if(parameters.size() == 6 && ((Choice) parameters.get(5)).getChoice() == ONE_MOVE)
            model.toolCardUsed(player, this);
        if(parameters.size() == 10)
            doAbility();
    }

    @Override
    void doAbility() throws InvalidMoveException {
        RoundTrackCell roundTrackCell= (RoundTrackCell) parameters.get(0);
        if(!firstMoveDone) {
            WindowFrame sourceFrme = (WindowFrame) parameters.get(1);
            WindowFrameCell source = (WindowFrameCell) parameters.get(2);
            WindowFrame targetFrame = (WindowFrame) parameters.get(3);
            WindowFrameCell target = (WindowFrameCell) parameters.get(4);
            Dice dice = source.removeDice();
            if(roundTrackCell.getDice().getColor() != dice.getColor()){
                source.put(dice);
                throw new InvalidMoveException("Selected color and dice color must be equals");
            }
            else if(!GameRules.validAllCellRestriction(dice, target)){
                source.put(dice);
                throw new InvalidMoveException("Cell restriction must be respected");
            }
            else if(!GameRules.validAllDiceRestriction(targetFrame, dice, target)){
                source.put(dice);
                throw new InvalidMoveException("Dice restrictions must be respected");
            }
            else {
                source.put(dice);
                model.move(player, source, target);
            }
            firstMoveDone=true;
            playable = isPlayable();
            if(!playable)
                model.toolCardUsed(player, this);
        }
        else{
            WindowFrame sourceFrme = (WindowFrame) parameters.get(6);
            WindowFrameCell source = (WindowFrameCell) parameters.get(7);
            WindowFrame targetFrame = (WindowFrame) parameters.get(8);
            WindowFrameCell target = (WindowFrameCell) parameters.get(9);
            Dice dice = source.removeDice();
            if(roundTrackCell.getDice().getColor() != dice.getColor()){
                source.put(dice);
                model.toolCardUsed(player, this);
                throw new InvalidMoveException("Selected color and dice color must be equals");
            }
            else if(!GameRules.validAllCellRestriction(dice, target)){
                source.put(dice);
                model.toolCardUsed(player, this);
                throw new InvalidMoveException("Cell restriction must be respected");
            }
            else if(!GameRules.validAllDiceRestriction(targetFrame, dice, target)){
                source.put(dice);
                model.toolCardUsed(player, this);
                throw new InvalidMoveException("Dice restrictions must be respected");
            }
            else {
                source.put(dice);
                model.move(player, source, target);
                model.toolCardUsed(player, this);
            }
        }
    }

    @Override
    public Response next() {   //da controllare
        if(parameters.size()==0)
            return Response.ROUND_TRACK_CELL;
        else if(parameters.size()==1 && playable)
            return Response.WINDOW_FRAME_MOVE;
        else if(parameters.size()==5 && playable)
            return Response.TAGLIERINA_MANUALE_CHOICE;
        else if(parameters.size()==6 && ((Choice) parameters.get(5)).getChoice() == TWO_MOVES && playable)
            return Response.WINDOW_FRAME_MOVE;
        else return null;
    }

    private boolean isPlayable(){
        WindowFrame frame = player.getWindowFrame();
        for(int i=0; i<WindowFrame.ROWS; i++) {
            for (int j = 0; j < WindowFrame.COLUMNS; j++) {
                Dice dice = null;
                try {
                    dice = frame.getCell(i, j).removeDice();
                    if (dice.getColor() == ((Cell) parameters.get(0)).getDice().getColor())
                        for (int x = 0; x < WindowFrame.ROWS; x++) {
                            for (int y = 0; y < WindowFrame.COLUMNS; y++) {
                                if (GameRules.validAllDiceRestriction(frame, dice, frame.getCell(x, y))
                                        && GameRules.validAllCellRestriction(dice, frame.getCell(x, y))) {
                                    frame.getCell(i, j).put(dice);
                                    return true;
                                }
                            }
                        }
                    frame.getCell(i, j).put(dice);
                } catch (InvalidMoveException e) {
                    dice = null;
                }
            }
        }
        return false;
    }
}
