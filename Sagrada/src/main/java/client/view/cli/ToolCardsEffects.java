package client.view.cli;
public class ToolCardsEffects {
   public String returnEffects(int toolCards) {
       switch (toolCards) {
           case 1:
               return "Grozing Pliers:\nAfter drafting, increase or decrease\nthe valure of the drafted die by 1.\n";
           case 2:
               return "Eglomise Brush:\nMove any one die in your window\nignoring the color restrictions.\n(You must obey all other placement restrictions)\n";
           case 3:
               return "Copper Foil Burnisher:\nMove any one die in your window\nignoring shade restriction.\n(You must obey all other placement restrictions)\n";
           case 4:
               return "Lathekin:\nMove exactly two dice, obeying all placement restrictions.\n";
           case 5:
               return "Lens Cutter:\nAfter drafting,swap the drafted die\nwith a die from the Round Track.\n";
           case 6:
               return "Flux Brush:\nAfter drafting,re-roll the drafted die.\nIf it cannot be placed return it to the Draft Pool.\n";
           case 7:
               return "Glazing Hammer:\nRe-roll all dice in the Draft Pool.\nThis may only be used on your\nsecond turn before drafting.\n";
           case 8:
               return "Running Pliers:\nAfter your first turn, immediately draft a die.\nSkyp your next turn this round.\n";
           case 9:
               return "Cork-backed Straightedge:\nAfter drafting,place the die in a spot\n that is not adjacent to another die.\n(You must obey all other placement restrictions)\n";
           case 10:
               return "Grinding Stone:\nAfter drafting,flip the die to its opposite side.\n(6 flips to 1, 5 to 2, 4 to 3, etc.\n";
           case 11:
               return "Flux Remover:\nAfter drafting, return the die to the\nDice Bag and pull 1 die from the bag.\n(Choose a value and place the new die,\nobeying all placement restrictions,or\nreturn it to the Draf Pool)\n";
           case 12:
               return "Tap Wheel:\nMove up to two dice of the same\ncolor that match the color of a die\non the Round Track.\n(You must obey all placement restrictions)\n";
           default:
               return "Error";
       }
   }
}
