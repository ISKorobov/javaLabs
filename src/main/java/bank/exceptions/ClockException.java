package bank.exceptions;

/**
 * Exception class for the clocks
 * @author ISKor
 * @version 1.0
 */
public class ClockException extends BanksException {
    private ClockException(String info) {
        super(info);
    }

    public static ClockException clockNegativeDaysException() {
        return new ClockException("Number of days must be positive");
    }
}