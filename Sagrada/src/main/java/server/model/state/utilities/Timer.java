package server.model.state.utilities;

import server.controller.Controller;

import static java.lang.Thread.sleep;

public class Timer implements Runnable {
    private int cont = 0;
    private final int MAX_CONT = 45;
    private Controller controller;
    private volatile Thread blinker;

    public Timer(Controller controller){
        this.controller = controller;
    }
    @Override
    public synchronized void run() {
        while(blinker == Thread.currentThread()) {
            if(cont == 0 ){
                controller.notifyTimeout();
            }
            else {
                try {
                        wait(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                cont--;
                System.out.print(cont);
            }
        }

    }

    public void start(){
        cont = MAX_CONT;
        blinker=new Thread(this);
        blinker.start();
    }

    public void stop(){
        blinker=null;
    }

    public boolean timeFinished(){
        return (cont==0);
    }

    public Thread getBlinker(){
        return this.blinker;
    }
}
