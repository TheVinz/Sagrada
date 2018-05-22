package client.view.cli;
public class ToolCardsEffects {
   public String returnEffects(int toolCards) {
       switch (toolCards) {
           case 1:
               return "GROZING PLIERS:\nAfter drafting, increase or decrease\nthe value of the drafted dice by 1.\n\n";
           case 2:
               return "EGLOMISE BRUSH:\nMove any one dice in your window\nignoring the color restrictions.\n(You must obey all other placement restrictions)\n\n";
           case 3:
               return "COPPER FOIL BURNISHER:\nMove any one dice in your window\nignoring shade restriction.\n(You must obey all other placement restrictions)\n\n";
           case 4:
               return "LATHEKIN:\nMove exactly two dice, obeying all placement restrictions.\n\n";
           case 5:
               return "LENS CUTTER:\nAfter drafting,swap the drafted die\nwith a dice from the Round Track.\n\n";
           case 6:
               return "FLUX BRUSH:\nAfter drafting,re-roll the drafted dice.\nIf it cannot be placed return it to the Draft Pool.\n\n";
           case 7:
               return "GLAZING HAMMER:\nRe-roll all dice in the Draft Pool.\nThis may only be used on your\nsecond turn before drafting.\n\n";
           case 8:
           case 9:
               return "CORK-BACKED STRAIGHTEDGE:\nAfter drafting,place the dice in a spot\n that is not adjacent to another dice.\n(You must obey all other placement restrictions)\n\n";
           case 10:
               return "GRINDING STONE:\nAfter drafting,flip the dice to its opposite side.\n(6 flips to 1, 5 to 2, 4 to 3, etc.\n\n";
           case 11:
               return "FLUX REMOVER:\nAfter drafting, return the dice to the\nDice Bag and pull 1 die from the bag.\n(Choose a value and place the new dice,\nobeying all placement restrictions,or\nreturn it to the Draf Pool)\n\n";
           case 12:
               return "TAP WHEEL:\nMove up to two dice of the same\ncolor that match the color of a dice\non the Round Track.\n(You must obey all placement restrictions)\n\n";
           default:
               return "Error";
       }
   }
}
