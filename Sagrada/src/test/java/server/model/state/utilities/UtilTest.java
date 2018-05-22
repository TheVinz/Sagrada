package server.model.state.utilities;

import org.junit.Before;
import org.junit.Test;
import server.model.state.boards.windowframe.WindowFrameList;
import server.model.state.objectivecards.privateobjectivecards.PrivateObjectiveCard;
import server.model.state.objectivecards.publicobjectivecards.PublicObjectiveCard;

import static org.junit.Assert.*;

public class UtilTest {
    private WindowFrameList[] result = new WindowFrameList[4];
    private Util util;
    private PublicObjectiveCard[] resultObjectiveCard=new PublicObjectiveCard[3];
    private PrivateObjectiveCard resultPrivateCard;

    @Before
    public void initClass(){
        util = new Util();
    }

    @Test
    public void shouldGetWindowFrameChoice(){
        result = util.getWindowFrameChoiche();
        System.out.println(result[0].getRep()+" "+result[0].getFavorToken());
        System.out.println(result[1].getRep()+" "+result[1].getFavorToken());
        System.out.println(result[2].getRep()+" "+result[2].getFavorToken());
        System.out.println(result[3].getRep()+" "+result[3].getFavorToken());
        result = util.getWindowFrameChoiche();
        System.out.println(result[0].getRep()+" "+result[0].getFavorToken());
        System.out.println(result[1].getRep()+" "+result[1].getFavorToken());
        System.out.println(result[2].getRep()+" "+result[2].getFavorToken());
        System.out.println(result[3].getRep()+" "+result[3].getFavorToken());
        result = util.getWindowFrameChoiche();
        System.out.println(result[0].getRep()+" "+result[0].getFavorToken());
        System.out.println(result[1].getRep()+" "+result[1].getFavorToken());
        System.out.println(result[2].getRep()+" "+result[2].getFavorToken());
        System.out.println(result[3].getRep()+" "+result[3].getFavorToken());
        result = util.getWindowFrameChoiche();
        System.out.println(result[0].getRep()+" "+result[0].getFavorToken());
        System.out.println(result[1].getRep()+" "+result[1].getFavorToken());
        System.out.println(result[2].getRep()+" "+result[2].getFavorToken());
        System.out.println(result[3].getRep()+" "+result[3].getFavorToken());
        result = util.getWindowFrameChoiche();
        System.out.println(result[0].getRep()+" "+result[0].getFavorToken());
        System.out.println(result[1].getRep()+" "+result[1].getFavorToken());
        System.out.println(result[2].getRep()+" "+result[2].getFavorToken());
        System.out.println(result[3].getRep()+" "+result[3].getFavorToken());
        result = util.getWindowFrameChoiche();
        System.out.println(result[0].getRep()+" "+result[0].getFavorToken());
        System.out.println(result[1].getRep()+" "+result[1].getFavorToken());
        System.out.println(result[2].getRep()+" "+result[2].getFavorToken());
        System.out.println(result[3].getRep()+" "+result[3].getFavorToken());


    }
    @Test
    public void shouldGetPublicObjectiveCards(){
        resultObjectiveCard = util.getPublicObjectiveCards();
        System.out.println(resultObjectiveCard[0]);
        System.out.println(resultObjectiveCard[1]);
        System.out.println(resultObjectiveCard[2]);
        resultObjectiveCard = util.getPublicObjectiveCards();
        System.out.println(resultObjectiveCard[0]);
        System.out.println(resultObjectiveCard[1]);
        System.out.println(resultObjectiveCard[2]);
        resultObjectiveCard = util.getPublicObjectiveCards();
        System.out.println(resultObjectiveCard[0]);
        System.out.println(resultObjectiveCard[1]);
        System.out.println(resultObjectiveCard[2]);
        resultObjectiveCard = util.getPublicObjectiveCards();
        System.out.println(resultObjectiveCard[0]);
        System.out.println(resultObjectiveCard[1]);
        System.out.println(resultObjectiveCard[2]);
    }
    @Test
    public void shouldPrivateObjectiveCard(){
        resultPrivateCard = util.getCard();
        System.out.println(resultPrivateCard.getColor());
        resultPrivateCard = util.getCard();
        System.out.println(resultPrivateCard.getColor());
        resultPrivateCard = util.getCard();
        System.out.println(resultPrivateCard.getColor());
        resultPrivateCard = util.getCard();
        System.out.println(resultPrivateCard.getColor());
        resultPrivateCard = util.getCard();
        System.out.println(resultPrivateCard.getColor());
    }
}