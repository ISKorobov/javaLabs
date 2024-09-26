package bank.entities.accounts;

import bank.entities.banks.Bank;
import bank.entities.clients.Client;
import bank.exceptions.AccountException;
import bank.exceptions.BanksException;
import bank.clocks.Clock;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Class credit account
 * @author ISKor
 * @version 1.0
 */
@Getter
@Setter
public class CreditAccount implements Account {
    private String accountType = "Credit";

    /**
     * Creates credit account
     * @param client Client account`s owner
     * @param bank Bank in which this account
     * @param money Start money
     * @param clock Clock for this account
     */
    public CreditAccount(Client client, Bank bank, BigDecimal money, Clock clock) {
        check(money);
        accountClient = client;
        accountBank = bank;
        balance = money;
        id = UUID.randomUUID();
    }

    /**
     * Account's id
     */
    public UUID id;
    /**
     * Client account`s owner
     */
    public Client accountClient;
    /**
     * Bank in which this account
     */
    public Bank accountBank;
    /**
     * Balance
     */
    public BigDecimal balance;

    @Override
    public boolean suspicious() {
        return accountClient.suspicious();
    }

    @Override
    public BigDecimal fixedFee() {
        return accountBank.getCreditConditions().fee;
    }

    @Override
    public void withdrawMoney(BigDecimal money) throws BanksException {
        check(money);

        if (suspicious() && money.compareTo(accountBank.getSuspiciousLimit().limit) > 0) {
            throw AccountException.suspiciousClientException(money, accountBank.getId());
        }

        if (balance.subtract(money).compareTo(BigDecimal.valueOf(0)) < 0) {
            if (balance.subtract(money).subtract(fixedFee()).compareTo(accountBank.getCreditConditions().limit.multiply(BigDecimal.valueOf(-1))) < 0) {
                throw AccountException.creditAccountNotEnoughMoneyException(money);
            }

            balance = balance.subtract(money.add(fixedFee()));
        }
        else {
            balance = balance.subtract(money);
        }
    }

    @Override
    public void addMoney(BigDecimal money) {
        check(money);
        balance = balance.add(money);
    }

    /**
     * Check money
     * @param money Money to check
     * @throws BanksException Throws exception if money is negative
     */
    private static void check(BigDecimal money) throws BanksException {
        if (money.compareTo(BigDecimal.valueOf(0)) < 0) {
            throw AccountException.negativeMoneyException();
        }
    }
}

