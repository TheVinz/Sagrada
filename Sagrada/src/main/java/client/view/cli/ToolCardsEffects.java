package client.view.cli;
public class ToolCardsEffects {
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
               return ":\n  After drafting, return the die to the\n  Dice Bag and pull 1 die from the bag.\n(Choose a value and place the new die,\nobeying all placement restrictions,or\nreturn it to the Draf Pool)\n\n";
           case 12:
               return ":\n  Move up to two die of the same\n  color that match the color of a die\n  on the Round Track.\n(You must obey all placement restrictions)\n\n";
           default:
               return "Error";
       }
   }

    public String returnName(int toolCard) {
        switch (toolCard) {
            case 1:
                return "GROZING PLIERS";   //viola
            case 2:
                return "EGLOMISE BRUSH";   //blu
            case 3:
                return "COPPER FOIL BURNISHER";    //rosso
            case 4:
                return "LATHEKIN";     //giallo
            case 5:
                return "LENS CUTTER";    //verde
            case 6:
                return "FLUX BRUSH";   //viola
            case 7:
                return "GLAZING HAMMER";    //blu
            case 8:
                return "RUNNING PLIERS";    //rosso
            case 9:
                return "CORK-BACKED STRAIGHTEDGE";    //giallo
            case 10:
                return "GRINDING STONE";     //verde
            case 11:
                return "FLUX REMOVER";    //viola
            case 12:
                return "TAP WHEEL";    //blu
            default:
                return "Error";
        }
    }

    public String returnColoredName(int toolCard){
        switch (toolCard) {
            case 1:
                return (char)27+"[1;35m"+returnName(1)+(char)27+"[0m"+returnEffects(1);  //viola
            case 2:
                return (char)27+"[1;36m"+returnName(2)+(char)27+"[0m"+returnEffects(2);   //blu
            case 3:
                return (char)27+"[1;31m"+returnName(3)+(char)27+"[0m"+returnEffects(3);    //rosso
            case 4:
                return (char)27+"[1;33m"+returnName(4)+(char)27+"[0m"+returnEffects(4);     //giallo
            case 5:
                return (char)27+"[1;32m"+returnName(5)+(char)27+"[0m"+returnEffects(5);    //verde
            case 6:
                return (char)27+"[1;35m"+returnName(6)+(char)27+"[0m"+returnEffects(6);   //viola
            case 7:
                return (char)27+"[1;36m"+returnName(7)+(char)27+"[0m"+returnEffects(7);    //blu
            case 8:
                return (char)27+"[1;31m"+returnName(8)+(char)27+"[0m"+returnEffects(8);    //rosso
            case 9:
                return (char)27+"[1;33m"+returnName(9)+(char)27+"[0m"+returnEffects(9);    //giallo
            case 10:
                return (char)27+"[1;32m"+returnName(10)+(char)27+"[0m"+returnEffects(10);     //verde
            case 11:
                return (char)27+"[1;35m"+returnName(11)+(char)27+"[0m"+returnEffects(11);    //viola
            case 12:
                return (char)27+"[1;36m"+returnName(12)+(char)27+"[0m"+returnEffects(12);    //blu
            default:
                return "Error";
        }
    }

 /*   case "BLU":
    card="Sum the values of every"+(char)27+"[1;36m"+" BLU "+(char)27+"[0m" +"dice on your WindowFrame\n";
                break;
            case "RED":
    card="Sum the values of every"+(char)27+"[1;31m"+" RED "+(char)27+"[0m"+"dice on your WindowFrame\n";
                break;
            case "YELLOW":
    card="Sum the values of every"+(char)27+"[1;33m"+" YELLOW "+(char)27+"[0m"+"dice on your WindowFrame\n";
                break;
            case "PURPLE":
    card="Sum the values of every"+(char)27+"[1;35m"+" PURPLE "+(char)27+"[0m"+"dice on your WindowFrame\n";
                break;
            case "GREEN":
    card="Sum the values of every"+(char)27+"[1;32m"+" GREEN "+(char)27+"[0m"+"dice on your WindowFrame\n";
                break;*/
}
