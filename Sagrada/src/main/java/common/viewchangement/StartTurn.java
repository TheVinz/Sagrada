package common.viewchangement;

import common.ChangementVisitor;

import java.rmi.RemoteException;

public class StartTurn implements Changement{
    @Override
    public void change(ChangementVisitor changementVisitor)  {
        try{
            changementVisitor.change(this);
        }catch(RemoteException e){

        }
    }
}
