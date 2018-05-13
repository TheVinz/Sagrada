package client.view.cli;

public class CliPlayerState {
    private String name;
    private String[][] windowFrame = new String[4][5];
    private String privateObjectiveCard;
    private Integer favorTokens;
    public CliPlayerState(String name,String[][] windowFrame,String privateObjectiveCard,Integer favorTokens){
        this.name=name;
        this.windowFrame=windowFrame;
        this.privateObjectiveCard=privateObjectiveCard;
        this.favorTokens=favorTokens;
    }

    public String[][] getWindowFrame() {
        return windowFrame;
    }     //nelle celle salviamo l'informazione come una stringa. Se casella bianca mettiamo una X;
        //se casella con restrizione mettiamo il numero se restrizione di numero o la letterla del colore se restrizione colore
        //se casella con dado mettiamo lettera(del colore del dado) e numero del dado

    public String getPrivateObjectiveCard() {
        return privateObjectiveCard;
    }   //differenziate dal colore quindi avremo:RED, GREEN..

    public Integer getFavorTokens() {
        return favorTokens;
    }   //un numero

    public String getName() {
        return name;
    }
}
