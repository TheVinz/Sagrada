package client.view.cli;


import common.viewchangement.SinglePlayerEndGame;

public class CliDisplayer {
    private CliState cliState = CliState.getCliState();
    private static CliDisplayer singleton;
    private ToolCardsEffects toolCardsEffects = new ToolCardsEffects();
    private ObjectiveCardsEffects objectiveCardsEffects = new ObjectiveCardsEffects();
    private boolean singlePlayer=false;

    public static CliDisplayer getDisplayer() {
        if (singleton == null) singleton = new CliDisplayer();
        return singleton;
    }




    public void displayText(String text) {
        System.out.print(text);
    }


    public void printMenu() {
        if(!singlePlayer) {
            if (cliState.getActivePlayer().isSecondTurn())
                displayText("\t\tIT'S YOUR SECOND TURN IN THE ROUND N-" + cliState.getRound());
            else
                displayText("\t\tIT'S YOUR FIRST TURN IN THE ROUND N-" + cliState.getRound());
            displayText("\n\t\t\t\tWhat would you like to see?\n");
            displayText("-DraftPool press\t\t\t\t\t P\n");      //ho assegnato a ogni comando una lettera
            displayText("-Your State press\t\t\t\t\t V\n");    //(char)27+"[31m"   colore rosso
            displayText("-ToolCards press\t\t\t\t\t T\n");
            displayText("-PublicObjectiveCard press\t\t\t O\n");
            displayText("-RoundTrack press\t\t\t\t\t R\n");
            displayText("-Other's State press\t\t\t\t S\n\n ");
            displayText("What you wanna do?\n");
            displayText("-Place a dice press\t\t\t\t\t D\n");
            displayText("-Use a ToolCard press\t\t\t\t U\n");
            displayText("-In order to skip the turn press\t N\n");
        }
        else
        {
            if (cliState.getActivePlayer().isSecondTurn())
                displayText("\t\tIT'S YOUR SECOND TURN IN THE ROUND N-" + cliState.getRound());
            else
                displayText("\t\tIT'S YOUR FIRST TURN IN THE ROUND N-" + cliState.getRound());
            displayText("\n\t\t\t\tWhat would you like to see?\n");
            displayText("-DraftPool press\t\t\t\t\t P\n");      //ho assegnato a ogni comando una lettera
            displayText("-Your State press\t\t\t\t\t V\n");    //(char)27+"[31m"   colore rosso
            displayText("-ToolCards press\t\t\t\t\t T\n");
            displayText("-PublicObjectiveCard press\t\t\t O\n");
            displayText("-RoundTrack press\t\t\t\t\t R\n");
            displayText("What you wanna do?\n");
            displayText("-Place a dice press\t\t\t\t\t D\n");
            displayText("-Use a ToolCard press\t\t\t\t U\n");
            displayText("-In order to skip the turn press\t N\n");


        }
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
                displayText("|");
                if (cliPlayerState.getWindowFrame()[i][j].length() == 2) {
                    switch (cliPlayerState.getWindowFrame()[i][j].charAt(1)) {
                        case 'g':
                            displayText((char) 27 + "[1;30;102m" +" "+cliPlayerState.getWindowFrame()[i][j].charAt(0)+"  " + (char) 27 + "[0m");
                            break;
                        case 'b':
                            displayText((char) 27 + "[1;30;106m" +" "+ cliPlayerState.getWindowFrame()[i][j].charAt(0) + "  " + (char) 27 + "[0m");
                            break;
                        case 'y':
                            displayText((char) 27 + "[1;30;103m" +" "+ cliPlayerState.getWindowFrame()[i][j].charAt(0) + "  " + (char) 27 + "[0m");
                            break;
                        case 'r':
                            displayText((char) 27 + "[1;30;41m" +" "+ cliPlayerState.getWindowFrame()[i][j].charAt(0) + "  " + (char) 27 + "[0m");
                            break;
                        case 'p':
                            displayText((char) 27 + "[1;30;45m" +" "+ cliPlayerState.getWindowFrame()[i][j].charAt(0) + "  " + (char) 27 + "[0m");
                            break;
                    }
                } else {
                    switch (cliPlayerState.getWindowFrame()[i][j].charAt(0)) {
                        case '1':
                            displayText(" "+(char)27+"[1;30m"+cliPlayerState.getWindowFrame()[i][j]+(char)27+"[0m" + "  ");
                            break;
                        case '2':
                            displayText(" "+(char)27+"[1;30m"+cliPlayerState.getWindowFrame()[i][j]+(char)27+"[0m" + "  ");
                            break;
                        case '3':
                            displayText(" "+(char)27+"[1;30m"+cliPlayerState.getWindowFrame()[i][j]+(char)27+"[0m" + "  ");
                            break;
                        case '4':
                            displayText(" "+(char)27+"[1;30m"+cliPlayerState.getWindowFrame()[i][j]+(char)27+"[0m" + "  ");
                            break;
                        case '5':
                            displayText(" "+(char)27+"[1;30m"+cliPlayerState.getWindowFrame()[i][j]+(char)27+"[0m" + "  ");
                            break;
                        case '6':
                            displayText(" "+(char)27+"[1;30m"+cliPlayerState.getWindowFrame()[i][j]+(char)27+"[0m" + "  ");
                            break;
                        case '0':
                            displayText("    ");
                            break;
                        case 'g':
                            displayText((char) 27 + "[102m" + "    " + (char) 27 + "[0m");   //caso green
                            break;
                        case 'b':
                            displayText((char) 27 + "[106m" + "    " + (char) 27 + "[0m");
                            break;
                        case 'y':
                            displayText((char) 27 + "[103m" + "    " + (char) 27 + "[0m");
                            break;
                        case 'r':
                            displayText((char) 27 + "[41m" + "    " + (char) 27 + "[0m");
                            break;
                        case 'p':
                            displayText((char) 27 + "[45m" + "    " + (char) 27 + "[0m");
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
        printWindowFrame(CliState.getCliState().getCliPlayerState());
    }

   public void printRoundTrack() {
        displayText("The RoundTrack is:\n");
       int max=0;
       for(int i=0;i<cliState.getRound()-1;i++){
           if(max<cliState.getRoundTrack()[i].length)
               max=cliState.getRoundTrack()[i].length;
       }
       displayText("Index:    ");
       for(int j=1;j<max+1;j++){
          displayText(j+" |  ");
       }
       displayText("\n");
        for (int i = 0; i < cliState.getRound()-1; i++) {
            displayText("Round "+(i+1) + ")");
                for (int j = 0; j < cliState.getRoundTrack()[i].length; j++) {
                    switch (cliState.getRoundTrack()[i][j].charAt(1)) {
                        case 'g':
                            displayText((char) 27 + "[1;30;102m" + "  " + cliState.getRoundTrack()[i][j].charAt(0) + " " + (char) 27 + "[0m" + "|");   //caso green
                            break;
                        case 'b':
                            displayText((char) 27 + "[1;30;106m" + "  " + cliState.getRoundTrack()[i][j].charAt(0) + " " + (char) 27 + "[0m" + "|");
                            break;
                        case 'y':
                            displayText((char) 27 + "[1;30;103m" + "  " + cliState.getRoundTrack()[i][j].charAt(0) + " " + (char) 27 + "[0m" + "|");
                            break;
                        case 'r':
                            displayText((char) 27 + "[1;30;41m" + "  " + cliState.getRoundTrack()[i][j].charAt(0) + " " + (char) 27 + "[0m" + "|");
                            break;
                        case 'p':
                            displayText((char) 27 + "[1;30;45m" + "  " + cliState.getRoundTrack()[i][j].charAt(0) + " " + (char) 27 + "[0m" + "|");
                            break;
                    }

            }


            displayText("\n");
        }
    }
     public void printToolCard() {
        displayText("You can use these ToolCards:\n");
        if(!singlePlayer){
        for (int i = 0; i < cliState.getToolCardIds().length; i++) {
            displayText(i + ")" +toolCardsEffects.returnName(cliState.getToolCardIds()[i])+ toolCardsEffects.returnEffects(cliState.getToolCardIds()[i]));     //numero è riferito all'ordine nell'array, mentre j indica la ToolCard vera e propria,
            //per ora un numero a cui dovremo associare la vera e propria ToolCard.
        }}
        else {
            for (int i = 0; i < cliState.getToolCardIds().length; i++) {
                displayText(i + ")" + toolCardsEffects.returnColoredName(cliState.getToolCardIds()[i]));

            }
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
                        displayText((char) 27 + "[1;30;102m" +"  "+ cliState.getDraftPool()[j].charAt(0)+" " + (char) 27 + "[0m"+"|");   //caso green
                        break;
                    case 'b':
                        displayText((char) 27 + "[1;30;106m" +"  "+ cliState.getDraftPool()[j].charAt(0)+" " + (char) 27 + "[0m"+"|");
                        break;
                    case 'y':
                        displayText((char) 27 + "[1;30;103m" +"  "+ cliState.getDraftPool()[j].charAt(0)+" " + (char) 27 + "[0m"+"|");
                        break;
                    case 'r':
                        displayText((char) 27 + "[1;30;41m"+"  " +cliState.getDraftPool()[j].charAt(0)+" " +(char) 27 + "[0m"+"|");
                        break;
                    case 'p':
                        displayText((char) 27 + "[1;30;45m"+"  "+ cliState.getDraftPool()[j].charAt(0)+" "+ (char) 27 + "[0m"+"|" );
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
        for (int i = 0; i < cliState.getPublicObjectiveCardIds().length; i++) {
            Integer j = cliState.getPublicObjectiveCardIds()[i];
            displayText(i + ")" + objectiveCardsEffects.returnEffects(j));
        }
    }

    public void printPrivateObjectiveCard() {
       for(int i=0; i<cliState.getPrivateObjectiveCard().length; i++)
            printColoredPrvCard(cliState.getPrivateObjectiveCard()[i]);
    }

    public void printFavorTokens() {
        printFavorTokens(CliState.getCliState().getCliPlayerState());
    }

    public void printFavorTokens(CliPlayerState cliPlayerState) {
        displayText("The FavorTokens are " + cliPlayerState.getFavorTokens() + "\n");
    }

    public void printState() {
        displayText("Your State is:\n");
        printPrivateObjectiveCard();
        printFavorTokens();
        printWindowFrame();

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
                displayText("|");
                char c = rep.charAt(index);
                switch (c) {
                    case '1':
                        displayText(" "+c + "  ");
                        break;
                    case '2':
                        displayText(" "+c + "  ");
                        break;
                    case '3':
                        displayText(" "+c + "  ");
                        break;
                    case '4':
                        displayText(" "+c + "  ");
                        break;
                    case '5':
                        displayText(" "+c + "  ");
                        break;
                    case '6':
                        displayText(" "+c + "  ");
                        break;
                    case '0':
                        displayText("    ");
                        break;
                    case 'g':
                        displayText((char) 27 + "[102m" + "    " + (char) 27 + "[0m");   //caso green
                        break;
                    case 'b':
                        displayText((char) 27 + "[106m" + "    " + (char) 27 + "[0m");
                        break;
                    case 'y':
                        displayText((char) 27 + "[103m" + "    " + (char) 27 + "[0m");
                        break;
                    case 'r':
                        displayText((char) 27 + "[41m" + "    " + (char) 27 + "[0m");
                        break;
                    case 'p':
                        displayText((char) 27 + "[45m" + "    " + (char) 27 + "[0m");
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

    public void printResults(char[] cards, int[] scoreboardIds, int[][] points) {
        displayText("\t\t\t\t\t\tSCOREBOARD:\n\n");
        int max=0;
        for(int i=0;i<scoreboardIds.length;i++)
            if(max<cliState.getCliPlayerState(scoreboardIds[i]).getName().length())
                max=cliState.getCliPlayerState(scoreboardIds[i]).getName().length();
        if(max+2<18){
            max=18;}
        displayText("    \tNAME\t    ");
        for(int i=0;i<max+2-20;i++){
            displayText(" ");
        }
        displayText("|     PUBLIC CARDS   |   PRV  |  FV  |  EMPTY  |  TOTAL:\n");
        for(int s=0;s<max+2;s++)
                displayText(" ");
        displayText("|   "+0+"  |   "+1+"  |   "+2+"  |  \t  |      |  \t   |        |\n");

        for(int i=0;i<scoreboardIds.length;i++) {
            switch (cards[i]){
                case 'r':
                    displayText("" + (i + 1) + ")" +(char)27+"[1;31m"+cliState.getCliPlayerState(scoreboardIds[i]).getName()+(char)27+"[0m");
                    break;
                case 'b':
                    displayText("" + (i + 1) + ")" +(char)27+"[1;36m"+cliState.getCliPlayerState(scoreboardIds[i]).getName()+(char)27+"[0m");
                    break;
                case 'g':
                    displayText("" + (i + 1) + ")" +(char)27+"[1;32m"+cliState.getCliPlayerState(scoreboardIds[i]).getName()+(char)27+"[0m");
                    break;
                case 'y':
                    displayText("" + (i + 1) + ")" +(char)27+"[1;33m"+cliState.getCliPlayerState(scoreboardIds[i]).getName()+(char)27+"[0m");
                    break;
                case 'p':
                    displayText("" + (i + 1) + ")" +(char)27+"[1;35m"+cliState.getCliPlayerState(scoreboardIds[i]).getName()+(char)27+"[0m");
                    break;
            }
                for(int s=0;s<max-cliState.getCliPlayerState(scoreboardIds[i]).getName().length();s++){
                displayText(" ");
                }
                displayText("|");
            if (points[scoreboardIds[i]][0] > 9)   //prima public
                displayText("  " + points[scoreboardIds[i]][0] + "  |");
            else
                displayText("   " + points[scoreboardIds[i]][0] + "  |");
            if (points[scoreboardIds[i]][1] > 9)   //seconda public
                displayText("  " + points[scoreboardIds[i]][1] + "  |");
            else
                displayText("   " + points[scoreboardIds[i]][1] + "  |");
            if (points[scoreboardIds[i]][2] > 9)   //terza public
                displayText("  " + points[scoreboardIds[i]][2] + "  |");
            else
                displayText("   " + points[scoreboardIds[i]][2] + "  |");
            if (points[scoreboardIds[i]][3] > 9)    //private
                displayText("   " + points[scoreboardIds[i]][3] + "   |");
            else
                displayText("    " + points[scoreboardIds[i]][3] + "   |");
            displayText("   " + points[scoreboardIds[i]][4] + "  |");  //favor
            if(points[scoreboardIds[i]][5]<-9){   //empty
                displayText("   "+points[scoreboardIds[i]][5]+"   |");
            }
            else
                displayText("   "+points[scoreboardIds[i]][5]+"    |");
                                        //total points
            if(points[scoreboardIds[i]][6]>0){
                if (points[scoreboardIds[i]][6] > 9)
                    displayText("   " + points[scoreboardIds[i]][6] + "   |\n");
                else
                    displayText("    " + points[scoreboardIds[i]][6] + "    |\n");
            }
            else{
                if(points[scoreboardIds[i]][6]<-9){
                    displayText("   "+points[scoreboardIds[i]][6]+"  |\n");
                }
                else
                    displayText("   "+points[scoreboardIds[i]][6]+"   |\n");
            }

        }
        for(int i=0;i<CliState.getCliState().getPublicObjectiveCardIds().length;i++)
        displayText("PublicObjectiveCard "+i+" is "+objectiveCardsEffects.returnName(CliState.getCliState().getPublicObjectiveCardIds()[i])+"\n");
        displayText("\n");
    }
    public void printBold(String modify) {    //da mettere in inglese
        displayText((char) 27 + "[1m" + modify + (char) 27 + "[0m");
    }
    public void setSinglePlayer(boolean singlePlayer) {
        this.singlePlayer = singlePlayer;}

    public void printColoredPrvCard(String color){
        String card;
        switch(color){
            case "BLU":
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
                break;
            default:
                card="Compiler wants me to add a default case";
                break;
        }
        displayText(card);
    }
    public void printSinglePlayerPoints(SinglePlayerEndGame singlePlayerEndGame){
        if(singlePlayerEndGame.getTargetPoints()<singlePlayerEndGame.getVectorPoints()[4]) {
            displayText("\t\t\t\t\tYOU WIN");
        }
        else
            displayText("\t\t\t\t\tYOU LOSE");
        displayText("\nYour Total Points are "+singlePlayerEndGame.getVectorPoints()[4]+" instead the score to beat was "+singlePlayerEndGame.getTargetPoints()+".\n");
        displayText("Points from PublicObjectiveCard:\n-"+objectiveCardsEffects.returnName(cliState.getPublicObjectiveCardIds()[0]));
        displayText(": "+singlePlayerEndGame.getVectorPoints()[0]+";\n-"+objectiveCardsEffects.returnName(cliState.getPublicObjectiveCardIds()[1]));
        displayText(": "+singlePlayerEndGame.getVectorPoints()[1]+";\nPoints from PrivateObjectiveCard: "+singlePlayerEndGame.getVectorPoints()[2]);
        displayText(";\nPoints from empty cell: "+singlePlayerEndGame.getVectorPoints()[3]+";\n");
    }
}
