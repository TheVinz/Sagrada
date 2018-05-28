package server.model.state.toolcards;

import common.exceptions.InvalidMoveException;
import common.exceptions.WrongParameter;
import common.response.Response;
import server.model.Model;
import server.model.state.ModelObject.ModelObject;
import server.model.state.utilities.GameRules;
import server.model.state.boards.windowframe.WindowFrame;
import server.model.state.boards.windowframe.WindowFrameCell;
import server.model.state.dice.Dice;
import server.model.state.player.Player;

import java.util.ArrayDeque;
import java.util.ArrayList;

import static server.model.state.ModelObject.ModelType.*;

public class Lathekin extends ToolCard {

    private boolean firstMoveDone;
    private WindowFrameCell possibleSource;
    private WindowFrameCell possibleTarget;

    public Lathekin(Model model) {
        super(model);
    }

    @Override
    public int getNumber() {
        return 4;
    }

    @Override
    public void start(Player player) {
        expectedParameters=new ArrayDeque<>(8);
        parameters=new ArrayList<>(8);
        expectedParameters.add(WINDOW_FRAME);
        expectedParameters.add(WINDOW_FRAME_CELL);
        expectedParameters.add(WINDOW_FRAME);
        expectedParameters.add(WINDOW_FRAME_CELL);
        expectedParameters.add(WINDOW_FRAME);
        expectedParameters.add(WINDOW_FRAME_CELL);
        expectedParameters.add(WINDOW_FRAME);
        expectedParameters.add(WINDOW_FRAME_CELL);
        this.player=player;
        firstMoveDone = false;
        possibleSource = null;
        possibleTarget = null;
    }

    @Override
    public void setParameter(ModelObject o) throws InvalidMoveException, WrongParameter {
        if(!firstMoveDone) {
            if(o.getType()!=expectedParameters.peek()) throw new WrongParameter("Wrong parameter");
            else {
                expectedParameters.poll();
                parameters.add(o);
            }
            if(parameters.size()==4){
                doAbility();
            }
        }
        else{
            if(o.getType()!=expectedParameters.peek()) throw new WrongParameter("Wrong parameter");
            else {
                expectedParameters.poll();
                parameters.add(o);
                if(expectedParameters.isEmpty()) doAbility();
            }
        }
    }

    private void refillParameters(){
        parameters.remove(7);
        parameters.remove(6);
        parameters.remove(5);
        parameters.remove(4);
        expectedParameters.add(WINDOW_FRAME);
        expectedParameters.add(WINDOW_FRAME_CELL);
        expectedParameters.add(WINDOW_FRAME);
        expectedParameters.add(WINDOW_FRAME_CELL);
    }

    @Override
    void doAbility() throws InvalidMoveException, WrongParameter {
        WindowFrame firstSourceFrame = null;
        WindowFrameCell firstSource = null;
        WindowFrame firstTargetFrame = null;
        WindowFrameCell firstTarget = null;
        WindowFrame secondSourceFrame = null;
        WindowFrameCell secondSource = null;
        WindowFrame secondtargetFrame = null;
        WindowFrameCell secondTarget = null;

        if(!firstMoveDone){
            firstSourceFrame= (WindowFrame) parameters.get(0);
            firstSource= (WindowFrameCell) parameters.get(1);
            firstTargetFrame= (WindowFrame) parameters.get(2);
            firstTarget= (WindowFrameCell) parameters.get(3);

            if(firstSourceFrame!=player.getWindowFrame())
                throw new InvalidMoveException("Dice must be on your same frame");

            if(firstSource == firstTarget)
                throw new InvalidMoveException("Cells must be differents");

            Dice dice = firstSource.removeDice();

            if(GameRules.validAllDiceRestriction(player.getWindowFrame(), dice, firstTarget) &&
                    GameRules.validAllCellRestriction(dice, firstTarget)){
                firstTarget.put(dice); //sarà seguito da firstTarget.move(firstSource) serve alla verify
            }
            else{
                firstSource.put(dice);
                throw new InvalidMoveException("Invalid move");
            }

            if(!verify(firstTarget)){
                firstTarget.move(firstSource);
                throw new InvalidMoveException("You will never be able to finish this tool card!");
            }
            else{
                firstTarget.move(firstSource);
                model.move(player, firstSource, firstTarget);
                firstMoveDone = true;
            }
        }else {
            secondSourceFrame = (WindowFrame) parameters.get(4);
            secondSource = (WindowFrameCell) parameters.get(5);
            secondtargetFrame = (WindowFrame) parameters.get(6);
            secondTarget = (WindowFrameCell) parameters.get(7);

            if(secondSourceFrame!=player.getWindowFrame())
                throw new InvalidMoveException("Dice must be on your same frame");

            if(secondSource == secondTarget){
                refillParameters();
                throw new WrongParameter("Cells must be differents");
            }

            if(secondSource == firstTarget)
            {
                refillParameters();
                throw new WrongParameter("Cannot move two time the same dice");
            }

            Dice dice = null;

            try{
                dice = secondSource.removeDice();
            }catch(InvalidMoveException e){
                refillParameters();
                throw new WrongParameter(e.getMessage());
            }


            if(GameRules.validAllDiceRestriction(player.getWindowFrame(), dice, secondTarget) &&
                    GameRules.validAllCellRestriction(dice, secondTarget)){
                secondSource.put(dice);
            }
            else{
                secondSource.put(dice);
                refillParameters();
                throw new WrongParameter("All move restrictions must be respected");
            }

            model.move(player, secondSource, secondTarget);
            model.toolCardUsed(player, this);
        }
    }

    private boolean verify(WindowFrameCell firstTarget) {
        WindowFrame windowFrame = player.getWindowFrame();
        for (int h = 0; h < WindowFrame.ROWS; h++) {
            for (int k = 0; k < WindowFrame.COLUMNS; k++) {
                if(firstTarget.getRow() == h &&  firstTarget.getColumnn() == k)
                    continue;

                WindowFrameCell source = windowFrame.getCell(h,k);
                Dice dice = null;

                try{
                    dice = source.removeDice();
                }catch (InvalidMoveException e){
                    continue;
                }

                for (int i = 0; i < WindowFrame.ROWS; i++) {
                    for (int j = 0; j < WindowFrame.COLUMNS; j++) {
                        if(i==h && j==k)
                            continue;
                        if (GameRules.validAllDiceRestriction(windowFrame, dice, windowFrame.getCell(i,j)) &&
                                GameRules.validAllCellRestriction(dice, windowFrame.getCell(i,j))){
                            try{
                                source.put(dice);
                            } catch (InvalidMoveException e){}
                            possibleSource = windowFrame.getCell(h,k);
                            possibleTarget = windowFrame.getCell(i,j);
                            return true;
                        }
                    }
                }
                try{
                    source.put(dice);
                } catch (InvalidMoveException e){}
            }
        }

        return false;
    }

    @Override
    public Response next() {   //trascinamento
        if(expectedParameters.peek().equals(WINDOW_FRAME)&&(expectedParameters.size()==8||expectedParameters.size()==4))
            return Response.WINDOW_FRAME_MOVE;
        else
            return null;
    }

    @Override
    public void abort(){
        try{
        model.move(player, possibleSource, possibleTarget);}
        catch (InvalidMoveException e){};
    }
}
