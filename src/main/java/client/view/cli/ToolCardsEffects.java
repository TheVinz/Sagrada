package client.view.cli;

/**
 * The <tt>ToolCardsEffects</tt> class is useful in the cli to gets the name and the effect of a {@link server.model.state.toolcards.ToolCard}
 * given a number.
 */
public class ToolCardsEffects {

    /**
     * Returns a String with the name and the effect of the ToolCard represented by a number.
     * @param toolCards an int which represents a ToolCard.
     * @return a String with the name and the effect of the ToolCard.
     */
   public String returnEffects(int toolCards) {
       switch (toolCards) {
           case 1:
               return ":\n  After drafting, increase or decrease\n  the value of the drafted die by 1.\n\n";
           case 2:
               return ":\n  Move any one die in your window\n  ignoring the color restrictions.\n(You must obey all other placement restrictions)\n\n";
           case 3:
               return ":\n  Move any one die in your window\n  ignoring shade restriction.\n(You must obey all other placement restrictions)\n\n";
           case 4:
               return ":\n  Move exactly two die, obeying all placement restrictions.\n\n";
           case 5:
               return ":\n  After drafting,swap the drafted die\n  with a die from the Round Track.\n\n";
           case 6:
               return ":\n  After drafting,re-roll the drafted die.\n  If it cannot be placed return it to the Draft Pool.\n\n";
           case 7:
               return ":\n  Re-roll all die in the Draft Pool.\n  This may only be used on your\n  second turn before drafting.\n\n";
           case 8:
			   return ":\n  After your first turn immediately draft a die.\n (Skip your next turn this round)\n\n";
           case 9:
               return ":\n  After drafting,place the die in a spot\n  that is not adjacent to another die.\n(You must obey all other placement restrictions)\n\n";
           case 10:
               return ":\n  After drafting,flip the die to its opposite side.\n(6 flips to 1, 5 to 2, 4 to 3, etc.)\n\n";
           case 11:
               return ":\n  After drafting, return the die to the\n  Dice Bag and pull 1 die from the bag.\n(Choose a value and place the new die,\nobeying all placement restrictions,or\nreturn it to the DraftPool)\n\n";
           case 12:
               return ":\n  Move up to two die of the same\n  color that match the color of a die\n  on the Round Track.\n(You must obey all placement restrictions)\n\n";
           default:
               return "Error";
       }
   }

    /**
     * Returns a String with the name of the ToolCard represented by a number.
     * @param toolCard an int which represents a ToolCard.
     * @return a String with the name of the ToolCard.
     */
    public String returnName(int toolCard) {
        switch (toolCard) {
            case 1:
                return "GROZING PLIERS";
            case 2:
                return "EGLOMISE BRUSH";
            case 3:
                return "COPPER FOIL BURNISHER";
            case 4:
                return "LATHEKIN";
            case 5:
                return "LENS CUTTER";
            case 6:
                return "FLUX BRUSH";
            case 7:
                return "GLAZING HAMMER";
            case 8:
                return "RUNNING PLIERS";
            case 9:
                return "CORK-BACKED STRAIGHTEDGE";
            case 10:
                return "GRINDING STONE";
            case 11:
                return "FLUX REMOVER";
            case 12:
                return "TAP WHEEL";
            default:
                return "Error";
        }
    }

    /**
     * Returns a String with the name and the color of the ToolCard represented by a number.
     * @param toolCard an int which represents a ToolCard.
     * @return a String with the name and the color of the ToolCard.
     */
    public String returnColoredName(int toolCard){
        switch (toolCard) {
            case 1:
                return (char)27+"[1;35m"+returnName(1)+(char)27+"[0m"+returnEffects(1);
            case 2:
                return (char)27+"[1;36m"+returnName(2)+(char)27+"[0m"+returnEffects(2);
            case 3:
                return (char)27+"[1;31m"+returnName(3)+(char)27+"[0m"+returnEffects(3);
            case 4:
                return (char)27+"[1;33m"+returnName(4)+(char)27+"[0m"+returnEffects(4);
            case 5:
                return (char)27+"[1;32m"+returnName(5)+(char)27+"[0m"+returnEffects(5);
            case 6:
                return (char)27+"[1;35m"+returnName(6)+(char)27+"[0m"+returnEffects(6);
            case 7:
                return (char)27+"[1;36m"+returnName(7)+(char)27+"[0m"+returnEffects(7);
            case 8:
                return (char)27+"[1;31m"+returnName(8)+(char)27+"[0m"+returnEffects(8);
            case 9:
                return (char)27+"[1;33m"+returnName(9)+(char)27+"[0m"+returnEffects(9);
            case 10:
                return (char)27+"[1;32m"+returnName(10)+(char)27+"[0m"+returnEffects(10);
            case 11:
                return (char)27+"[1;35m"+returnName(11)+(char)27+"[0m"+returnEffects(11);
            case 12:
                return (char)27+"[1;36m"+returnName(12)+(char)27+"[0m"+returnEffects(12);
            default:
                return "Error";
        }
    }
}
