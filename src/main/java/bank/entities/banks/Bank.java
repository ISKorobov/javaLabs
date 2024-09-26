package bank.entities.banks;

import bank.clocks.Clock;
import bank.entities.accounts.Account;
import bank.entities.clients.Client;
import bank.models.CreditConditions;
import bank.models.DebitPercent;
import bank.models.DepositPercent;
import bank.models.SuspiciousLimit;

import java.math.BigDecimal;
import java.util.List;
import java.util.Date;
import java.util.UUID;

/**
 * Lists methods for the banks
 * @author ISKor
 * @version 1.0
 */
public interface Bank {
    /**
     * Returns debit percent of this bank
     * @return Returns debit percent
     */
    DebitPercent getDebitPercent();

    /**
     * Returns credit conditions
     * @return Returns credit conditions
     */
    CreditConditions getCreditConditions();

    /**
     * Returns suspicious limit
     * @return Returns suspicious limit
     */
    SuspiciousLimit getSuspiciousLimit();

    /**
     * Returns deposit percent
     * @param money Money to calculate the deposit percent
     * @return Returns deposit percent
     */
    DepositPercent getDepositPercent(BigDecimal money);

    /**
     * Adds client
     * @param client Client to add
     */
    void addClient(Client client);

    /**
     * Creates debit account
     * @param client Client for who want to make account
     * @param money Money of the account
     * @return Returns account
     */
    Account createDebitAccount(Client client, BigDecimal money);

    /**
     * Creates credit account
     * @param client Client for who want to make account
     * @param money Money of the account
     * @return Returns account
     */
    Account createCreditAccount(Client client, BigDecimal money);

    /**
     * Creates deposit account
     * @param client Client for who want to make account
     * @param money Money of the account
     * @param date Deposit to this date
     * @return Returns created account
     */
    Account createDepositAccount(Client client, BigDecimal money, Date date);

    /**
     * Changes debit percent
     * @param newPercent New percent
     */
    void changeDebitPercent(BigDecimal newPercent);

    /**
     * Changes deposit percent
     * @param newPercent New percent
     */
    void changeDepositPercent(List<DepositPercent> newPercent);

    /**
     * Changes credit limit
     * @param newLimit New limit
     */
    void changeCreditLimit(BigDecimal newLimit);

    /**
     * Changes credit fee
     * @param newFee New credit fee
     */
    void changeCreditFee(BigDecimal newFee);

    /**
     * Changes suspicious limit
     * @param newLimit New suspicious limit
     */
    void changeSuspiciousLimit(BigDecimal newLimit);

    /**
     * Changes clock
     * @param newClock New clock
     */
    void changeClock(Clock newClock);

    /**
     * Get client by name and surname
     * @param name Name
     * @param surname Surname
     * @return Returns client
     */
    Client getClient(String name, String surname);

    /**
     * Get account by id
     * @param id id of the account
     * @return Returns account
     */
    Account getAccount(String id);

    /**
     * Returns bank`s id
     * @return Returns id
     */
    UUID getId();

    /**
     * Returns bank`s name
     * @return Returns name
     */
    String getName();

    /**
     * Returns list accounts
     * @return Returns list accounts
     */
    List<Account> getAccounts();
}

