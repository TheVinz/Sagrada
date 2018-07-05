package server.controller;

import server.model.state.ModelObject.ModelObject;
import server.model.Model;
import server.model.state.ModelObject.ModelType;
import server.model.state.boards.windowframe.WindowFrameList;
import server.model.state.player.Player;
import server.model.state.utilities.Choice;

/**
 * The <tt>SelectingWindowFrame</tt> is the {@link PlayerState} handling the {@link Player} window frame choices.
 */
public class SelectingWindowFrame extends PlayerState{
    private WindowFrameList[] windowFrameLists;

    /**
     * Initializes a new <tt>SelectingWindowFrame</tt> setting the {@link Model} of the current game and the {@link Player} related
     * to the actions represented by this state.
     * @param player the player associated to this state.
     * @param model the model of the current game.
     */
    public SelectingWindowFrame(Player player, Model model, WindowFrameList[] windowFrameLists) {
        super(player, model);
        this.windowFrameLists=windowFrameLists;
    }

    /**
     * If the {@link ModelObject} is a {@link Choice} then the {@link WindowFrameList} with the indicated index is used to
     * create the Player's {@link server.model.state.boards.windowframe.WindowFrame}. The Choice's value is supposed to
     * be from 0 to 3.
     * @param modelObject the object representing the client input.
     * @return a new {@link SelectingToolCards} if the game is a single-player game, <code>this</code> if the input is not valid,
     * a new {@link WaitingState} otherwise.
     */
    @Override
    public PlayerState selectObject(ModelObject modelObject) {
        if(modelObject.getType()==ModelType.CHOICE) {
            model.windowFrameChoice(player, windowFrameLists[((Choice) modelObject).getChoice()]);
            if(model.isSingleplayer())
                return new SelectingToolCards(player, model);
            else
                return new WaitingState(player, model);
        }
        else return this;
    }

    @Override
    public void abort(){
        selectObject(new Choice(0));
    }

}
