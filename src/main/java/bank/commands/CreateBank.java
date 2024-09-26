package bank.commands;

import bank.entities.banks.CentralBank;
import bank.models.CreditConditions;
import bank.models.DebitPercent;
import bank.models.DepositPercent;
import bank.models.SuspiciousLimit;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Command create bank
 * @author ISKor
 * @version 1.0
 */
public class CreateBank implements Command {
    private final CentralBank centralBank;
    private final Scanner in;

    public CreateBank(CentralBank centralBank, Scanner in)
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
        String name = in.nextLine();
        System.out.println("Enter debit percent:");
        DebitPercent debitPercent = new DebitPercent(in.nextBigDecimal());
        in.nextLine();
        System.out.println("Enter number deposit interests:");
        int numberDeposit = in.nextInt();
        in.nextLine();
        List<DepositPercent> depositPercents = new ArrayList<>();
        for (int i = 0; i < numberDeposit; i++) {
            System.out.println("Deposit percent №" + (i + 1));
            System.out.println("Enter lower bound:");
            BigDecimal lower = in.nextBigDecimal();
            in.nextLine();
            System.out.println("Enter upper bound or enter if not upper bound:");
            String upperBound = in.nextLine();
            BigDecimal upper;
            if (upperBound.isEmpty()) {
                upper = null;
            } else {
                upper = new BigDecimal(upperBound);
            }

            System.out.println("Enter percent for this bounds:");
            BigDecimal interest = in.nextBigDecimal();
            in.nextLine();
            depositPercents.add(new DepositPercent(lower, upper, interest));
        }

        System.out.println("Enter credit limit:");
        BigDecimal creditLimit = in.nextBigDecimal();
        in.nextLine();
        System.out.println("Enter credit fee:");
        BigDecimal creditFee = in.nextBigDecimal();
        in.nextLine();
        CreditConditions creditConditions = new CreditConditions(creditLimit, creditFee);
        System.out.println("Enter suspicious limit");
        SuspiciousLimit suspiciousClientLimit = new SuspiciousLimit(in.nextBigDecimal());
        in.nextLine();

        centralBank.addBank(name, debitPercent, depositPercents, creditConditions, suspiciousClientLimit);
        System.out.println("Bank " + name + " create successful");
    }
}
