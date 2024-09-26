package bank.models;

import bank.exceptions.BankConditionsException;
import bank.exceptions.BanksException;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


/**
 * Checks and stores limits for suspicious clients
 * @author ISKor
 * @version 1.0
 */
@Getter
@Setter
public class SuspiciousLimit {
    /**
     * Checks if BigDecimal is suitable to be a limit for suspicious clients
     * @param limit Limit to check and store
     */
    public SuspiciousLimit(BigDecimal limit) {
        checkLimit(limit);
        this.limit = limit;
    }

    /**
     * Stores the limit
     */
    public BigDecimal limit;

    /**
     * Changes limit to a new value
     * @param newLimit New limit
     */
    public void changeLimit(BigDecimal newLimit) {
        checkLimit(newLimit);
        limit = newLimit;
    }

    /**
     * Checks if BigDecimal is suitable to be a limit
     * @param limit BigDecimal to be checked
     * @throws BanksException Throws exception if BigDecimal is negative
     */
    private void checkLimit(BigDecimal limit) throws BanksException {
        if (limit.compareTo(BigDecimal.valueOf(0)) < 0) {
            throw BankConditionsException.suspiciousClientLimitIsNegativeException();
        }
    }
}