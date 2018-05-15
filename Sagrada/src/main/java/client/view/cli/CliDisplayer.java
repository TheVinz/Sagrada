package client.view.cli;


import org.omg.CORBA.PRIVATE_MEMBER;

public class CliDisplayer {
    private CliState cliState;
    private static CliDisplayer singleton;

    public static CliDisplayer getDisplayer(){
        if(singleton==null) singleton=new CliDisplayer();
        return singleton;
    }

    public void setCliState(CliState state){ this.cliState = state; }


    public void displayText(String text){ System.out.print(text); }

    public void printMenu(){
        displayText("\t\t\t\tTOCCA A TE\nCosa vuoi vedere?\n");
        displayText("-Per vedere la DraftPool premi\t\t\t\t\t P\n");      //ho assegnato a ogni comando una lettera
        displayText("-Per vedere il tuo stato premi\t\t\t\t\t V\n");
        displayText("-Per vedere le ToolCard premi\t\t\t\t\t T\n");
        displayText("-Per vedere le PublicObjectiveCard premi\t\t O\n");
        displayText("-Per vedere la RoundTrack premi\t\t\t\t\t R\n");
        displayText("-Per vedere lo stato di altri premi\t\t\t\t S\n\n ");
        displayText("Cosa vuoi fare?\n");
        displayText("-Per posizionare un dado premi\t\t\t D\n");
        displayText("-Per usare una ToolCard premi\t\t\t U\n");
        displayText("-Per passare il turno premi\t\t\t\t N\n");
    }

    public void printWindowFrame(CliPlayerState cliPlayerState){
        displayText("La windowframe è:\n");
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
        displayText("RoundTrack:\n");
        for(int i=0;i<cliState.getRoundTrack().size();i++){
            displayText(((int) i+1) + ">>> ");
            for(int j=0; j<cliState.getRoundTrack().get(i).size(); i++)
                displayText(j + "_" + cliState.getRoundTrack().get(i).get(j) + " | ");
            displayText("\n");
        }
    }

    public void printToolCard(){
        displayText("Puoi usare queste Toolcard:\n");
        for(int i=0;i<3;i++){
            displayText("La ToolCard numero " +i+" è la ToolCard "+cliState.getToolCardIds()[i]+"\n");     //numero è riferito all'ordine nell'array, mentre j indica la ToolCard vera e propria,
                                                                       //per ora un numero a cui dovremo associare la vera e propria ToolCard.
        }
    }

    public void printDraftPool(){
        displayText("Nella DraftPool ci sono questi dadi: ");
        for(int i=0; i<cliState.getDraftPool().size();i++){
            displayText(cliState.getDraftPool().get(i)+" ");
        }
        displayText("\n");
    }

    public void printPublicObjectiveCards(){
        displayText("Sul campo di gioco ci sono queste PublicObjectiveCard:\n");
        for(int i=0;i<3;i++){
            Integer j=cliState.getPublicObjectiveCardIds()[i];
            displayText("L'ObjectiveCard numero "+ i+" è la ObjectiveCard "+ j+"\n");
        }
    }

    public void printPrivateObjectiveCard(){
        displayText("La tua carta privata: ");
        displayText(cliState.getPrivateObjectiveCard()+"\n");
    }

    public void printFavorTokens(){
        printFavorTokens(cliState.getActivePlayer());
        }

    public void printFavorTokens(CliPlayerState cliPlayerState){
       displayText("I FavorTokens sono "+cliPlayerState.getFavorTokens()+"\n");
        }

    public void printState(){
        displayText("Il tuo stato è:\n");
        printFavorTokens();
        printWindowFrame();
        printPrivateObjectiveCard();
    }

    public void printState(String name){
        displayText("Lo stato di "+name+" è:\n");
        printFavorTokens(cliState.getCliPlayerState(name));
        printWindowFrame(cliState.getCliPlayerState(name));
    }

}
