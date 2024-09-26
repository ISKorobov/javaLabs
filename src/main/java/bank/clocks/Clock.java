package bank.clocks;

import bank.observers.TimeObserver;

import java.util.Date;

public interface Clock {
    /**
     * Add days
     * @param count How many days to add
     */
    void addDays(int count);
    /**
     * Add observer
     * @param timeObserver Observer to add
     */
    void addObserver(TimeObserver timeObserver);
    /**
     * Notifies observers
     * @param time Updated t ime
     */
    void notify(Date time);

    /**
     * Returns current time
     */
    Date getTime();
}
