package server.model.utilities;

import org.junit.Before;
import org.junit.Test;
import server.model.Model;
import server.model.state.boards.windowframe.WindowFrameList;
import server.model.state.objectivecards.privateobjectivecards.PrivateObjectiveCard;
import server.model.state.objectivecards.publicobjectivecards.PublicObjectiveCard;
import server.model.state.toolcards.ToolCard;
import server.model.state.utilities.Util;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class UtilTest {
 /*   WindowFrameList[] frames = new WindowFrameList[4];
    @Test
    public void getWindowFrameChoiche() {

        for(int i=0; i<5; i++) {
            frames[0] = Util.getWindowFrameChoiche()[0];
            assertNotEquals(frames[0], frames[2]);
            assertNotEquals(frames[1], frames[3]);
            assertNotNull(frames[0]);
            assertNotNull(frames[1]);
            assertNotNull(frames[2]);
            assertNotNull(frames[3]);
        }
    }

    @Test
    public void getToolCards() {
        Model model = mock(Model.class);
        ToolCard[] toolCard=Util.getToolCards(model);
        assertNotEquals(toolCard[0].getClass(), toolCard[1].getClass());
        assertNotEquals(toolCard[0].getClass(), toolCard[2].getClass());
        assertNotEquals(toolCard[1].getClass(), toolCard[2].getClass());
        assertNotNull(toolCard[0]);
        assertNotNull(toolCard[1]);
        assertNotNull(toolCard[2]);


        toolCard=Util.getToolCards(model);
        assertNotEquals(toolCard[0].getClass(), toolCard[1].getClass());
        assertNotEquals(toolCard[0].getClass(), toolCard[2].getClass());
        assertNotEquals(toolCard[1].getClass(), toolCard[2].getClass());
        assertNotNull(toolCard[0]);
        assertNotNull(toolCard[1]);
        assertNotNull(toolCard[2]);

        toolCard=Util.getToolCards(model);
        assertNotEquals(toolCard[0].getClass(), toolCard[1].getClass());
        assertNotEquals(toolCard[0].getClass(), toolCard[2].getClass());
        assertNotEquals(toolCard[1].getClass(), toolCard[2].getClass());
        assertNotNull(toolCard[0]);
        assertNotNull(toolCard[1]);
        assertNotNull(toolCard[2]);

        toolCard=Util.getToolCards(model);
        assertNotEquals(toolCard[0].getClass(), toolCard[1].getClass());
        assertNotEquals(toolCard[0].getClass(), toolCard[2].getClass());
        assertNotEquals(toolCard[1].getClass(), toolCard[2].getClass());
        assertNotNull(toolCard[0]);
        assertNotNull(toolCard[1]);
        assertNotNull(toolCard[2]);

        toolCard=Util.getToolCards(model);
        assertNotEquals(toolCard[0].getClass(), toolCard[1].getClass());
        assertNotEquals(toolCard[0].getClass(), toolCard[2].getClass());
        assertNotEquals(toolCard[1].getClass(), toolCard[2].getClass());
        assertNotNull(toolCard[0]);
        assertNotNull(toolCard[1]);
        assertNotNull(toolCard[2]);
    }

    @Test
    public void getPublicObjectiveCards() {
        PublicObjectiveCard[] cards=Util.getPublicObjectiveCards();
        assertNotEquals(cards[0].getClass(), cards[1].getClass());
        assertNotEquals(cards[0].getClass(), cards[2].getClass());
        assertNotEquals(cards[1].getClass(), cards[2].getClass());
        assertNotNull(cards[0]);
        assertNotNull(cards[1]);
        assertNotNull(cards[2]);

        cards=Util.getPublicObjectiveCards();
        assertNotEquals(cards[0].getClass(), cards[1].getClass());
        assertNotEquals(cards[0].getClass(), cards[2].getClass());
        assertNotEquals(cards[1].getClass(), cards[2].getClass());
        assertNotNull(cards[0]);
        assertNotNull(cards[1]);
        assertNotNull(cards[2]);

        cards=Util.getPublicObjectiveCards();
        assertNotEquals(cards[0].getClass(), cards[1].getClass());
        assertNotEquals(cards[0].getClass(), cards[2].getClass());
        assertNotEquals(cards[1].getClass(), cards[2].getClass());
        assertNotNull(cards[0]);
        assertNotNull(cards[1]);
        assertNotNull(cards[2]);

        cards=Util.getPublicObjectiveCards();
        assertNotEquals(cards[0].getClass(), cards[1].getClass());
        assertNotEquals(cards[0].getClass(), cards[2].getClass());
        assertNotEquals(cards[1].getClass(), cards[2].getClass());
        assertNotNull(cards[0]);
        assertNotNull(cards[1]);
        assertNotNull(cards[2]);

        cards=Util.getPublicObjectiveCards();
        assertNotEquals(cards[0].getClass(), cards[1].getClass());
        assertNotEquals(cards[0].getClass(), cards[2].getClass());
        assertNotEquals(cards[1].getClass(), cards[2].getClass());
        assertNotNull(cards[0]);
        assertNotNull(cards[1]);
        assertNotNull(cards[2]);

        cards=Util.getPublicObjectiveCards();
        assertNotEquals(cards[0].getClass(), cards[1].getClass());
        assertNotEquals(cards[0].getClass(), cards[2].getClass());
        assertNotEquals(cards[1].getClass(), cards[2].getClass());
        assertNotNull(cards[0]);
        assertNotNull(cards[1]);
        assertNotNull(cards[2]);
    }

    @Test
    public void getCard() {
        PrivateObjectiveCard[] cards=new PrivateObjectiveCard[5];
        cards[0]=Util.getCard();
        cards[1]=Util.getCard();
        cards[2]=Util.getCard();
        cards[3]=Util.getCard();
        cards[4]=Util.getCard();
        assertNotEquals(cards[0],cards[1]);
        assertNotEquals(cards[0],cards[2]);
        assertNotEquals(cards[0],cards[3]);
        assertNotEquals(cards[0],cards[4]);
        assertNotEquals(cards[1],cards[2]);
        assertNotEquals(cards[1],cards[3]);
        assertNotEquals(cards[1],cards[4]);
        assertNotEquals(cards[2],cards[3]);
        assertNotEquals(cards[2],cards[4]);
        assertNotEquals(cards[3],cards[4]);

    }*/
}