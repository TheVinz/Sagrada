package server.model.state;

import server.model.state.player.Player;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.List;

public class RoundManager implements Iterator<Player> {

    private ArrayDeque<Player> players;
    private Iterator<Player> routine;
    private Iterator<Player> descendingRoutine;

    public RoundManager(List<Player> players){
        this.players=new ArrayDeque<>(players);
    }

    public void startRound(){
        Player player=players.poll();
        players.add(player);
        this.routine=players.iterator();
        this.descendingRoutine=players.descendingIterator();
    }

    @Override
    public boolean hasNext() {
        return routine.hasNext() || descendingRoutine.hasNext();
    }

    @Override
    public Player next() {
        if(!routine.hasNext()) return descendingRoutine.next();
        else return routine.next();
    }
}
