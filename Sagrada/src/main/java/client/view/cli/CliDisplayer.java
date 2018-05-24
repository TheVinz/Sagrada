package client.view.cli;


public class CliDisplayer {
    public static final String ANSI_PURPLE = "\u001B[34M";
    public static final String ANSI_RESET = "\u001B[0m";
    private CliState cliState = CliState.getCliState();
    private static CliDisplayer singleton;
    private ToolCardsEffects toolCardsEffects = new ToolCardsEffects();
    private ObjectiveCardsEffects objectiveCardsEffects = new ObjectiveCardsEffects();

    public static CliDisplayer getDisplayer() {
        if (singleton == null) singleton = new CliDisplayer();
        return singleton;
    }


    public void displayText(String text) {
        System.out.print(text);
    }

    //(char)27+"[0m]"
    public void printMenu() {
        displayText("\t\t\t\tIT'S YOUR TURN\nWhat would you like to see?\n");
        displayText("-DraftPool press\t\t\t\t\t P\n");      //ho assegnato a ogni comando una lettera
        displayText("-Your State press\t\t\t\t\t V\n");    //(char)27+"[31m"   colore rosso
        displayText("-ToolCards press\t\t\t\t\t T\n");
        displayText("-PublicObjectiveCard press\t\t\t O\n");
        displayText("-RoundTrack press\t\t\t\t\t R\n");
        displayText("-Other's State press\t\t\t\t S\n\n ");
        displayText("What you wanna do?\n");
        displayText("-Place a dice press\t\t\t\t\t D\n");
        displayText("-Use a ToolCard press\t\t\t\t U\n");
        displayText("-In order to skip the turn press\t N\n>>>");
    }

    public void printWindowFrame(CliPlayerState cliPlayerState) {
        displayText("The WindowFrame is:\n");
        displayText("");
        displayText("  || 0  | 1  | 2  | 3  | 4  |\n- - ");
        for (int k = 0; k < 8; k++) {
            displayText("===");
        }
        displayText("\n");
        for (int i = 0; i < 4; i++) {
            displayText(i + " |");
            for (int j = 0; j < 5; j++) {
                displayText("| ");
                if (cliPlayerState.getWindowFrame()[i][j].length() == 2) {
                    switch (cliPlayerState.getWindowFrame()[i][j].charAt(1)) {
                        case 'g':
                            displayText((char) 27 + "[102m" + cliPlayerState.getWindowFrame()[i][j].charAt(0) + "  " + (char) 27 + "[0m");   //caso green
                            break;
                        case 'b':
                            displayText((char) 27 + "[106m" + cliPlayerState.getWindowFrame()[i][j].charAt(0) + "  " + (char) 27 + "[0m");
                            break;
                        case 'y':
                            displayText((char) 27 + "[103m" + cliPlayerState.getWindowFrame()[i][j].charAt(0) + "  " + (char) 27 + "[0m");
                            break;
                        case 'r':
                            displayText((char) 27 + "[101m" + cliPlayerState.getWindowFrame()[i][j].charAt(0) + "  " + (char) 27 + "[0m");
                            break;
                        case 'p':
                            displayText((char) 27 + "[105m" + cliPlayerState.getWindowFrame()[i][j].charAt(0) + "  " + (char) 27 + "[0m");
                            break;
                    }
                } else {
                    switch (cliPlayerState.getWindowFrame()[i][j].charAt(0)) {
                        case '1':
                            displayText(cliPlayerState.getWindowFrame()[i][j] + "  ");
                            break;
                        case '2':
                            displayText(cliPlayerState.getWindowFrame()[i][j] + "  ");
                            break;
                        case '3':
                            displayText(cliPlayerState.getWindowFrame()[i][j] + "  ");
                            break;
                        case '4':
                            displayText(cliPlayerState.getWindowFrame()[i][j] + "  ");
                            break;
                        case '5':
                            displayText(cliPlayerState.getWindowFrame()[i][j] + "  ");
                            break;
                        case '6':
                            displayText(cliPlayerState.getWindowFrame()[i][j] + "  ");
                            break;
                        case 'X':
                            displayText("   ");
                            break;
                        case 'g':
                            displayText((char) 27 + "[102m" + "   " + (char) 27 + "[0m");   //caso green
                            break;
                        case 'b':
                            displayText((char) 27 + "[106m" + "   " + (char) 27 + "[0m");
                            break;
                        case 'y':
                            displayText((char) 27 + "[103m" + "   " + (char) 27 + "[0m");
                            break;
                        case 'r':
                            displayText((char) 27 + "[101m" + "   " + (char) 27 + "[0m");
                            break;
                        case 'p':
                            displayText((char) 27 + "[105m" + "   " + (char) 27 + "[0m");
                            break;
                    }
                }
            }
            displayText("|\n");
            if (i != 3) {
                for (int k = 0; k < 15; k++) {
                    displayText("- ");
                }
                displayText("\n");
            } else {
                displayText("- -  ");
                for (int k = 0; k < 8; k++) {
                    displayText("===");
                }
                displayText("\n");
            }
        }
    }

