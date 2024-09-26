package bank.exceptions;

/**
 * Exception class central bank
 * @author ISKor
 * @version 1.0
 */
public class CentralBankException extends BanksException {
    private CentralBankException(String message) {
        super(message);
    }

    public static CentralBankException noBankException(String name)
    {
        return new CentralBankException("No bank with name " + name);
    }

    public static CentralBankException alreadyExists(String name)
    {
        return new CentralBankException("Bank with name " + name + " already exist");
    }
}
