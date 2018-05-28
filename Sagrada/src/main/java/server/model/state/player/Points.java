package server.model.state.player;

public class Points {
    private int[] pointsFromPublicCard= new int[3];
    private int pointsFromPrivateCard;
    private int pointsFromEmptyCells;
    private int pointsFromFavorTokens;



    public void setPointsFromPublicCard(int card, int points) {
        this.pointsFromPublicCard[card] = points;
    }

    public int getPointsFromPublicCard(int card) {
       return this.pointsFromPublicCard[card];
    }

    public int getPointsFromPrivateCard() {
        return pointsFromPrivateCard;
    }

    public void setPointsFromPrivateCard(int pointsFromPrivateCard) {
        this.pointsFromPrivateCard = pointsFromPrivateCard;
    }

    public int getPointsFromEmptyCells() {
        return pointsFromEmptyCells;
    }

    public void setPointsFromEmptyCells(int pointsFromEmptyCells) { //saved negative
        this.pointsFromEmptyCells = (-1)*pointsFromEmptyCells;
    }

    public int getPointsFromFavorTokens() {
        return pointsFromFavorTokens;
    }

    public void setPointsFromFavorTokens(int pointsFromFavorTokens) {
        this.pointsFromFavorTokens = pointsFromFavorTokens;
    }

    public int getPointsFromPublicCards(){
        return pointsFromPublicCard[0]+pointsFromPublicCard[1]+pointsFromPublicCard[2];
    }
    public int getFinalPoints(){
        return getPointsFromPublicCards()+pointsFromPrivateCard+pointsFromFavorTokens+pointsFromEmptyCells;
    }
}
