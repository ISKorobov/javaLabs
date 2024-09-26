package bank.entities.banks;

import bank.clocks.Clock;
import bank.entities.accounts.Account;
import bank.entities.accounts.CreditAccount;
import bank.entities.accounts.DebitAccount;
import bank.entities.accounts.DepositAccount;
import bank.entities.clients.Client;
import bank.exceptions.BankException;
import bank.exceptions.BanksException;
import bank.models.CreditConditions;
import bank.models.DebitPercent;
import bank.models.DepositPercent;
import bank.models.SuspiciousLimit;
import bank.observers.BankNotifier;
import bank.observers.BankObserver;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.*;

/**
 * Class for the concrete bank
 * @author ISKor
 * @version 1.0
 */
@Getter
@Setter
public class ConcreteBank implements Bank, BankNotifier {
    private final List<Client> clients;
    private final List<Account> accounts;
    private final List<BankObserver> observers;
    private Clock clock;
    private DebitPercent concreteDebitPercent;
    private CreditConditions creditConditions;
    private SuspiciousLimit suspiciousLimit;
    private List<DepositPercent> depositPercents;

    /**
     * Creates concrete bank
     * @param name Name bank
     * @param debitPercent Debit percent
     * @param depositPercents Deposit percent
     * @param creditConditions Credit conditions
     * @param suspiciousLimit Suspicious limit
     * @param clock Clock
     */
    public ConcreteBank(String name, DebitPercent debitPercent, List<DepositPercent> depositPercents, CreditConditions creditConditions, SuspiciousLimit suspiciousLimit, Clock clock) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(clock);
        Objects.requireNonNull(debitPercent);
        Objects.requireNonNull(depositPercents);
        Objects.requireNonNull(creditConditions);
        Objects.requireNonNull(suspiciousLimit);
        clients = new ArrayList<>();
        accounts = new ArrayList<>();
        observers = new ArrayList<>();
        this.name = name;
        id = UUID.randomUUID();
        this.clock = clock;
        this.concreteDebitPercent = debitPercent;
        this.depositPercents = depositPercents;
        this.creditConditions = creditConditions;
        this.suspiciousLimit = suspiciousLimit;
    }

    /**
     * Name bank
     */
    public String name;
    /**
     * Id bank
     */
    public UUID id;

    @Override
    public DebitPercent getDebitPercent() {
        return concreteDebitPercent;
    }
    @Override
    public CreditConditions getCreditConditions() {
        return creditConditions;
    }

    @Override
    public SuspiciousLimit getSuspiciousLimit() {
        return suspiciousLimit;
    }

    @Override
    public DepositPercent getDepositPercent(BigDecimal money) throws BanksException {
        for (DepositPercent depositPercent : depositPercents) {
            if (depositPercent.isInRange(money)) {
                return depositPercent;
            }
        }

        throw BankException.noDepositPercent(money, this.id);
    }

    @Override
    public void addClient(Client client) throws BanksException {
        if (clients.contains(client)) {
            throw BankException.clientAlreadyRegistered(client.getId(), this.id);
        }

        clients.add(client);
    }

    @Override
    public Account createDebitAccount(Client client, BigDecimal money) {
        Account newDebitAccount = new DebitAccount(client, this, money, clock);
        client.addAccount(newDebitAccount);
        accounts.add(newDebitAccount);
        return newDebitAccount;
    }

    @Override
    public Account createCreditAccount(Client client, BigDecimal money) {
        Account newCreditAccount = new CreditAccount(client, this, money, clock);
        client.addAccount(newCreditAccount);
        accounts.add(newCreditAccount);
        return newCreditAccount;
    }

    @Override
    public Account createDepositAccount(Client client, BigDecimal money, Date dueTo) {
        Account newDepositAccount = new DepositAccount(client, this, money, clock, dueTo);
        client.addAccount(newDepositAccount);
        accounts.add(newDepositAccount);
        return newDepositAccount;
    }

    @Override
    public void changeDebitPercent(BigDecimal newPercent) {
        concreteDebitPercent.changePercent(newPercent);
        notify("Debit percent change. New percent " + newPercent);
    }

    @Override
    public void changeDepositPercent(List<DepositPercent> newPercent) {
        depositPercents = newPercent;
        notify("Deposit percent change");
    }

    @Override
    public void changeCreditLimit(BigDecimal newCreditLimit) {
        creditConditions.changeLimit(newCreditLimit);
        notify("Credit limit change. New limit: " + newCreditLimit);
    }

    @Override
    public void changeCreditFee(BigDecimal newCreditFee) {
        creditConditions.changeFee(newCreditFee);
        notify("Credit fee change. New fee: " + newCreditFee);
    }

    @Override
    public void changeSuspiciousLimit(BigDecimal newLimit) {
        suspiciousLimit.changeLimit(newLimit);
        notify("Limit for suspicious clients change. New limit: " + newLimit);
    }

    @Override
    public void changeClock(Clock newClock) {
        Objects.requireNonNull(newClock);
        clock = newClock;
    }

    @Override
    public Client getClient(String name, String surname) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(surname);

        for (Client client : clients) {
            if (client.getName().equals(name) && client.getSurname().equals(surname)) {
                return client;
            }
        }

        throw BankException.noClient(name, surname);
    }

    @Override
    public Account getAccount(String id) throws BanksException {
        for (Account account : accounts) {
            if (account.getId().toString().equals(id)) {
                return account;
            }
        }

        throw BankException.noSuchAccountException();
    }

    @Override
    public void addObserver(BankObserver bankObserver) throws BanksException {
        Objects.requireNonNull(bankObserver);
        if (observers.contains(bankObserver)) {
            throw BankException.alreadySubscribed(bankObserver.getId());
        }

        observers.add(bankObserver);
    }

    @Override
    public void deleteObserver(BankObserver bankObserver) throws BanksException {
        Objects.requireNonNull(bankObserver);
        if (!observers.remove(bankObserver)) {
            throw BankException.noObserver(bankObserver.getId());
        }
    }

    @Override
    public void notify(String info) {
        for (BankObserver observer : observers) {
            observer.update(info);
        }
    }
}

