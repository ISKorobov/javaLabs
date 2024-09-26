package bank.models;

import bank.exceptions.BankConditionsException;
import bank.exceptions.BanksException;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Checks and stores debit percent
 * @author ISKor
 * @version 1.0
 */
@Getter
@Setter
public class DebitPercent {
    /**
     * Checks if BigDecimal is suitable to be a debit percent
     * @param percent Percent to store
     */
    public DebitPercent(BigDecimal percent) {
        check(percent);
        this.percent = percent;
    }

    /**
     * Stores percent
     */
    public BigDecimal percent;

    /**
     * Changes percent to a new value
     * @param newPercent New percent
     */
    public void changePercent(BigDecimal newPercent)
    {
        check(newPercent);
        percent = newPercent;
    }

    /**
     * Checks if BigDecimal is suitable to be a percent
     * @param percent BigDecimal to check
     * @throws BanksException Throws exception if BigDecimal is incorrect
     */
    private void check(BigDecimal percent) throws BanksException {
        if (percent.compareTo(BigDecimal.valueOf(0)) < 0 || percent.compareTo(BigDecimal.valueOf(100)) > 0) {
            throw BankConditionsException.accountIncorrectPercentException("Debit");
        }
    }
}
