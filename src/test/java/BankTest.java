import bank.entities.accounts.Account;
import bank.entities.banks.Bank;
import bank.entities.banks.CentralBank;
import bank.entities.banks.ConcreteCentralBank;
import bank.entities.clients.Client;
import bank.entities.clients.ConcreteClient;
import bank.exceptions.AccountException;
import bank.models.CreditConditions;
import bank.models.DebitPercent;
import bank.models.DepositPercent;
import bank.models.SuspiciousLimit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

public class BankTest {
    private final CentralBank centralBank = new ConcreteCentralBank();
    private final Bank bank = centralBank.addBank(
            "TestBank",
            new DebitPercent(BigDecimal.valueOf(10)),
            Arrays.asList(new DepositPercent(BigDecimal.valueOf(0), BigDecimal.valueOf(50000), BigDecimal.valueOf(10)),
                    new DepositPercent(BigDecimal.valueOf(50000), null, BigDecimal.valueOf(15))),
            new CreditConditions(BigDecimal.valueOf(200000), BigDecimal.valueOf(100)),
            new SuspiciousLimit(BigDecimal.valueOf(10000)));
    private final Client client = ConcreteClient.getBuilder().withName("Ivan").withSurname("Korobov").withAddress("MyAddress").withPassportNumber(800800).build();

    public BankTest() {
        bank.addClient(client);
    }

    @Test
    void testBadSuspiciousLimit_GetException() {
        Client suspiciousClient = ConcreteClient.getBuilder().withName("Ivan").withSurname("Korobov").build();
        Account account = bank.createDebitAccount(suspiciousClient, BigDecimal.valueOf(100000));
        Assertions.assertThrows(AccountException.class, () -> centralBank.withdraw(account, BigDecimal.valueOf(100000)));
    }

    @Test
    void testBadCreditLimit_GetException() {
        Account account = bank.createCreditAccount(client, BigDecimal.valueOf(100100));
        Assertions.assertThrows(AccountException.class, () -> centralBank.withdraw(account, BigDecimal.valueOf(300001)));
    }

    @Test
    void testDepositTermNotEndWithdraw_GetException() {
        Account account = bank.createDepositAccount(client, BigDecimal.valueOf(100000), Date.from(LocalDate.of(2024, 12, 31).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        Assertions.assertThrows(AccountException.class, () -> centralBank.withdraw(account, BigDecimal.valueOf(1)));
    }

    @Test
    void testGoToTheFuture_GetNewBalance() {
        Account account1 = bank.createDebitAccount(client, BigDecimal.valueOf(100000));
        Account account2 = bank.createDepositAccount(client, BigDecimal.valueOf(100000), Date.from(LocalDate.of(2024, 12, 31).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        centralBank.addDays(30);

        BigDecimal balanceDebit = BigDecimal.valueOf(100000);
        BigDecimal balanceDeposit = BigDecimal.valueOf(100000);
        BigDecimal percentDebit = BigDecimal.valueOf(0);
        BigDecimal percentDeposit = BigDecimal.valueOf(0);
        BigDecimal percentDebitPerDay = BigDecimal.valueOf(10).divide(BigDecimal.valueOf(36500), 10, RoundingMode.CEILING);
        BigDecimal percentDepositPerDay = BigDecimal.valueOf(15).divide(BigDecimal.valueOf(36500), 10, RoundingMode.CEILING);

        for (int i = 0; i < 30; i++) {
            percentDebit = percentDebit.add(balanceDebit.multiply(percentDebitPerDay));
            percentDeposit = percentDeposit.add(balanceDeposit.multiply(percentDepositPerDay));
        }

        balanceDebit = balanceDebit.add(percentDebit);
        balanceDeposit = balanceDeposit.add(percentDeposit);

        Assertions.assertEquals(account1.getBalance(), balanceDebit);
        Assertions.assertEquals(account2.getBalance(), balanceDeposit);
    }

    @Test
    void testCancelTransaction_TransactionCancelled() {
        Account account1 = bank.createDebitAccount(client, BigDecimal.valueOf(100000));
        Account account2 = bank.createCreditAccount(client, BigDecimal.valueOf(1));

        UUID transactionId = centralBank.transfer(account2, account1, BigDecimal.valueOf(1000));
        BigDecimal expectedCreditBalance = BigDecimal.valueOf(1 - 1000 - 100);
        Assertions.assertEquals(expectedCreditBalance, account2.getBalance());

        centralBank.cancelTransaction(transactionId);
        expectedCreditBalance = expectedCreditBalance.add(BigDecimal.valueOf(1000 + 100));
        Assertions.assertEquals(expectedCreditBalance, account2.getBalance());
    }

    @Test
    void testChangePercentAndGoToTheFuture_GetNewBalance() {
        Account account = bank.createDebitAccount(client, BigDecimal.valueOf(100000));

        centralBank.addDays(15);
        bank.changeDebitPercent(BigDecimal.valueOf(15));
        centralBank.addDays(15);

        BigDecimal balanceDebit = BigDecimal.valueOf(100000);
        BigDecimal percentDebit = BigDecimal.valueOf(0);
        BigDecimal percentDebitPerDay = BigDecimal.valueOf(10).divide(BigDecimal.valueOf(36500), 10, RoundingMode.CEILING);

        for (int i = 0; i < 15; i++) {
            percentDebit = percentDebit.add(balanceDebit.multiply(percentDebitPerDay));
        }

        percentDebitPerDay = BigDecimal.valueOf(15).divide(BigDecimal.valueOf(36500), 10, RoundingMode.CEILING);

        for (int i = 0; i < 15; i++) {
            percentDebit = percentDebit.add(balanceDebit.multiply(percentDebitPerDay));
        }

        balanceDebit = balanceDebit.add(percentDebit);
        Assertions.assertEquals(account.getBalance(), balanceDebit);
    }
}