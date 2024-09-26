package bank.observers;

import java.util.Date;

public interface TimeObserver {
    /**
     * Gets clock notification about time update
     * @param time Updated time
     */
    void update(Date time);
}
