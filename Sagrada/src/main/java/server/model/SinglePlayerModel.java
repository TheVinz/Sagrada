package server.model;

import server.GameManager;
import server.model.state.objectivecards.privateobjectivecards.PrivateObjectiveCard;
import server.model.state.objectivecards.publicobjectivecards.PublicObjectiveCard;
import server.model.state.player.Player;
import server.model.state.player.Points;
import server.model.state.player.SinglePlayer;
import server.model.state.toolcards.ToolCard;
import server.model.state.utilities.PointsComparator;
import server.observer.Observer;
import server.observer.SinglePlayerObservable;
import server.viewproxy.RMIViewProxy;
import server.viewproxy.ViewProxy;

import java.util.ArrayList;
import java.util.List;

public class SinglePlayerModel extends Model implements SinglePlayerObservable {

    private Observer observer=null;
    SinglePlayer player = null;

    public SinglePlayerModel(GameManager gameManager){
        super(gameManager);
        getState().getDraftPool().increaseSizeByOne();
    }

    @Override
    public boolean isSingleplayer(){
        return true;
    }

    @Override
    public void addViewProxyPlayer(ViewProxy viewProxy, Player player)  {
        super.addViewProxyPlayer(viewProxy, player);
        observer = viewProxy;
    }

    @Override
    public Player addPlayer(String name) { //da fare synchronized nel caso più giocatori si connettano contemporaneamente?
        player = new SinglePlayer(name, 0);
        getState().addPlayer(player);
        return player;
    }


    @Override
    public void init() {
        started = true;
        player.setActive();
        notifyToolCardsChoice();
    }

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

    @Override
    public void notifyPrivateObjectiveCard() {
        observer.updatePrivateObjectiveCard(getUtil().getCard());
        observer.updatePrivateObjectiveCard(getUtil().getCard());
    }

    public void notifyToolCardsChoice() {
        observer.updateToolCardsChoice();
    }

    @Override
    public void endGame() {
        super.getState().setGameFinished(true);
        observer.updatePrivateObjectiveCardChoice();
    }

    @Override
    public synchronized void suspendPlayer(Player player){
        if(player == this.player && !player.isSuspended()) {
            player.setSuspended(true);
            getState().setGameFinished(true);
            super.notifyGameManager(player.getName() + " disconnected.");
        }
    }

}
