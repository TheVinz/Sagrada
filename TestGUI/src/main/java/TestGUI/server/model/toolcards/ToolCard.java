package TestGUI.server.model.toolcards;

import TestGUI.server.model.exceptions.InvalidMoveException;

public interface ToolCard {
    Object nextParameter();
    void start();
    boolean setParameter(Object o) throws InvalidMoveException;
    void doAbility() throws InvalidMoveException;
    String getName();
}
