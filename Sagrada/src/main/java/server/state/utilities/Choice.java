package server.state.utilities;

import server.state.ModelObject;

public class Choice implements ModelObject {
    private Integer choiceInt;

    public Choice(Integer choice){
        this.choiceInt = choice;
    }
    public Integer getChoice() {
        return choiceInt;
    }

    @Override
    public int getType() {
        return CHOICE;
    }
}
