package client.view.cli;


import org.omg.CORBA.PRIVATE_MEMBER;

public class CliDisplayer {
    private CliState cliState;
    private static CliDisplayer singleton;
    private ToolCardsEffects toolCardsEffects;

    public static CliDisplayer getDisplayer(){
        if(singleton==null) singleton=new CliDisplayer();
        return singleton;
    }

    public void setCliState(CliState state){ this.cliState = state; }


    public void displayText(String text){ System.out.print(text); }

    public void printMenu(){
        displayText("\t\t\t\tIT'S YOUR TURN\nWhat would you like to see?\n");
        displayText("-DraftPool press\t\t\t\t\t P\n");      //ho assegnato a ogni comando una lettera
        displayText("-Your State press\t\t\t\t\t V\n");
        displayText("-ToolCards press\t\t\t\t\t T\n");
        displayText("-PublicObjectiveCard press\t\t O\n");
        displayText("-RoundTrack press\t\t\t\t\t R\n");
        displayText("-Other's State press\t\t\t\t S\n\n ");
        displayText("What you wanna do?\n");
        displayText("-Place a dice press\t\t\t D\n");
        displayText("-Use a ToolCard press\t\t\t U\n");
        displayText("-In order to skip the turn press\t N\n");
    }

    public void printWindowFrame(CliPlayerState cliPlayerState){
        displayText("The WindowFrame is:\n");
        for(int i=0;i<4;i++){
            for(int j=0;j<5;j++) {
                displayText(cliPlayerState.getWindowFrame()[i][j]+" ");
                if(cliPlayerState.getWindowFrame()[i][j].length()==1)
                    displayText(" ");
            }
           displayText("\n");
        }
    }

    public void printWindowFrame(){
        printWindowFrame(cliState.getActivePlayer());
    }

    public void printRoundTrack(){
        displayText("The RoundTrack is:\n");
        for(int i=0;i<cliState.getRoundTrack().length; i++){
            displayText(((int) i+1) + ">>> ");
            for(int j=0; j<cliState.getRoundTrack()[i].length; i++)
                displayText(j + "_" + cliState.getRoundTrack()[i][j] + " | ");
            displayText("\n");
        }
    }

    public void printToolCard(){
        displayText("You can use these ToolCards:\n");
        for(int i=0;i<3;i++){
            displayText("The ToolCard number " +i+" is "+toolCardsEffects.returnEffects(cliState.getToolCardIds()[i]));     //numero è riferito all'ordine nell'array, mentre j indica la ToolCard vera e propria,
                                                                       //per ora un numero a cui dovremo associare la vera e propria ToolCard.
        }
    }

    public void printDraftPool(){
        displayText("In the DraftPool there are those Dices ");
        for(int i=0; i<cliState.getDraftPool().length;i++){
            displayText(cliState.getDraftPool()[i]+" ");
        }
        displayText("\n");
    }

    public void printPublicObjectiveCards(){
        displayText("There are these PublicObjectiveCard:\n");
        for(int i=0;i<3;i++){
            Integer j=cliState.getPublicObjectiveCardIds()[i];
            displayText("The ObjectiveCard number "+ i+" is "+ j);
        }
    }

    public void printPrivateObjectiveCard(){
        displayText("Your PrivateObjectiveCard is: ");
        displayText(cliState.getPrivateObjectiveCard()+"\n");
    }

    public void printFavorTokens(){
        printFavorTokens(cliState.getActivePlayer());
        }

    public void printFavorTokens(CliPlayerState cliPlayerState){
       displayText("The FavorTokens are "+cliPlayerState.getFavorTokens()+"\n");
        }

    public void printState(){
        displayText("Your State is:\n");
        printFavorTokens();
        printWindowFrame();
        printPrivateObjectiveCard();
    }

    public void printState(String name){
        displayText("The state of "+name+" is:\n");
        printFavorTokens(cliState.getCliPlayerState(name));
        printWindowFrame(cliState.getCliPlayerState(name));
    }

}
