package server.model.state.utilities;

/**
 * The <tt>TimerObserver</tt> interface has to be implemented by all classes that wants to be notified by a {@link Timer}.
 */
public interface TimerObserver {
    /**
     * Notifies the timeout of a timer started by this <tt>TimerObserver</tt>.
     */
    void notifyTimeout();
}
