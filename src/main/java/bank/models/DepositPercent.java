package bank.models;

import bank.exceptions.BankConditionsException;
import bank.exceptions.BanksException;
import lombok.Getter;

import java.math.BigDecimal;

/**
 * Checks and stores deposit percent
 * @author ISKor
 * @version 1.0
 */
@Getter
public class DepositPercent {
    /**
     * Checks values and stores them if they are valid
     * @param lowerBound Value for lower bound
     * @param upperBound Value for upper bound
     * @param percent Value for percent
     */
    public DepositPercent(BigDecimal lowerBound, BigDecimal upperBound, BigDecimal percent) {
        checkLowerBound(lowerBound, upperBound);
        checkUpperBound(upperBound, lowerBound);
        checkPercent(percent);
        this.upperBound = upperBound;
        this.lowerBound = lowerBound;
        this.percent = percent;
    }

    /**
     * Lower bound of a range in which balance should fall to get this percent
     */
    public BigDecimal upperBound;
    /**
     * Upper bound of a range in which balance should fall to get this percent (can be null)
     */
    public BigDecimal lowerBound;
    /**
     * Percent for this balance range
     */
    public BigDecimal percent;

    /**
     * Checks if balance falls in this particular range
     * @param balance Opening balance to check
     * @return true - yes, false - no
     */
    public boolean isInRange(BigDecimal balance) {
        if (upperBound == null) {
            return balance.compareTo(lowerBound) >= 0;
        }

        return balance.compareTo(lowerBound) >= 0 && balance.compareTo(upperBound) < 0;
    }

    /**
     * Checks if the upper bound is valid and correct
     * @param upperBound Upper bound to check
     * @param lowerBound Lower bound to use in checks
     * @throws BanksException Throws exceptions if upper bound is incorrect
     */
    private void checkUpperBound(BigDecimal upperBound, BigDecimal lowerBound) throws BanksException {
        if (upperBound != null && upperBound.compareTo(lowerBound) < 0) {
            throw BankConditionsException.depositBoundsException();
        }

        if (upperBound != null && upperBound.compareTo(BigDecimal.valueOf(0)) < 0) {
            throw BankConditionsException.depositNegativeBoundException();
        }
    }

    /**
     * Checks if the lower bound is valid and correct
     * @param lowerBound Lower bound to check
     * @param upperBound Upper bound to use in checks
     * @throws BanksException Throws exceptions if lower bound is incorrect
     */
    private void checkLowerBound(BigDecimal lowerBound, BigDecimal upperBound) throws BanksException {
        if (upperBound != null && upperBound.compareTo(lowerBound) < 0) {
            throw BankConditionsException.depositBoundsException();
        }

        if (lowerBound.compareTo(BigDecimal.valueOf(0)) < 0) {
            throw BankConditionsException.depositNegativeBoundException();
        }
    }

    /**
     * Checks if the percent is valid and correct
     * @param percent Percent to check
     * @throws BanksException Throws exceptions if percent is incorrect
     */
    private void checkPercent(BigDecimal percent) throws BanksException {
        if (percent.compareTo(BigDecimal.valueOf(0)) < 0 || percent.compareTo(BigDecimal.valueOf(100)) > 0) {
            throw BankConditionsException.accountIncorrectPercentException("Deposit");
        }
    }
}
