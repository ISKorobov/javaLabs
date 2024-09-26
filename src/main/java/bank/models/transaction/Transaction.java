package bank.models.transaction;

/**
 * Methods for transactions
 * @author ISKor
 * @version 1.0
 */
public interface Transaction {
    /**
     * Execute transaction
     */
    void execute();

    /**
     * Cancel transactions
     * @return true - transaction cancel, false - transaction not cancel
     */
    boolean cancel();
}