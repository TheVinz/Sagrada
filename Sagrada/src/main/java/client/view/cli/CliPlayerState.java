package client.view.cli;

public class CliPlayerState {
    private String name;
    private String[][] windowFrame = new String[4][5];
    private String privateObjectiveCard;
    private Integer favorTokens;

    public String[][] getWindowFrame() {
        return windowFrame;
    }

    public String getPrivateObjectiveCard() {
        return privateObjectiveCard;
    }

    public Integer getFavorTokens() {
        return favorTokens;
    }

    public String getName() {
        return name;
    }
}
