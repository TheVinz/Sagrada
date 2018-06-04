package server.model.state.objectivecards.publicobjectivecards;

import server.model.state.boards.windowframe.WindowFrame;

/**
 * The ColoredDiagonal class implements the method that add one point for every {@link server.model.state.dice.Dice}
 * with same color adjacent diagonally on the {@link server.model.state.boards.windowframe.WindowFrame}.
 */
public class ColoredDiagonal extends PublicObjectiveCard{
    /**
     * This method calculate the points to add to the final points looking at the adjacent dices on a specific WindowFrame.
     * @param windowFrame WindowFrame of the player.
     * @return an int that indicates the points.
     */
    public int calculatePoints(WindowFrame windowFrame) {
        int points=0;
        for(int i=0; i<WindowFrame.ROWS; i++){
            for(int j=0; j<WindowFrame.COLUMNS; j++){
                if(isValid(i,j,windowFrame)) points++;
            }
        }
        return points;
    }

    /**
     * Gets the number that indicates the PublicObjectiveCard.
     * @return an int corresponding to COLORED_DIAGONAL.
     */
    @Override
    public int getNumber() {
        return COLORED_DIAGONAL;
    }

    private boolean isValid(int i, int j, WindowFrame windowFrame){
        if(!windowFrame.getCell(i,j).isEmpty()) {
            return isValidUpRight(i,j, windowFrame) || isValidUpLeft(i,j, windowFrame)
                    || isValidDownRight(i,j, windowFrame) || isValidDownLeft(i,j, windowFrame);
        }
        else return false;
    }

    private boolean isValidDownLeft(int i, int j, WindowFrame windowFrame) {
        if(!isValidRow(i+1)) return false;
        else if(!isValidColumn(j-1)) return false;
        else if(windowFrame.getCell(i+1, j-1).isEmpty()) return false;
        else
            return windowFrame.getCell(i+1,j-1).getDice().getColor()==windowFrame.getCell(i,j).getDice().getColor();
    }

    private boolean isValidDownRight(int i, int j, WindowFrame windowFrame) {
        if(!isValidRow(i+1)) return false;
        else if(!isValidColumn(j+1)) return false;
        else if(windowFrame.getCell(i+1, j+1).isEmpty()) return false;
        else
            return windowFrame.getCell(i+1,j+1).getDice().getColor()==windowFrame.getCell(i,j).getDice().getColor();
    }

    private boolean isValidUpLeft(int i, int j, WindowFrame windowFrame) {
        if(!isValidRow(i-1)) return false;
        else if(!isValidColumn(j-1)) return false;
        else if(windowFrame.getCell(i-1, j-1).isEmpty()) return false;
        else
            return windowFrame.getCell(i-1,j-1).getDice().getColor()==windowFrame.getCell(i,j).getDice().getColor();
    }

    private boolean isValidUpRight(int i, int j, WindowFrame windowFrame) {
        if(!isValidRow(i-1)) return false;
        else if(!isValidColumn(j+1)) return false;
        else if(windowFrame.getCell(i-1, j+1).isEmpty()) return false;
        else
            return windowFrame.getCell(i-1, j+1).getDice().getColor()==windowFrame.getCell(i,j).getDice().getColor();
    }

    private boolean isValidRow(int i){
        return i>=0 && i<WindowFrame.ROWS;
    }

    private boolean isValidColumn(int j){
        return j>=0 && j<WindowFrame.COLUMNS;
    }
}
