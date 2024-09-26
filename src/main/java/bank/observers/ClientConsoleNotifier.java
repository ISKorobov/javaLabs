package bank.observers;

/**
 * Notifies clients in console
 * @author ISKor
 * @version 1.0
 */
public class ClientConsoleNotifier implements ClientNotifier{
    @Override
    public void notify(String info) {
        System.out.println("Notification: " + info);
    }
}
