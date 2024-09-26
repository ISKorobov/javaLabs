package bank.entities.clients.builders;

import bank.entities.clients.Client;
import bank.entities.clients.ConcreteClient;

import java.util.Objects;

/**
 * Concrete client builder interface
 * @author ISKor
 * @version 1.0
 */
public class ConcreteClientBuilder implements ClientNameBuilder, ClientSurnameBuilder, ClientBuilder {
    private String name;
    private String surname;
    private String address = null;
    private Integer passportNumber = null;

    @Override
    public ClientSurnameBuilder withName(String name) {
        Objects.requireNonNull(name);
        this.name = name;
        return this;
    }

    @Override
    public ClientBuilder withSurname(String surname) {
        Objects.requireNonNull(surname);
        this.surname = surname;
        return this;
    }
    @Override
    public ClientBuilder withAddress(String address) {
        this.address = address;
        return this;
    }

    @Override
    public ClientBuilder withPassportNumber(Integer passportNumber) {
        this.passportNumber = passportNumber;
        return this;
    }

    @Override
    public Client build() {
        return new ConcreteClient(name, surname, address, passportNumber);
    }
}
