package common.RemoteMVC;

import common.response.Response;
import server.model.state.ModelObject.ModelType;

import java.rmi.Remote;
import java.rmi.RemoteException;

/*I vari param rappresentano le coordinate:
*   DraftPoolCell  ( index )
*   RoundTrackCell ( round , index )
*   WindowFrameCell ( row , column )
*Rigorosamente nell' ordine descritto
*   */

public interface RemoteView extends Remote {
    void move(int player, Response sourceType, Response destType, int param1, int param2, int param3) throws RemoteException; // DP to WF
    void move(int player, Response sourceType, Response destType, int param1, int param2, int param3, int param4) throws RemoteException; // RT/WF to WF/RT

    void updateCell(int player, Response type, int index, int value, char color) throws RemoteException; //DraftPoolCell
    void updateCell(int player, Response type, int param1,int param2, int value, char color) throws RemoteException; //RoundTrackCell/DraftPoolCell

    void setId(int id) throws RemoteException;

    void loadToolCards(int[] toolCards) throws RemoteException;

    void refilledDraftPool(int[] values, char[] colors) throws RemoteException;

    void loadPublicObjectiveCards(int[] cards) throws RemoteException;

    void loadWindowFrameChoice(String[] reps, int[] favorTokens) throws RemoteException;

    void loadPlayers(String[] names, int[] ids, String[] windowFrameReps, int[] windowFrameFavorTokens) throws RemoteException;

    void toolCardUsed(int player, int index, int tokens) throws RemoteException;

    void loadPrivateObjectiveCard(char color) throws RemoteException;

    void newTurn(int player) throws RemoteException;

    void notifyDiceDraw(int player, char color) throws RemoteException;

    void updateRoundTrack(int round, int[] values, char[] colors) throws RemoteException;

    void nextParameter(Response response) throws RemoteException;

    void error(String message) throws RemoteException;

    void wrongParameter(String message) throws RemoteException;

    void endGame(char[] cards, int[] points) throws RemoteException;
}
