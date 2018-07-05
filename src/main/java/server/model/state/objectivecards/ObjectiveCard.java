package server.model.state.objectivecards;

import server.model.state.boards.windowframe.WindowFrame;

/**
 * <tt>ObjectiveCard</tt> is an interface class that represents the {@link server.model.state.objectivecards.privateobjectivecards.PrivateObjectiveCard} and the {@link server.model.state.objectivecards.publicobjectivecards.PublicObjectiveCard}.
 *
 */
public interface ObjectiveCard {
	int calculatePoints(WindowFrame windowFrame);
}
