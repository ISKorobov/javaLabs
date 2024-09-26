package bank.models.transaction;


import bank.entities.accounts.Account;

import java.math.BigDecimal;

/**
 * Transaction add money
 * @author ISKor
 * @version 1.0
 */
public class AddMoney implements Transaction {
    private final Account account;
    private final BigDecimal money;
    private BigDecimal fee = BigDecimal.valueOf(0);

    private TransactionStatus status;

    /**
     * Creates transactions
     * @param account Account for add money
     * @param money Money to add
     */
    public AddMoney(Account account, BigDecimal money) {
        status = TransactionStatus.Process;
        this.account = account;
        this.money = money;
    }

    @Override
    public void execute() {
        if (account.getBalance().compareTo(BigDecimal.valueOf(0)) < 0) {
            fee = account.fixedFee();
        }
        account.addMoney(money);
        status = TransactionStatus.Complete;
    }

    @Override
    public boolean cancel() {
        if (status == TransactionStatus.Complete) {
            account.withdrawMoney(money);
            account.addMoney(fee);
            status = TransactionStatus.Cancel;
            return true;
        }

        return false;
    }
}

