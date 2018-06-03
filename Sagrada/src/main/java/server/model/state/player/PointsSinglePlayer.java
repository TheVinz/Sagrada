package server.model.state.player;

public class PointsSinglePlayer {
    private int[] pointsFromPublicCard= new int[2];
    private int pointsFromPrivateCard;
    private int pointsFromEmptyCells;
    private int objectivePoints;

    public int[] getPointsFromPublicCard() {
        return pointsFromPublicCard;
    }

    public void setPointsFromPublicCard(int card,int points) {
        this.pointsFromPublicCard[card] = points;
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

    public void setPointsFromEmptyCells(int pointsFromEmptyCells) {
        this.pointsFromEmptyCells = (-3)*pointsFromEmptyCells;
    }
    public int getPointsFromPublicCards(){
        return pointsFromPublicCard[0]+pointsFromPublicCard[1];
    }
    public int getFinalPoints(){
        return getPointsFromPublicCards()+pointsFromPrivateCard+pointsFromEmptyCells;
    }

    public int getObjectivePoints() {
        return objectivePoints;
    }

    public void setObjectivePoints(int objectivePoints) {
        this.objectivePoints = objectivePoints;
    }
}

