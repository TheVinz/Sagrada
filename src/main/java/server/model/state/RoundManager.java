package server.model.state;

import server.model.state.player.Player;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.List;

/**
 * The <tt>RoundManager</tt> class in an {@link Iterator} that defines the order of the active {@link Player}s for the current round.
 */
public class RoundManager implements Iterator<Player> {

    private ArrayDeque<Player> players;
    private Iterator<Player> routine;
    private Iterator<Player> descendingRoutine;

    /**
     * Initializes a new <tt>RoundManager</tt> with the given players list.
     * @param players the players of the game.
     */
    public RoundManager(List<Player> players){
        this.players=new ArrayDeque<>(players);
    }

    /**
     * Defines a new players order for a new round. This order is defined according to the Sagrada rules.
     */
    public void startRound(){
        Player player=players.poll();
        players.add(player);
        this.routine=players.iterator();
        this.descendingRoutine=players.descendingIterator();
    }

    /**
     * Returns <code>true</code> if there are still players that have to perform their turns during the current round.
     * @return <code>true</code> if the round is not finished yet, <code>false</code> otherwise.
     */
    @Override
    public boolean hasNext() {
        return routine.hasNext() || descendingRoutine.hasNext();
    }

    /**
     * Returns the next player that has to play during the current round.
     * @return the next player that has to play during the current round.
     */
    @Override
    public Player next() {
        if(!routine.hasNext()) return descendingRoutine.next();
        else return routine.next();
    }
}
