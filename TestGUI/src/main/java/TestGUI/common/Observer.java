package TestGUI.common;


import TestGUI.server.model.boards.Cell;
import TestGUI.server.model.toolcards.ToolCard;

public interface Observer {

    void updateMove(Cell source, Cell target);

    void updateRefillDraftPool();
    void updateCell(Cell cell);

    void loadToolCards(ToolCard[] toolCards);
}
