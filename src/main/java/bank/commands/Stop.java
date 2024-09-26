package bank.commands;

/**
 * Command stop
 * @author ISKor
 * @version 1.0
 */
public class Stop implements Command{
    @Override
    public void execute() {
        System.out.println("Goodbye!");
    }
}
