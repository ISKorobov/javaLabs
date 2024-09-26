package bank;

import bank.commands.*;
import bank.entities.banks.CentralBank;
import bank.entities.banks.ConcreteCentralBank;
import java.util.Scanner;

import static java.lang.System.exit;

public class Main {
    public static void main(String[] args) {
        Command command = new Unknown();
        CentralBank centralBank = null;
        Scanner in = new Scanner(System.in);


        while(true) {
            switch (in.nextLine()) {
                case "help":
                    command = new Help();
                    break;
                case "createCentralBank":
                    centralBank = new ConcreteCentralBank();
                    System.out.println("Central bank create successful");
                    break;
                case "showDate":
                    command = new CurrentDate(centralBank);
                    break;
                case "createBank":
                    command = new CreateBank(centralBank, in);
                    break;
                case "createClient":
                    command = new CreateClient(centralBank, in);
                    break;
                case "createAccount":
                    command = new CreateAccount(centralBank, in);
                    break;
                case "addDays":
                    command = new AddDays(centralBank, in);
                    break;
                case "transaction":
                    command = new Transaction(centralBank, in);
                    break;
                case "showBalance":
                    command = new ShowBalance(centralBank, in);
                    break;
                case "addAddress":
                    command = new AddAddress(centralBank, in);
                    break;
                case "addPassport":
                    command = new AddPassport(centralBank, in);
                    break;
                case "stop":
                    command = new Stop();
                    command.execute();
                    exit(0);
                default:
                    command = new Unknown();
                    break;
            }
            command.execute();
        }
    }
}