package server.model.state.objectivecards;

import server.model.state.boards.windowframe.WindowFrame;

/**
 * <tt>ObjectiveCard</tt> is an interface class that represents the {@link server.model.state.objectivecards.privateobjectivecards.PrivateObjectiveCard} and the {@link server.model.state.objectivecards.publicobjectivecards.PublicObjectiveCard}.
 *
 */
public interface ObjectiveCard {

	/**
	 * Calculates the points scored thanks to the <tt>ObjectiveCard</tt> inside the given {@link WindowFrame}.
	 * @param windowFrame the window frame in which the card will calculate the points scored.
	 * @return the points scored by the frame owner.
	 */
	int calculatePoints(WindowFrame windowFrame);
}
