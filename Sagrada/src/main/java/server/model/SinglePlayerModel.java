package server.model;

import server.model.state.objectivecards.privateobjectivecards.PrivateObjectiveCard;
import server.model.state.objectivecards.publicobjectivecards.PublicObjectiveCard;
import server.model.state.player.Player;
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

    public SinglePlayerModel(){
        super();
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
    public Player addPlayer(String name, int id) throws Exception{ //da fare synchronized nel caso più giocatori si connettano contemporaneamente?
        if(!getState().getPlayers().isEmpty()) throw new Exception("The game is full");
        player = new SinglePlayer(name, id);
        getState().addPlayer(player);
        return player;
    }


    @Override
    public void init() {
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
        if(card == 1){
            PrivateObjectiveCard privateObjectiveCard = player.getPrivateObjectiveCard(1);
            player.resetPrivateObjectiveCard();
            player.setPrivateObjectiveCard(privateObjectiveCard);
        }
        player.calculatePoints(super.getState());
        observer.updateSinglePlayerEndGame(super.getState().getRoundTrack().calculatePoints(), player.getPoints());
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

}
