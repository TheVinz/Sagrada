package client.view.cli;

public class ObjectiveCardsEffects {
    public String returnEffects(int objectiveCard) {
        switch (objectiveCard) {
            case 0:
                return "COLORED DIAGONAL:\n  Number of dices with same color in adjacent diagonal.\n\n";
            case 1:
                return "DARK SHADES:\n  2 points for each set of 5 and 6.\n\n";
            case 2:
                return "DIFFERENT COLORS:\n  4 points for each set of different colored dices.\n\n";
            case 3:
                return "DIFFERENT COLORS COLUMN:\n  5 points for each column without repeated colors.\n\n";
            case 4:
                return "DIFFERENT COLORS ROW:\n  6 points for each row without repeated colors.\n\n";
            case 5:
                return "DIFFERENT SHADES:\n  5 points for each set of different shade dices.\n\n";
            case 6:
                return "DIFFERENT SHADES COLUMN:\n  4 points for each column without repeated shade.\n\n";
            case 7:
                return "DIFFERENT SHADES ROW:\n  5 points for each row without repeated shade.\n\n";
            case 8:
                return "MEDIUM SHADES:\n  2 points for each set of 3 and 4.\n\n";
            case 9:
                return "PALE SHADES:\n  2 points for each set of 1 and 2.\n\n";
            default:
                return "error";
        }
    }

    public String returnName(int objectiveCard){
        switch(objectiveCard) {
            case 0:
                return "COLORED DIAGONAL";
            case 1:
                return "DARK SHADES";
            case 2:
                return "DIFFERENT COLORS";
            case 3:
                return "DIFFERENT COLORS COLUMN";
            case 4:
                return "DIFFERENT COLORS ROW";
            case 5:
                return "DIFFERENT SHADES";
            case 6:
                return "DIFFERENT SHADES COLUMN";
            case 7:
                return "DIFFERENT SHADES ROW";
            case 8:
                return "MEDIUM SHADES";
            case 9:
                return "PALE SHADES";
            default:
                return "error";

        }
    }


}
