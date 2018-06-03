package server.model.state.toolcards;

import common.exceptions.InvalidMoveException;
import common.exceptions.WrongParameter;
import common.response.Response;
import server.model.Model;
import server.model.state.ModelObject.ModelObject;
import server.model.state.ModelObject.ModelType;
import server.model.state.boards.draftpool.DraftPoolCell;
import server.model.state.dice.Dice;
import server.model.state.player.Player;
import server.model.state.utilities.Color;

import java.util.ArrayDeque;
import java.util.ArrayList;

public class SinglePlayerToolCard extends ToolCard {

    private Player singlePlayer;
    private ToolCard toolCard=null;
    private DraftPoolCell selected = null;
    private Dice dice;

    public SinglePlayerToolCard(Model model) {
        super(model);
    }

    @Override
    public int getNumber() {
        return -1;
    }

    @Override
    public Color getColor() {
        return null;
    }

    @Override
    public void setParameter(ModelObject o) throws InvalidMoveException, WrongParameter {
       if(toolCard==null) {
           if (o.getType() != ModelType.TOOL_CARD)
               throw new InvalidMoveException("Invalid Parameter");
           else {
               toolCard = (ToolCard) o;
           }
       }
       else if(selected == null){
           if(o.getType() != ModelType.DRAFT_POOL_CELL)
               throw new InvalidMoveException("The dice must be in the draft pool");
           else {
               selected = (DraftPoolCell) o;
               dice = selected.getDice();
               doAbility();
           }
       }
       else {
           try{
               toolCard.setParameter(o);
           }
           catch(InvalidMoveException e){
               model.putDice(singlePlayer, dice, selected);
               throw e;
           }
       }
    }

    @Override
    public boolean hasNext(){
        if(toolCard==null)
            return true;
        else if(selected == null)
            return true;
        else
            return toolCard.hasNext();

    }

    @Override
    public Response next(){
        if(toolCard==null)
            return Response.TOOL_CARD;
        else if(selected == null)
            return Response.DRAFT_POOL_CELL;
        else
            return toolCard.next();
    }


    @Override
    public void start(Player singlePlayer) throws InvalidMoveException {
        if(model.getState().getDraftPool().isEmpty())
            throw new InvalidMoveException("Draft pool is empty");
        this.singlePlayer = singlePlayer;
        expectedParameters = new ArrayDeque<>(2);
        parameters = new ArrayList<>(2);
        expectedParameters.add(ModelType.TOOL_CARD);
        expectedParameters.add(ModelType.DRAFT_POOL_CELL);
    }


    @Override
    void doAbility() throws InvalidMoveException {
        if(selected.isEmpty())
            throw new InvalidMoveException("DraftPool Cell is empty");
        if(!toolCard.getColor().equals(selected.getDice().getColor()))
            throw new InvalidMoveException("Colors are differents");
        model.remove(singlePlayer, selected); //da cambiare nel model, fare in mdo che rimuova solo dopo che toolcard used
        try {
            toolCard.start(singlePlayer);
        }
        catch(InvalidMoveException e){
            model.putDice(singlePlayer, dice, selected);
            throw e;
        }

    }
}
