package bank.commands;

import bank.entities.banks.CentralBank;

import java.util.Scanner;

/**
 * Command add passport
 * @author ISKor
 * @version 1.0
 */
public class AddPassport implements Command {
    private final CentralBank centralBank;
    private final Scanner in;

    public AddPassport(CentralBank centralBank, Scanner in)
    {
        this.centralBank = centralBank;
        this.in = in;
    }

    @Override
    public void execute() {
        if (centralBank == null) {
            System.out.println("You should create central bank first");
            return;
        }

        System.out.println("Enter bank name:");
        String bankName = in.nextLine();
        System.out.println("Enter client name:");
        String name = in.nextLine();
        System.out.println("Enter client surname:");
        String surname = in.nextLine();
        System.out.println("Enter client passport number:");
        int passport = in.nextInt();
        in.nextLine();
        centralBank.getBank(bankName).getClient(name, surname).addPassport(passport);
        System.out.println("Passport add successful");
    }
}