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

    Player player;
    ToolCard toolCard=null;

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
       if(toolCard==null)
           super.setParameter(o);
       else
           toolCard.setParameter(o);
    }

    @Override
    public boolean hasNext(){
        if(toolCard==null)
            return super.hasNext();
        else
            return toolCard.hasNext();

    }

    @Override
    public Response next(){
        if(toolCard==null)
            return super.next();
        else
            return toolCard.next();
    }


    @Override
    public void start(Player player) throws InvalidMoveException {
        if(model.getState().getDraftPool().isEmpty())
            throw new InvalidMoveException("Draft pool is empty");
        this.player = player;
        expectedParameters = new ArrayDeque<>(2);
        parameters = new ArrayList<>(2);
        expectedParameters.add(ModelType.TOOL_CARD);
        expectedParameters.add(ModelType.DRAFT_POOL_CELL);
    }


    @Override
    void doAbility() throws InvalidMoveException, WrongParameter {
        toolCard = (ToolCard) parameters.get(0);
        DraftPoolCell draftPoolCell = (DraftPoolCell) parameters.get(1);
        if(draftPoolCell.isEmpty())
            throw new InvalidMoveException("DraftPool Cell is empty");
        if(!toolCard.getColor().equals(draftPoolCell.getDice().getColor()))
            throw new InvalidMoveException("Colors are differents");
        model.remove(player, draftPoolCell);
        toolCard.start(player);

    }
}
