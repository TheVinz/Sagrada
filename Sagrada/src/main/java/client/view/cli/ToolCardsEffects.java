package client.view.cli;
public class ToolCardsEffects {
   public String returnEffects(int toolCards) {
       switch (toolCards) {
           case 1:
               return "GROZING PLIERS:\n  After drafting, increase or decrease\n  the value of the drafted die by 1.\n\n";
           case 2:
               return "EGLOMISE BRUSH:\n  Move any one die in your window\n  ignoring the color restrictions.\n(You must obey all other placement restrictions)\n\n";
           case 3:
               return "COPPER FOIL BURNISHER:\n  Move any one die in your window\n  ignoring shade restriction.\n(You must obey all other placement restrictions)\n\n";
           case 4:
               return "LATHEKIN:\n  Move exactly two die, obeying all placement restrictions.\n\n";
           case 5:
               return "LENS CUTTER:\n  After drafting,swap the drafted die\n  with a die from the Round Track.\n\n";
           case 6:
               return "FLUX BRUSH:\n  After drafting,re-roll the drafted die.\n  If it cannot be placed return it to the Draft Pool.\n\n";
           case 7:
               return "GLAZING HAMMER:\n  Re-roll all die in the Draft Pool.\n  This may only be used on your\n  second turn before drafting.\n\n";
           case 8:
			   return "RUNNING PLIERS:\n  After your first turn immediately draft a die.\n (Skip your next turn this round)\n";
           case 9:
               return "CORK-BACKED STRAIGHTEDGE:\n  After drafting,place the die in a spot\n  that is not adjacent to another die.\n(You must obey all other placement restrictions)\n\n";
           case 10:
               return "GRINDING STONE:\n  After drafting,flip the die to its opposite side.\n(6 flips to 1, 5 to 2, 4 to 3, etc.)\n\n";
           case 11:
               return "FLUX REMOVER:\nAfter drafting, return the die to the\n  Dice Bag and pull 1 die from the bag.\n(Choose a value and place the new die,\nobeying all placement restrictions,or\nreturn it to the Draf Pool)\n\n";
           case 12:
               return "TAP WHEEL:\n  Move up to two die of the same\n  color that match the color of a die\n  on the Round Track.\n(You must obey all placement restrictions)\n\n";
           default:
               return "Error";
       }
   }
}
