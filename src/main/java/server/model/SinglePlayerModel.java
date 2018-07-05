package server.model;

import server.GameManager;
import server.model.state.objectivecards.privateobjectivecards.PrivateObjectiveCard;
import server.model.state.objectivecards.publicobjectivecards.PublicObjectiveCard;
import server.model.state.player.Player;
import server.model.state.player.Points;
import server.model.state.player.SinglePlayer;
import server.model.state.toolcards.ToolCard;
import server.observer.Observer;
import server.observer.SinglePlayerObservable;
import server.viewproxy.ViewProxy;

/**
 * The <tt>SinglePlayerModel</tt> class represents the {@link Model} class for the single-player games. So this class just overrides
 * those methods required to implements single-player features.
 */
public class SinglePlayerModel extends Model implements SinglePlayerObservable {

    private Observer observer=null;
    SinglePlayer player = null;

    /**
     * Initializes a new Model setting the {@link GameManager} that will be notified at the end of the game.
     * @param gameManager the GameManager of the Server.
     */
    public SinglePlayerModel(GameManager gameManager){
        super(gameManager);
        getState().getDraftPool().increaseSizeByOne();
    }

    /**
     * Returns <code>true</code>.
     * @return <code>true</code>.
     */
    @Override
    public boolean isSingleplayer(){
        return true;
    }

    /**
     * Sets the {@link ViewProxy} for handling the connection with the remote client.
     * @param viewProxy the view proxy handling the connection with the remote player.
     * @param player the model player representing the remote player associated with the ViewProxy.
     */
    @Override
    public void addViewProxyPlayer(ViewProxy viewProxy, Player player)  {
        super.addViewProxyPlayer(viewProxy, player);
        observer = viewProxy;
    }

    /**
     * Sets the {@link Player} that will play this game.
     * @param name the player's username.
     * @return the Player of this game.
     */
    @Override
    public Player addPlayer(String name) {
        player = new SinglePlayer(name, 0);
        getState().addPlayer(player);
        return player;
    }


    /**
     * Notifies the player to select the game's difficulty.
     */
    @Override
    public void init() {
        started = true;
        player.setActive();
        notifyToolCardsChoice();
    }

    /**
     * Initializes the {@link ToolCard}s, depending on the difficulty, and notifies the player about the ToolCards,
     * {@link PublicObjectiveCard}s and {@link PrivateObjectiveCard}s drawn and about his Player representation on the model.
     * @param toolCards the number of tool cards chosen.
     */
    @Override
    public void toolCardsChoice(int toolCards) {
        player.setInactive();
        for(ToolCard t : getUtil().getToolCards(this, toolCards))
            getState().getToolCards().add(t);
        for(PublicObjectiveCard p : getUtil().getPublicObjectiveCards(true))
            getState().getPublicObjectiveCards().add(p);
        notifyToolCards();
        notifyObjectiveCards();
        notifyPlayers(getState().getPlayers().toArray(new Player[0]));
        startRound();

    }

    /**
     * Set the PrivateObjectiveCard chosen by the client, calculates his points and notifies him about the game result.
     * @param card the chosen private objective card index.
     */
    @Override
    public void privateCardChoice(int card) {
        PrivateObjectiveCard privateObjectiveCard;
        if(card == 1){
            privateObjectiveCard = player.getPrivateObjectiveCard(1);
            player.resetPrivateObjectiveCard();
            player.setPrivateObjectiveCard(privateObjectiveCard);
        }
        else{
            privateObjectiveCard = player.getPrivateObjectiveCard(0);
        }
        player.calculatePoints(super.getState());
        Points points = player.getPoints();
        int total = points.getFinalPoints();
        int target = super.getState().getRoundTrack().calculatePoints();
        observer.updateSinglePlayerEndGame(target, points, privateObjectiveCard);
        String message = total>target ? player.getName() + "won" : player.getName() + "lost";
        super.notifyGameManager(message);
    }

    /**
     * Notifies the PrivateObjectiveCards drawn to the player.
     */
    @Override
    public void notifyPrivateObjectiveCard() {
        observer.updatePrivateObjectiveCard(getUtil().getCard());
        observer.updatePrivateObjectiveCard(getUtil().getCard());
    }

    /**
     * Notifies the player he has to chose the difficulty.
     */
    public void notifyToolCardsChoice() {
        observer.updateToolCardsChoice();
    }

    /**
     * Ends this game and notifies the player the PrivateObjectiveCards he has to chose from.
     */
    @Override
    public void endGame() {
        super.getState().setGameFinished(true);
        observer.updatePrivateObjectiveCardChoice();
    }

    /**
     * Suspends the player and ends the game.
     * @param player the Player that is playing this game.
     */
    @Override
    public synchronized void suspendPlayer(Player player){
        if(player == this.player && !player.isSuspended()) {
            player.setSuspended(true);
            observer.updateSuspendPlayer(player);
            getState().setGameFinished(true);
            super.notifyGameManager(player.getName() + " disconnected.");
        }
    }

}
