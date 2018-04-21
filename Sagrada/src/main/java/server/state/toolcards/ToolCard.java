package server.state.toolcards;

import common.exceptions.InvalidMoveException;

public interface ToolCard {
	boolean hasNext();
	void setParameter(Object o) throws InvalidMoveException;
	void doAbility() throws InvalidMoveException;
	void start();
}