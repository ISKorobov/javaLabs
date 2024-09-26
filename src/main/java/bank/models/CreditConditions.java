package bank.models;

import bank.exceptions.BankConditionsException;
import bank.exceptions.BanksException;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Checks and stores credit conditions
 * @author ISKor
 * @version 1.0
 */
@Getter
@Setter
public class CreditConditions {
    /**
     * Checks if BigDecimals are suitable to be the credit conditions
     * @param limit Credit limit to check and store
     * @param fee Credit fee to check and store
     */
    public CreditConditions(BigDecimal limit, BigDecimal fee)
    {
        checkLimit(limit);
        checkFee(fee);
        this.limit = limit;
        this.fee = fee;
    }

    /**
     * Stores credit limit
     */
    public BigDecimal limit;
    /**
     * Stores credit fee
     */
    public BigDecimal fee;

    /**
     * Changes credit limit to a new value
     * @param newLimit New credit limit
     */
    public void changeLimit(BigDecimal newLimit) {
        checkLimit(newLimit);
        limit = newLimit;
    }

    /**
     * Changes credit fee to a new value
     * @param newFee New credit fee
     */
    public void changeFee(BigDecimal newFee)
    {
        checkFee(newFee);
        fee = newFee;
    }

    /**
     * Checks if BigDecimal is suitable to be a credit limit
     * @param creditLimit BigDecimal to be checked
     * @throws BanksException Throws exception if the given BigDecimal is incorrect
     */
    private void checkLimit(BigDecimal creditLimit) throws BanksException {
        if (creditLimit.compareTo(BigDecimal.valueOf(0)) < 0) {
            throw BankConditionsException.creditLimitIsNegativeException();
        }
    }

    /**
     * Checks if BigDecimal is suitable to be a credit fee
     * @param fee BigDecimal to be checked
     * @throws BanksException Throws exception if the given BigDecimal is incorrect
     */
    private void checkFee(BigDecimal fee) throws BanksException {
        if (fee.compareTo(BigDecimal.valueOf(0)) < 0) {
            throw BankConditionsException.creditFeeIsNegativeException();
        }
    }
}