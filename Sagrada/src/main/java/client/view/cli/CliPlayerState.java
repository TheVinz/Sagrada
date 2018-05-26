package client.view.cli;

public class CliPlayerState {
    private String name;
    private String windowFrameRep;
    private String[][] windowFrame = new String[4][5];
    private Integer favorTokens;
    private boolean secondTurn;
    private int id;

    public CliPlayerState(String name, int id, String rep, int favorTokens){
        this.secondTurn = true;
        this.name=name;
        this.id = id;
        this.windowFrameRep=rep;
        this.favorTokens=favorTokens;
        for(int row=0; row<windowFrame.length; row++){
            for(int col=0; col<windowFrame[row].length; col++){
                setEmpty(row, col);
            }
        }
    }

    public void setEmpty(int row, int col){
        if(windowFrameRep.charAt(row*5+col) == '0' ) windowFrame[row][col]="X";
        else windowFrame[row][col]= "" + windowFrameRep.charAt(row*5+col);
    }
    public void setWindowFrame(String[][] windowFrame){
        this.windowFrame=windowFrame;
    }
    public void setFavorTokens(int favorTokens){
        this.favorTokens=favorTokens;
    }

    public String[][] getWindowFrame() {
        return windowFrame;
    }     //nelle celle salviamo l'informazione come una stringa. Se casella bianca mettiamo una X;
        //se casella con restrizione mettiamo il numero se restrizione di numero o la letterla del colore se restrizione colore
        //se casella con dado mettiamo lettera(del colore del dado) e numero del dado

    public Integer getFavorTokens() {
        return favorTokens;
    }   //un numero

    public String getName() {
        return name;
    }

    public int getId() {
        return this.id;
    }

    public void removeFavorTokens(int tokens) {
        favorTokens=favorTokens-tokens;
    }

    public void setSecondTurn(boolean secondTurn){
        this.secondTurn = secondTurn;
    }

    public boolean isSecondTurn(){
        return secondTurn;
    }
}
