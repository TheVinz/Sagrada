package server.state.utilities;

import org.junit.Test;
import server.Model;
import server.state.boards.windowframe.WindowFrame;
import server.state.objectivecards.privateobjectivecards.PrivateObjectiveCard;
import server.state.objectivecards.publicobjectivecards.PublicObjectiveCard;
import server.state.toolcards.ToolCard;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class UtilTest {

    @Test
    public void getWindowFrameChoiche() {
        WindowFrame[] frames= Util.getWindowFrameChoiche();
        assertNotEquals(frames[0], frames[2]);
        assertNotEquals(frames[1], frames[3]);
        frames= Util.getWindowFrameChoiche();
        assertNotEquals(frames[0], frames[2]);
        assertNotEquals(frames[1], frames[3]);
        frames= Util.getWindowFrameChoiche();
        assertNotEquals(frames[0], frames[2]);
        assertNotEquals(frames[1], frames[3]);
        frames= Util.getWindowFrameChoiche();
        assertNotEquals(frames[0], frames[2]);
        assertNotEquals(frames[1], frames[3]);
        frames= Util.getWindowFrameChoiche();
        assertNotEquals(frames[0], frames[2]);
        assertNotEquals(frames[1], frames[3]);
        frames= Util.getWindowFrameChoiche();
        assertNotEquals(frames[0], frames[2]);
        assertNotEquals(frames[1], frames[3]);
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

    }
}