package bank.entities.clients;

import bank.entities.accounts.Account;
import bank.observers.ClientNotifier;
import bank.observers.ClientConsoleNotifier;
import bank.entities.clients.builders.ClientNameBuilder;
import bank.entities.clients.builders.ConcreteClientBuilder;
import bank.observers.BankObserver;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Class concrete client
 * @author ISKor
 * @version 1.0
 */
@Getter
@Setter
public class ConcreteClient implements Client, BankObserver {
    private final List<Account> accounts;

    /**
     * Creates concrete client
     * @param name Name client
     * @param surname Surname client
     * @param address Address client (can be null)
     * @param passportNumber Passport number client (can be null)
     */
    public ConcreteClient(String name, String surname, String address, Integer passportNumber) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(surname);
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.passport = passportNumber;
        this.id = UUID.randomUUID();
        accounts = new ArrayList<>();
        clientNotifier = new ClientConsoleNotifier();
    }

    /**
     * Name client
     */
    public String name;
    /**
     * Surname client
     */
    public String surname;
    /**
     * Address client
     */
    public String address;
    /**
     * Passport number client
     */
    public Integer passport;
    /**
     * Id client
     */
    public UUID id;
    /**
     * Client's notifier
     */
    public ClientNotifier clientNotifier;


    /**
     * Returns start client builder
     * @return Start client builder
     */
    public static ClientNameBuilder getBuilder() {
        return new ConcreteClientBuilder();
    }

    @Override
    public boolean suspicious() {
        return address == null || passport == null;
    }

    @Override
    public void changeNotifier(ClientNotifier clientNotifier) {
        Objects.requireNonNull(clientNotifier);
        this.clientNotifier = clientNotifier;
    }

    @Override
    public void addAccount(Account account) {
        Objects.requireNonNull(account);
        accounts.add(account);
    }

    @Override
    public void addAddress(String address) {
        this.address = address;
    }

    @Override
    public void addPassport(Integer passport) {
        this.passport = passport;
    }

    @Override
    public void update(String info) {
        clientNotifier.notify(info);
    }
}
