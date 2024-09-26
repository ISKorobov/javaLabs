package bank.commands;

import bank.entities.accounts.Account;
import bank.entities.banks.CentralBank;

import java.math.BigDecimal;
import java.util.Scanner;

/**
 * Command transaction
 * @author ISKor
 * @version 1.0
 */
public class Transaction implements Command {
    private final CentralBank centralBank;
    private final Scanner in;

    public Transaction(CentralBank centralBank, Scanner in)
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
        System.out.println("Enter account id:");
        for (Account account : centralBank.getBank(bankName).getClient(name, surname).getAccounts()) {
            System.out.println(account.getId() + " " + account.getAccountType());
        }
        String id = in.nextLine();
        Account account = centralBank.getBank(bankName).getAccount(id);
        System.out.println("Enter transaction type AddMoney/Transfer/Withdraw:");
        String type = in.nextLine();
        System.out.println("Enter money:");
        BigDecimal money = in.nextBigDecimal();
        in.nextLine();
        switch (type.toLowerCase()) {
            case "addmoney":
                account.addMoney(money);
                break;
            case "transfer":
                System.out.println("Enter information about who you want to send money to");
                System.out.println("Enter bank name:");
                String receiverBankName = in.nextLine();
                System.out.println("Enter client name:");
                String receiverName = in.nextLine();
                System.out.println("Enter client surname:");
                String receiverSurname = in.nextLine();
                System.out.println("Enter account id:");
                for (Account accountTmp : centralBank.getBank(bankName).getClient(name, surname).getAccounts()) {
                    System.out.println(accountTmp.getId() + " " + accountTmp.getAccountType());
                }
                String receiverId = in.nextLine();
                Account receiver = centralBank.getBank(bankName).getAccount(id);
                centralBank.transfer(account, receiver, money);
                break;
            case "withdraw":
                account.withdrawMoney(money);
                break;
            default:
                System.out.println("Invalid transaction type. Please enter AddMoney, Transfer, or Withdraw.");
                return;
        }
        System.out.println("Transaction complete successful");
    }
}
