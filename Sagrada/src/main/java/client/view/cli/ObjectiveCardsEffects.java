package client.view.cli;

public class ObjectiveCardsEffects {
    public String returnEffects(int ObjectiveCard){
        switch (ObjectiveCard){
            case 0:
                return "COLORED DIAGONAL:\nNumber of dices with same color in adjacent diagonal.\n\n";
            case 1:
                return "DARK SHADES:\n2 point for each set of 5 and 6.\n\n";
            case 2:
                return "DIFFERENT COLORS:\n4 points for each set of different colored dices.\n\n";
            case 3:
                return "DIFFERENT COLORS COLUMN:\n5 points for each column without repeated colors.\n\n";
            case 4:
                return "DIFFERENT COLORS ROW:\n6 points for each row without repeated colors.\n\n";
            case 5:
                return "DIFFERENT SHADES:\n5 points for each set of different shade dices.\n\n";
            case 6:
                return "DIFFERENT SHADES COLUMN:\n4 points for each column without repeated shade.\n\n";
            case 7:
                return "DIFFERENT SHADES ROW:\n5 points for each row without repeated shade.\n\n";
            case 8:
                return "MEDIUM SHADES:\n2 points for each set of 3 and 4.\n\n";
            case 9:
                return "PALE SHADES:\n2 point for each set of 1 and 2.\n\n";
            default:
                return "error";
        }

    }
}
