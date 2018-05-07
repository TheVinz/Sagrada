package server.state.utilities;

import server.state.ModelObject;

public class Choice implements ModelObject {
    private Integer choice;

    public Choice(Integer choice){
        this.choice = choice;
    }
    public Integer getChoice() {
        return choice;
    }

    @Override
    public String getType() {
        return "Choice";
    }
}
