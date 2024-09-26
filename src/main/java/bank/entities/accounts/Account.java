package bank.entities.accounts;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Methods for account
 * @author ISKor
 * @version 1.0
 */
public interface Account {
    /**
     * Checks if the owner is suspicious
     * @return True - suspicious, False - not suspicious
     */
    boolean suspicious();

    /**
     * Returns fixed fee
     * @return Fixed fee
     */
    BigDecimal fixedFee();

    /**
     * Withdraws money
     * @param summa Summa of money to withdraw
     */
    void withdrawMoney(BigDecimal summa);

    /**
     * Add money
     * @param summa Summa of money to add
     */
    void addMoney(BigDecimal summa);

    /**
     * Gets account's id
     * @return Account id
     */
    UUID getId();

    /**
     * Gets account's current balance
     * @return Balance of the account
     */
    BigDecimal getBalance();

    /**
     * Returns account's type
     * @return String account's type
     */
    String getAccountType();
}
