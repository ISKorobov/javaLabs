package bank.observers;

import java.util.UUID;

/**
 * Methods for BankObservers
 * @author ISKor
 * @version 1.0
 */
public interface BankObserver {
    /**
     * Get bank notification and works with it
     * @param info Information from the notification
     */
    void update(String info);

    /**
     * Observer's id
     * @return Observer's id
     */
    UUID getId();
}