    public void printWindowFrame() {
        printWindowFrame(cliState.getActivePlayer());
    }

   public void printRoundTrack() {
        displayText("The RoundTrack is:\n");
       int max=0;
       for(int j=0;j<10;j++)
           if(cliState.getRoundTrack()[j]!=null){
             if(max<cliState.getRoundTrack()[j].length)
                   max=cliState.getRoundTrack()[j].length;
            }
       displayText("Index:    ");
       for(int j=0;j<max;j++){
          displayText(j+" |  ");
       }
       displayText("\n");
        for (int i = 0; i < cliState.getRoundTrack().length && cliState.getRoundTrack()[i] != null; i++) {
            displayText("Round "+((int) i + 1) + ")");
            for (int j = 0; j < cliState.getRoundTrack()[i].length; j++)
                if (cliState.getRoundTrack()[i][j].length() == 2) {
                    switch (cliState.getRoundTrack()[i][j].charAt(1)) {
                        case 'g':
                            displayText((char) 27 + "[102m" +"  "+cliState.getRoundTrack()[i][j].charAt(0)+" " + (char) 27 + "[0m"+"|");   //caso green
                            break;
                        case 'b':
                            displayText((char) 27 + "[106m" +"  "+cliState.getRoundTrack()[i][j].charAt(0)+" " + (char) 27 + "[0m"+"|");
                            break;
                        case 'y':
                            displayText((char) 27 + "[103m" +"  "+cliState.getRoundTrack()[i][j].charAt(0)+" " + (char) 27 + "[0m"+"|");
                            break;
                        case 'r':
                            displayText((char) 27 + "[101m"+"  " +cliState.getRoundTrack()[i][j].charAt(0)+" " +(char) 27 + "[0m"+"|");
                            break;
                        case 'p':
                            displayText((char) 27 + "[105m"+"  "+ cliState.getRoundTrack()[i][j].charAt(0)+" "+ (char) 27 + "[0m"+"|" );
                            break;
                    }

                }
                else
                    displayText("    |");

            displayText("\n");
        }
    }
     public void printToolCard() {
        displayText("You can use these ToolCards:\n");
        for (int i = 0; i < 3; i++) {
            displayText(i + ")" + toolCardsEffects.returnEffects(cliState.getToolCardIds()[i]));     //numero è riferito all'ordine nell'array, mentre j indica la ToolCard vera e propria,
            //per ora un numero a cui dovremo associare la vera e propria ToolCard.
        }
    }

