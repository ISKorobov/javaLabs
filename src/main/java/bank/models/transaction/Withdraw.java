package bank.models.transaction;


import bank.entities.accounts.Account;

import java.math.BigDecimal;

/**
 * Transaction of withdrawal
 * @author ISKor
 * @version 1.0
 */
public class Withdraw implements Transaction {
    private final Account account;
    private final BigDecimal money;
    private TransactionStatus status;
    private BigDecimal fee = BigDecimal.valueOf(0);

    /**
     * Creates the transaction
     * @param account Account for withdrawal
     * @param money Money to withdraw
     */
    public Withdraw(Account account, BigDecimal money) {
        status = TransactionStatus.Process;
        this.account = account;
        this.money = money;
    }

    @Override
    public void execute() {
        account.withdrawMoney(money);
        if (account.getBalance().compareTo(BigDecimal.valueOf(0)) < 0) {
            fee = account.fixedFee();
        }
        status = TransactionStatus.Complete;
    }

    @Override
    public boolean cancel() {
        if (status == TransactionStatus.Complete) {
            account.addMoney(money.add(fee));
            status = TransactionStatus.Cancel;
            return true;
        }

        return false;
    }
}