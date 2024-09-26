package bank.observers;

/**
 * Methods for BankNotifier
 * @author ISKor
 * @version 1.0
 */
public interface BankNotifier {
    /**
     * Add observer
     * @param bankObserver Observer to add
     */
    void addObserver(BankObserver bankObserver);

    /**
     * Delete observer
     * @param bankObserver Observers to delete
     */
    void deleteObserver(BankObserver bankObserver);

    /**
     * Notifies observers
     * @param info Information in notification
     */
    void notify(String info);
}