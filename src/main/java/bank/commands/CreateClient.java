package bank.commands;

import bank.entities.banks.CentralBank;
import bank.entities.clients.Client;
import bank.entities.clients.ConcreteClient;
import bank.entities.clients.builders.ClientBuilder;
import bank.entities.clients.builders.ClientNameBuilder;
import bank.entities.clients.builders.ClientSurnameBuilder;

import java.util.Scanner;

/**
 * Command create client
 * @author ISKor
 * @version 1.0
 */
public class CreateClient implements Command {
    private final CentralBank centralBank;
    private final Scanner in;

    public CreateClient(CentralBank centralBank, Scanner in)
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

        ClientNameBuilder clientName = ConcreteClient.getBuilder();
        System.out.println("Enter bank name:");
        String bankName = in.nextLine();
        System.out.println("Enter client name:");
        String name = in.nextLine();
        ClientSurnameBuilder clientSurname = clientName.withName(name);
        System.out.println("Enter client surname:");
        String surname = in.nextLine();
        ClientBuilder client = clientSurname.withSurname(surname);
        System.out.println("Enter client's address (or press enter if you want register without address):");
        String address = in.nextLine();
        if (!address.isEmpty()) {
            client = client.withAddress(address);
        }
        System.out.println("Enter client's passport (or press enter if you want register without passport number):");
        String passport = in.nextLine();
        if (!passport.isEmpty()) {
            client = client.withPassportNumber(Integer.parseInt(passport));
        }
        centralBank.getBank(bankName).addClient(client.build());
        System.out.println("Client " + name + " " + surname + " create successful");
    }
}