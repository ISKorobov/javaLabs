package bank.exceptions;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Exception class for bank
 * @author ISKor
 * @version 1.0
 */
public class BankException extends BanksException {
    private BankException(String message) {
        super(message);
    }

    public static BankException noObserver(UUID id)
    {
        return new BankException("Client with id " + id + " not in list subscribers");
    }

    public static BankException alreadySubscribed(UUID id)
    {
        return new BankException("Client with id " + id + " is already subscribed");
    }

    public static BankException clientAlreadyRegistered(UUID clientId, UUID bankId) {
        return new BankException("Client with id " + clientId + " is already registered in bank with id " + bankId);
    }

    public static BankException accountAlreadyRegistered(UUID accountId, UUID bankId)
    {
        return new BankException("Account with id " + accountId + " is already registered in bank with id " + bankId);
    }

    public static BankException noDepositPercent(BigDecimal money, UUID bankId)
    {
        return new BankException("Bank with id " + bankId + " doesn't have percent to this money " + money);
    }

    public static BankException noClient(String name, String surname)
    {
        return new BankException("No client: " + name + " " + surname);
    }

    public static BankException noSuchAccountException()
    {
        return new BankException("No this account");
    }
}
