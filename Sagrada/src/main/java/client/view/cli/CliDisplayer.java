package client.view.cli;


public class CliDisplayer {
    CliState cliState;

    public void displayText(String text){

        System.out.print(text);

    }

    public void printMenu(){
        displayText("\t\t\t\tTOCCA A TE\nCosa vuoi vedere?\n");
        displayText("-Per vedere la DraftPool premi\t\t\t\t\t P\n");      //ho assegnato a ogni comando una lettera
        displayText("-Per vedere la tua vetrata premi\t\t\t\t V\n");
        displayText("-Per vedere le ToolCard premi\t\t\t\t\t T\n");
        displayText("-Per vedere i tuoi FavorToken premi\t\t\t\t F\n");
        displayText("-Per vedere la tua PrivateObjectiveCard premi\t C\n");
        displayText("-Per vedere le PublicObjectiveCard premi\t\t O\n");
        displayText("-Per vedere la RoundTrack premi\t\t\t\t\t R\n");
        displayText("-Per vedere\n\n ");
        displayText("Cosa vuoi fare?\n");
        displayText("-Per posizionare un dado premi\t\t\t D\n");
        displayText("-Per usare una ToolCard premi\t\t\t U\n");
        displayText("-Per passare il turno premi\t\t\t\t N\n");



    }

    public void printWindowFrame(CliPlayerState cliPlayerState){
        displayText("La tua windowframe:\n");
        for(int i=0;i<4;i++){
            for(int j=0;j<5;j++) {
                displayText(cliPlayerState.getWindowFrame()[i][j]+" ");
                if(cliPlayerState.getWindowFrame()[i][j].length()==1)
                    displayText(" ");
            }
           displayText("\n");
        }
    }

    public void printRoundTrack(CliState cliState){
        displayText("RoundTrack:\n");
        for(int i=0;i<cliState.getRoundTrack().size();i++){
            displayText(i+": ");
            for(int j=0;j<cliState.getRoundTrack().get(i).length();j=j+2){
                displayText(cliState.getRoundTrack().get(i).substring(j,j+2)+" ");
            }
            displayText("\n");
        }

    }

    public void printToolCard(CliState cliState){
        displayText("Puoi usare queste Toolcard:\n");
        for(int i=0;i<3;i++){
            displayText("La ToolCard numero " +i+" è la ToolCard "+cliState.getToolCardIds()[i]+"\n");     //numero è riferito all'ordine nell'array, mentre j indica la ToolCard vera e propria,
                                                                       //per ora un numero a cui dovremo associare la vera e propria ToolCard.
        }
    }
    public void printDraftPool(CliState cliState){
        displayText("Nella DraftPool ci sono questi dadi: ");
        for(int i=0; i<cliState.getDraftPool().size();i++){
            displayText(cliState.getDraftPool().get(i)+" ");
        }
        displayText("\n");
    }

    public void printPublicObjectiveCards(CliState cliState){
        displayText("Sul campo di gioco ci sono queste PublicObjectiveCard:\n");
        for(int i=0;i<3;i++){
            Integer j=cliState.getPublicObjectiveCardIds()[i];
            displayText("L'ObjectiveCard numero "+ i+" è la ObjectiveCard "+ j+"\n");
        }
    }

    public void printPrivateObjectiveCard(CliPlayerState cliPlayerState){
        displayText("La tua carta privata: ");
        displayText(cliPlayerState.getPrivateObjectiveCard()+"\n");
    }

    public void printFavorTokens(CliPlayerState cliPlayerState){
        displayText("I tuoi FavorTokens sono "+cliPlayerState.getFavorTokens()+"\n");
        }



}
