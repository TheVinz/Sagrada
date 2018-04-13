package common.toolcards.;

import common.exceptions.InvalidMoveException;

public interface ToolCard {
	Object nextParameter();
	void setParameter(Object o);
	void doAbility() throws InvalidMoveException;
}