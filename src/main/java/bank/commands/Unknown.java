package bank.commands;

/**
 * Command unknown
 * @author ISKor
 * @version 1.0
 */
public class Unknown implements Command {
    @Override
    public void execute() {
        System.out.println("Unknown command. You can use command -help to see list available command.");
    }
}
