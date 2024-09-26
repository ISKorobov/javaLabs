package bank.models.transaction;

import bank.exceptions.BanksException;
import bank.exceptions.TransactionException;

import java.util.HashMap;
import java.util.UUID;

/**
 * Works and execute transaction
 * @author ISKor
 * @version 1.0
 */
public class TransactionExecutor {
    private final HashMap<UUID, Transaction> transactionHistory = new HashMap<>();

    /**
     * Execute transaction and add transaction to history
     * @throws BanksException Throws exception if transaction is incorrect
     */
    public UUID executeTransaction(Transaction transaction) throws BanksException {
        if (transaction == null) {
            throw TransactionException.transactionIsNullException();
        }
        UUID transactionId = UUID.randomUUID();
        this.transactionHistory.put(transactionId, transaction);
        transaction.execute();
        return  transactionId;
    }

    /**
     * Cancel transaction by id
     * @param id id transaction
     * @throws BanksException Throws exception if no transaction in history or transaction not cancel
     */
    public void cancelTransaction(UUID id) throws BanksException {
        if (!transactionHistory.containsKey(id)) {
            throw TransactionException.noTransactionsWithId(id);
        }
        if (!transactionHistory.get(id).cancel()) {
            throw TransactionException.uncompletedTransactionCancellation(id);
        }
    }
}
