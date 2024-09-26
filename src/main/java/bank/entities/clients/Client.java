package bank.entities.clients;

import bank.entities.accounts.Account;
import bank.observers.ClientNotifier;

import java.util.List;
import java.util.UUID;

/**
 * Methods for the clients
 * @author ISKor
 * @version 1.0
 */
public interface Client {
    /**
     * Checks if the client is suspicious
     * @return True - suspicious, False - not suspicious
     */
    boolean suspicious();

    /**
     * Change notifier
     * @param notifier New notifier
     */
    void changeNotifier(ClientNotifier notifier);

    /**
     * Add account
     * @param account Account to add
     */
    void addAccount(Account account);

    /**
     * Add address
     * @param address Address (can be null)
     */
    void addAddress(String address);

    /**
     * Add passport number
     * @param number Passport number (can be null)
     */
    void addPassport(Integer number);

    /**
     * Return Id
     * @return Client's Id
     */
    UUID getId();

    /**
     * Client's name
     * @return Returns client's name
     */
    String getName();

    /**
     * Client's surname
     * @return Returns client's surname
     */
    String getSurname();

    /**
     * The entire list of client accounts
     * @return Returns the entire list of client accounts
     */
    List<Account> getAccounts();
}