    public void printDraftPool() {
        displayText("In the DraftPool there are those Dice\nIndex:   ");
        for (int i = 0; i < cliState.getDraftPool().length; i++) {
            displayText(i+" |  ");
        }
        displayText("\nDice:  ");
        for (int j=0;j<cliState.getDraftPool().length;j++){
            if (cliState.getDraftPool()[j].length() == 2) {
                switch (cliState.getDraftPool()[j].charAt(1)) {
                    case 'g':
                        displayText((char) 27 + "[102m" +"  "+ cliState.getDraftPool()[j].charAt(0)+" " + (char) 27 + "[0m"+"|");   //caso green
                        break;
                    case 'b':
                        displayText((char) 27 + "[106m" +"  "+ cliState.getDraftPool()[j].charAt(0)+" " + (char) 27 + "[0m"+"|");
                        break;
                    case 'y':
                        displayText((char) 27 + "[103m" +"  "+ cliState.getDraftPool()[j].charAt(0)+" " + (char) 27 + "[0m"+"|");
                        break;
                    case 'r':
                        displayText((char) 27 + "[101m"+"  " +cliState.getDraftPool()[j].charAt(0)+" " +(char) 27 + "[0m"+"|");
                        break;
                    case 'p':
                        displayText((char) 27 + "[105m"+"  "+ cliState.getDraftPool()[j].charAt(0)+" "+ (char) 27 + "[0m"+"|" );
                        break;
                }

            }
            else
                displayText("    |");

        }
        displayText("\n");
    }

    public void printPublicObjectiveCards() {
        displayText("There are these PublicObjectiveCard:\n");
        for (int i = 0; i < 3; i++) {
            Integer j = cliState.getPublicObjectiveCardIds()[i];
            displayText(i + ")" + objectiveCardsEffects.returnEffects(j));
        }
    }

    public void printPrivateObjectiveCard() {
        displayText("Your PrivateObjectiveCard is: ");
        displayText(cliState.getPrivateObjectiveCard() + "\n");
    }

    public void printFavorTokens() {
        printFavorTokens(cliState.getActivePlayer());
    }

    public void printFavorTokens(CliPlayerState cliPlayerState) {
        displayText("The FavorTokens are " + cliPlayerState.getFavorTokens() + "\n");
    }

    public void printState() {
        displayText("Your State is:\n");
        printFavorTokens();
        printWindowFrame();
        printPrivateObjectiveCard();
    }

    public void printState(String name) {
        displayText("The state of " + name + " is:\n");
        printFavorTokens(cliState.getCliPlayerState(name));
        printWindowFrame(cliState.getCliPlayerState(name));
    }
    public void printRep(String rep,int favorTokens){
        displayText("\n  || 0  | 1  | 2  | 3  | 4  |\n- - ");
        for (int k = 0; k < 8; k++) {
            displayText("===");
        }
        displayText("\n");
        int index=0;
        for(int i=0;i<4;i++) {
            displayText(i + " |");
            for (int j = 0; j < 5; j++) {
                displayText("| ");
                char c = rep.charAt(index);
                switch (c) {
                    case '1':
                        displayText(c + "  ");
                        break;
                    case '2':
                        displayText(c + "  ");
                        break;
                    case '3':
                        displayText(c + "  ");
                        break;
                    case '4':
                        displayText(c + "  ");
                        break;
                    case '5':
                        displayText(c + "  ");
                        break;
                    case '6':
                        displayText(c + "  ");
                        break;
                    case '0':
                        displayText("   ");
                        break;
                    case 'g':
                        displayText((char) 27 + "[102m" + "   " + (char) 27 + "[0m");   //caso green
                        break;
                    case 'b':
                        displayText((char) 27 + "[106m" + "   " + (char) 27 + "[0m");
                        break;
                    case 'y':
                        displayText((char) 27 + "[103m" + "   " + (char) 27 + "[0m");
                        break;
                    case 'r':
                        displayText((char) 27 + "[101m" + "   " + (char) 27 + "[0m");
                        break;
                    case 'p':
                        displayText((char) 27 + "[105m" + "   " + (char) 27 + "[0m");
                        break;
                }
                index++;
            }
            displayText("|\n");
            if (i != 3) {
                for (int k = 0; k < 15; k++) {
                    displayText("- ");
                }
                displayText("\n");
            } else {
                displayText("- - ");
                for (int k = 0; k < 8; k++) {
                    displayText("===");
                }
                displayText("\n");
            }
        }
        displayText("FAVOR TOKENS: "+favorTokens+"\n");
    }
}
