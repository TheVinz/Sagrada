package server.model.state.utilities;

/**
 * The <tt>Timer</tt> class contains the timer functions for the game.
 */

public class Timer implements Runnable {
    private int cont = 60;
    private final int MAX_CONT;
    private volatile Thread blinker;
    private TimerObserver timerObserver;

    /**
     * Creates a new <tt>Timer</tt> that waits for max_cont seconds and then notifies the {@link TimerObserver} associated.
     * @param timerObserver the observer of the timer.
     * @param max_cont the timeout of the timer.
     */
    public Timer(TimerObserver timerObserver, int max_cont){
        this.timerObserver = timerObserver;
        this.MAX_CONT = max_cont;
    }

    /**
     * Starts the timer.
     */
    @Override
    public synchronized void run() {
        Thread.currentThread().setName("Sagrada - timer");
        while(blinker == Thread.currentThread()) {
            if(cont == 0 ){
                timerObserver.notifyTimeout();
            }
            else {
                try {
                        wait(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                cont--;
            }
        }

    }

    /**
     * Reset the timer count and then runs a new timer thread.
     */
    public void start(){
        cont = MAX_CONT;
        blinker=new Thread(this);
        blinker.start();
    }

    /**
     * Stops this timer.
     */
    public void stop(){
        blinker=null;
    }

    /**
     * Returns the current active timer's {@link Thread}.
     * @return the last timer launched by the TimerObserver.
     */
    public Thread getBlinker(){
        return this.blinker;
    }
}
