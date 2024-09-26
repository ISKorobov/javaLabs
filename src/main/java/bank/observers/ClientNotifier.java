package bank.observers;

/**
 * Methods for ClientNotifiers
 * @author ISKor
 * @version 1.0
 */
public interface ClientNotifier {
    /**
     * Notifies clients
     * @param info Information in notification
     */
    void notify(String info);
}
