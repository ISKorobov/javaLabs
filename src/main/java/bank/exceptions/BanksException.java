package bank.exceptions;

/**
 * Main exception class for project
 * @author ISKor
 * @version 1.0
 */
public class BanksException extends RuntimeException {
    public BanksException(String info) {
        super(info);
    }
}
