package server.model.state.utilities;


/**
 * <tt>Color</tt> is an enum class that describes all the possible color in the game.
 */
public enum Color{
	RED, BLUE, GREEN, PURPLE, YELLOW;

    /**
     *Switch a string representing a color into a char.
     * @return a char that indicates a color.
     */
    public char asChar() {
        switch(this){
            case RED:
                return 'r';
            case BLUE:
                return 'b';
            case GREEN:
                return 'g';
            case PURPLE:
                return 'p';
            case YELLOW:
                return 'y';
        }
        return '\n'; //In compilatore voleva per forza avere un return quaggiù, nonostante questo sia codice morto che non verrà mai eseguito
    }
}