package bank.commands;

import bank.entities.banks.CentralBank;

/**
 * Command date
 * @author ISKor
 * @version 1.0
 */
public class CurrentDate implements Command{
    private final CentralBank centralBank;

    public CurrentDate(CentralBank centralBank) {
        this.centralBank = centralBank;
    }

    @Override
    public void execute() {
        if (centralBank == null)
        {
            System.out.println("You should create central bank first");
            return;
        }

        System.out.println("Current date is: " + centralBank.getClock().getTime());
    }
}
