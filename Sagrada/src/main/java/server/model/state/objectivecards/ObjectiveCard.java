package server.model.state.objectivecards;

import server.model.state.boards.windowframe.WindowFrame;

public interface ObjectiveCard {
	int calculatePoints(WindowFrame windowFrame);
}
