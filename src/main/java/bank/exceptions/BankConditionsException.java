package bank.exceptions;

/**
 * Exception class for the bank conditions
 * @author ISKor
 * @version 1.0
 */
public class BankConditionsException extends BanksException{
    private BankConditionsException(String message) {
        super(message);
    }

    public static BankConditionsException depositBoundsException()
    {
        return new BankConditionsException("Incorrect bounds in deposit percent: lower bound mustn't be bigger than higher bound");
    }

    public static BankConditionsException depositNegativeBoundException()
    {
        return new BankConditionsException("Incorrect bounds in deposit: neither of bounds can be negative");
    }

    public static BankConditionsException accountIncorrectPercentException(String type)
    {
        return new BankConditionsException(type + " percent must be >0 and <100");
    }


    public static BankConditionsException creditLimitIsNegativeException()
    {
        return new BankConditionsException("Credit limit value must be positive");
    }

    public static BankConditionsException creditFeeIsNegativeException()
    {
        return new BankConditionsException("Credit fee value must be positive");
    }

    public static BankConditionsException suspiciousClientLimitIsNegativeException()
    {
        return new BankConditionsException("Limit for suspicious client must not be negative");
    }
}
