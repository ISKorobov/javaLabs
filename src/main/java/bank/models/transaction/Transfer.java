package bank.models.transaction;

import bank.entities.accounts.Account;

import java.math.BigDecimal;

/**
 * Transaction of transferring
 * @author ISKor
 * @version 1.0
 */
public class Transfer implements Transaction {
    private final Account sender;
    private final Account receiver;
    private final BigDecimal money;
    private TransactionStatus status;
    private BigDecimal feeSender = BigDecimal.valueOf(0);
    private BigDecimal feeReceiver = BigDecimal.valueOf(0);

    /**
     * Creates transaction
     * @param accountSender Account to send the transfer
     * @param accountReceiver Account to receive the transfer
     * @param money Money to be transferred
     */
    public Transfer(Account accountSender, Account accountReceiver, BigDecimal money) {
        status = TransactionStatus.Process;
        this.sender = accountSender;
        this.receiver = accountReceiver;
        this.money = money;
    }

    @Override
    public void execute() {
        sender.withdrawMoney(money);
        if (receiver.getBalance().compareTo(BigDecimal.valueOf(0)) < 0){
            feeReceiver = receiver.fixedFee();
        }
        receiver.addMoney(money);
        if (sender.getBalance().compareTo(BigDecimal.valueOf(0)) < 0) {
            feeSender = sender.fixedFee();
        }
        status = TransactionStatus.Complete;
    }

    @Override
    public boolean cancel() {
        if (status == TransactionStatus.Complete) {
            receiver.withdrawMoney(money);
            receiver.addMoney(feeReceiver);
            sender.addMoney(money.add(feeSender));
            status = TransactionStatus.Cancel;
            return true;
        }

        return false;
    }
}