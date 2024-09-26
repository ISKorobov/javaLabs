package bank.commands;

import bank.entities.banks.CentralBank;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Scanner;

public class CreateAccount implements Command {
    private final CentralBank centralBank;
    private final Scanner in;

    public CreateAccount(CentralBank centralBank, Scanner in)
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
        System.out.println("Enter client's name:");
        String name = in.nextLine();
        System.out.println("Enter client's surname:");
        String surname = in.nextLine();
        System.out.println("Enter start balance:");
        BigDecimal money = in.nextBigDecimal();
        in.nextLine();
        System.out.println("Enter account type Credit/Debit/Deposit:");
        String type = in.nextLine();
        switch (type.toLowerCase()) {
            case "credit":
                centralBank.getBank(bankName).createCreditAccount(centralBank.getBank(bankName).getClient(name, surname), money);
                break;
            case "debit":
                centralBank.getBank(bankName).createDebitAccount(centralBank.getBank(bankName).getClient(name, surname), money);
                break;
            case "deposit":
                System.out.println("Enter term in dd mm yyyy format");
                int day = in.nextInt();
                int month = in.nextInt();
                int year = in.nextInt();
                in.nextLine();
                Date dueTo = Date.from(LocalDate.of(year, month, day).atStartOfDay(ZoneId.systemDefault()).toInstant());
                centralBank.getBank(bankName).createDepositAccount(centralBank.getBank(bankName).getClient(name, surname), money, dueTo);
                break;
            default:
                System.out.println("Invalid account type. Please enter Credit, Debit, or Deposit.");
                return;
        }
        System.out.println("Account create successful");
    }
}
