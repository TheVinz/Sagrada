package server.model;

import server.model.state.objectivecards.publicobjectivecards.PublicObjectiveCard;
import server.model.state.player.Player;
import server.model.state.player.SinglePlayer;
import server.model.state.toolcards.ToolCard;
import server.observer.Observer;
import server.observer.SinglePlayerObservable;
import server.viewproxy.RMIViewProxy;

public class SinglePlayerModel extends Model implements SinglePlayerObservable {

    private Observer observer=null;
    SinglePlayer player = null;

    public SinglePlayerModel(){
        super();
        getState().getDraftPool().increaseSizeByOne();
    }

    @Override
    public RMIViewProxy addRMIPlayer(String name) throws Exception {
        RMIViewProxy rmiViewProxy = super.addRMIPlayer(name);
        observer = rmiViewProxy;
        return rmiViewProxy;
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
    public void notifyPrivateObjectiveCard() {
        observer.updatePrivateObjectiveCard(getUtil().getCard());
        observer.updatePrivateObjectiveCard(getUtil().getCard());
    }

    public void notifyToolCardsChoice() {
        observer.updateToolCardsChoice();
    }
}
