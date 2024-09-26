package bank.entities.banks;

import bank.clocks.Clock;
import bank.models.CreditConditions;
import bank.models.DebitPercent;
import bank.models.DepositPercent;
import bank.models.SuspiciousLimit;
import bank.entities.accounts.Account;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * Methods central bank
 * @author ISKor
 * @version 1.0
 */
public interface CentralBank {
    /**
     * Add bank
     * @param name Name bank
     * @param debitPercent Debit percent
     * @param depositPercents Deposit percent
     * @param creditConditions Credit conditions
     * @param suspiciousLimit Suspicious limit
     * @return Created bank
     */
    Bank addBank(String name, DebitPercent debitPercent, List<DepositPercent> depositPercents, CreditConditions creditConditions, SuspiciousLimit suspiciousLimit);

    /**
     * Add money
     * @param account Account to add money
     * @param money Money to add
     * @return Id transaction
     */
    UUID addMoneyToAccount(Account account, BigDecimal money);

    /**
     * Withdraw money
     * @param account Account to withdraw
     * @param money Money to withdraw
     * @return Id transaction
     */
    UUID withdraw(Account account, BigDecimal money);

    /**
     * Transfer money
     * @param accountSender Account send money
     * @param accountReceiver Account receive money
     * @param money Money to transfer
     * @return Id transaction
     */
    UUID transfer(Account accountSender, Account accountReceiver, BigDecimal money);

    /**
     * Cancel transaction
     * @param transactionId Id transaction to cancel
     */
    void cancelTransaction(UUID transactionId);

    /**
     * Change clock
     * @param newClock New clock
     */
    void changeClock(Clock newClock);

    /**
     * Add days
     * @param days Days to add
     */
    void addDays(int days);

    /**
     * Return bank by name
     * @param name Name bank
     * @return Bank by name
     */
    Bank getBank(String name);

    /**
     * Clock
     * @return Central bank clock
     */
    Clock getClock();
}