package common.RemoteMVC;

import server.observer.Observer;

import java.rmi.Remote;

/*I vari param rappresentano le coordinate:
*   DraftPoolCell  ( index )
*   RoundTrackCell ( round , index )
*   WindowFrameCell ( row , column )
*Rigorosamente nell' ordine descritto
*   */

public interface RemoteView extends Remote {
    void move(int player, int sourceType, int destType, int param1, int param2, int param3); // DP to WF
    void move(int player, int sourceType, int destType, int param1, int param2, int param3, int param4); // RT/WF to WF/RT

    void updateCell(int player, int type, int index, int value, char color); //DraftPoolCell
    void updateCell(int player, int type, int param1,int param2, int value, char color); //RoundTrackCell/DraftPoolCell

    void loadToolCards(int[] toolCards);

    void refilledDraftPool(int[] values, char[] colors);

    void startTurn(int player);

}
