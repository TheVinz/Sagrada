package client.view.cli;

public class ObjectiveCardsEffects {
    public String returnEffects(int ObjectiveCard){
        switch (ObjectiveCard){
            case 1:
                return "";
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            default:
                return "error";
        }

    }
}
