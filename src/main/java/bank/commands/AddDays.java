package bank.commands;

import bank.entities.banks.CentralBank;

import java.util.Scanner;

/**
 * Command add days
 * @author ISKor
 * @version 1.0
 */
public class AddDays implements Command {
    private final CentralBank centralBank;
    private final Scanner in;

    public AddDays(CentralBank centralBank, Scanner in)
    {
        this.centralBank = centralBank;
        this.in = in;
    }

    @Override
    public void execute() {
        if (centralBank == null)
        {
            System.out.println("You should create central bank first");
            return;
        }

        System.out.println("Enter number days:");
        int days = in.nextInt();
        in.nextLine();
        centralBank.addDays(days);
        System.out.println(days + " add successful");
    }
}