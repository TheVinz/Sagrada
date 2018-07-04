package client.view.cli;

/**
 * The <tt>CliPlayerState</tt> class represents the state of the {@link server.model.state.player.Player} in a game.
 * This class has useful method to get information on the state of the player.
 */
public class CliPlayerState {
    private String name;
    private String windowFrameRep;
    private String[][] windowFrame = new String[4][5];
    private Integer favorTokens;
    private boolean secondTurn;
    private int id;

    /**
     * Initialize a CliPlayerState. Save a new Player with the name, the id and the
     * {@link server.model.state.boards.windowframe.WindowFrame} of the Player.
     * @param name the name of the Player.
     * @param id the if of the Player.
     * @param rep a String which indicates the WindowFrame of the Player.
     * @param favorTokens an int which indicates the FavorTokens of the Player.
     */
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

    /**
     * Set a {@link server.model.state.boards.Cell} of a WindowFrame empty.
     * @param row the row of the Cell in the WindowFrame.
     * @param col the column of the Cell in the WindowFrame.
     */
    public void setEmpty(int row, int col){
        if(windowFrameRep.charAt(row*5+col) == '0' ) windowFrame[row][col]="0";
        else windowFrame[row][col]= "" + windowFrameRep.charAt(row*5+col);
    }

    /**
     * Set the WindowFrame of the Player.
     * @param windowFrame a matrix of String which describes all the Cell of the WindowFrame of the Player.
     */
    public void setWindowFrame(String[][] windowFrame){
        this.windowFrame=windowFrame;
    }

    public void setFavorTokens(int favorTokens){
        this.favorTokens=favorTokens;
    }

    /**
     * Gets the WindowFrame of the Player.
     * @return a matrix of String which represents the WindowFrame of the Player.
     */
    public String[][] getWindowFrame() {
        return windowFrame;
    }

    /**
     * Gets the FavorTokens of the Player.
     * @return an integer which represents the FavorTokens of the Player.
     */
    public Integer getFavorTokens() {
        return favorTokens;
    }

    /**
     * Gets the name of the Player.
     * @return a String which represents the name of the Player.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the id of the Player.
     * @return an int which represents the id of the Player.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Removes one or two FavorTokens after the Player used a {@link server.model.state.toolcards.ToolCard}.
     * @param tokens an int which represents how many FavorTokens are to remove. One if the ToolCard is used for the first
     *               time, two if not.
     */
    public void removeFavorTokens(int tokens) {
        favorTokens=favorTokens-tokens;
    }

    /**
     * Set the turn of the Player on the second turn.
     * @param secondTurn
     */
    public void setSecondTurn(boolean secondTurn){
        this.secondTurn = secondTurn;
    }

    /**
     * Return a boolean which indicates if it is the second turn of the Player or not.
     * @return true if it is the second turn of the Player, false if it isn't.
     */
    public boolean isSecondTurn(){
        return secondTurn;
    }
}
