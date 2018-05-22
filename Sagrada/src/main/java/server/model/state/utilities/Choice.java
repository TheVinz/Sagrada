package server.model.state.utilities;

import server.model.state.ModelObject.ModelObject;
import server.model.state.ModelObject.ModelType;

public class Choice implements ModelObject {
    private Integer choiceInt;

    public Choice(Integer choice){
        this.choiceInt = choice;
    }
    public Integer getChoice() {
        return choiceInt;
    }

    @Override
    public ModelType getType() {
        return ModelType.CHOICE;
    }
}
