package bank.exceptions;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Exception class account
 * @author ISKor
 * @version 1.0
 */
public class AccountException extends BanksException {
    private AccountException(String info) {
        super(info);
    }

    public static AccountException negativeMoneyException()
    {
        return new AccountException("Money should be positive");
    }

    public static AccountException notEnoughMoneyException(BigDecimal balance, BigDecimal amount)
    {
        return new AccountException("Not enough money. Your balance: " + balance + ". Need: " + amount);
    }

    public static AccountException suspiciousClientException(BigDecimal amount, UUID id)
    {
        return new AccountException("You are suspicious client, so you cannot withdraw " + amount + " in the bank with id " + id);
    }


    public static AccountException depositAccountWithdrawException()
    {
        return new AccountException("You cannot withdraw money from the deposit account before end of the term");
    }

    public static AccountException creditAccountNotEnoughMoneyException(BigDecimal sum)
    {
        return new AccountException("You cannot withdraw " + sum + " money. Check your credit limit");
    }
}
