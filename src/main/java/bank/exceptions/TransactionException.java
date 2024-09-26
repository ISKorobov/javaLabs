package bank.exceptions;

import java.util.UUID;

/**
 * Exception class transaction
 * @author ISKor
 * @version 1.0
 *
 */
public class TransactionException extends BanksException{
    private TransactionException(String message) {
        super(message);
    }

    public static TransactionException negativeMoneyException(UUID id)
    {
        return new TransactionException("Transaction with id " + id + ". Money is negative");
    }

    public static TransactionException uncompletedTransactionCancellation(UUID id)
    {
        return new TransactionException("Transaction with id " + id + ". Transaction status must be complete to be cancel");
    }

    public static TransactionException noTransactionsWithId(UUID id)
    {
        return new TransactionException("Transaction with id " + id + " not contains");
    }

    public static TransactionException transactionIsNullException()
    {
        return new TransactionException("Transaction can not be null");
    }
}