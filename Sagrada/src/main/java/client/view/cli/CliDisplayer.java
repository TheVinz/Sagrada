package client.view.cli;


public class CliDisplayer {
    CliState cliState;

    public void displayText(String text){
        System.out.println(text);

    }

    public void printMenu(){
        displayText("Tocca a te, cosa vuoi vedere?/n");
        displayText("Per vedere la DraftPool premi P/n");      //ho assegnato a ogni comando una lettera
        displayText("Per vedere la tua vetrata premi V/n");
        displayText("Per vedere le ToolCard premi T/n");
        displayText("Per vedere i tuoi FavorToken premi F/n");
        displayText("Per vedere la tua PrivateObjectiveCard premi C/n");
        displayText("Per vedere le PublicObjectiveCard premi O/n");
        displayText("Per vedere la RoundTrack premi R/n");
        displayText("Per vedere ");
        displayText("Cosa vuoi fare?/n");
        displayText("Per posizionare un dado premi D/n");
        displayText("Per usare una ToolCard premi U/n");
        displayText("Per passare il turno premi N/n");



    }

    public void printWindowFrame(CliPlayerState cliPlayerState){

        for(int i=0;i<5;i++){
            for(int j=0;j<4;j++) {
                displayText(cliPlayerState.getWindowFrame()[i][j]);      //passiamo a displayText cella per cella e lui gestirà la stampa
            }
            displayText("/n");   //stampare uno spazio dopo aver stampato una riga
        }
    }

    public void printRoundTrack(){
        displayText("RoundTrack");
        for(int i=0;i<cliState.getRoundTrack().size();i++){
            displayText("casella"+i);
         //   for (int j=0;j<cliState.getRoundTrack()
        }


    }

    public void printToolCard(){
        displayText("Puoi usare queste Toolcard:");
        for(int i=0;i<3;i++){
            Integer j=cliState.getToolCardIds()[i];
            displayText("La ToolCard numero" +i+"è la ToolCard"+j);     //numero è riferito all'ordine nell'array, mentre j indica la ToolCard vera e propria,
                                                                       //per ora un numero a cui dovremo associare la vera e propria ToolCard.
        }
    }
    public void printDraftPool(CliPlayerState cliPlayerState){
        displayText("Nella DraftPool ci sono questi dadi");
        for(int i=0;i<cliState.getDraftPool().size();i++){
            displayText(cliState.getDraftPool().get(i));
        }
    }

    public void printPublicObjectiveCards(){
        displayText("Sul campo di gioco ci sono queste PublicObjectiveCard:");
        for(int i=0;i<3;i++){
            Integer j=cliState.getPublicObjectiveCardIds()[i];
            displayText("L'ObjectiveCard numero"+ i+" è la ObjectiveCard"+ j);}


    }

    public void printPrivateObjectiveCard(CliPlayerState cliPlayerState){
        displayText("la tua carta privata:");
        displayText(cliPlayerState.getPrivateObjectiveCard());
        displayText("/n");
    }



}
