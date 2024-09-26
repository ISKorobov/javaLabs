package bank.entities.accounts;

import bank.clocks.Clock;
import bank.entities.banks.Bank;
import bank.exceptions.AccountException;
import bank.entities.clients.Client;
import bank.exceptions.BanksException;
import bank.observers.TimeObserver;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Class debit account
 * @author ISKor
 * @version 1.0
 */
@Getter
@Setter
public class DebitAccount implements Account, TimeObserver {
    private final Clock clock;
    private BigDecimal percent = BigDecimal.valueOf(0);
    private Date previousCheckDay;
    private int checkedDaysCounter = 0;
    private String accountType = "Debit";

    /**
     * Creates debit account
     * @param client Client account`s owner
     * @param bank Bank in which this account
     * @param money Start money
     * @param clock Clock for this account
     */
    public DebitAccount(Client client, Bank bank, BigDecimal money, Clock clock) {
        check(money);
        accountClient = client;
        accountBank = bank;
        balance = money;
        this.clock = clock;
        this.clock.addObserver(this);
        previousCheckDay = Date.from(Calendar.getInstance().toInstant());
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
        return BigDecimal.valueOf(0);
    }

    @Override
    public void withdrawMoney(BigDecimal money) throws BanksException {
        check(money);
        if (suspicious() && money.compareTo(accountBank.getSuspiciousLimit().limit) > 0) {
            throw AccountException.suspiciousClientException(money, accountBank.getId());
        }

        if (balance.subtract(money).compareTo(BigDecimal.valueOf(0)) < 0) {
            throw AccountException.notEnoughMoneyException(balance, money);
        }

        balance = balance.subtract(money);
    }

    @Override
    public void addMoney(BigDecimal money) {
        check(money);
        balance = balance.add(money);
    }

    /**
     * Add commission
     */
    public void addCommission() {
        BigDecimal dailyInterest = accountBank.getDebitPercent().percent.divide(BigDecimal.valueOf(36500), 10, RoundingMode.CEILING);
        percent = percent.add(balance.multiply(dailyInterest));
    }

    /**
     * Accrues commission and sets its value to 0 for a new period
     */
    public void accrueCommission() {
        balance = balance.add(percent);
        percent = BigDecimal.valueOf(0);
    }

    @Override
    public void update(Date currentTime) {
        long daysUnchecked = TimeUnit.DAYS.convert(Math.abs(currentTime.getTime() - previousCheckDay.getTime()), TimeUnit.MILLISECONDS);
        daysUnchecked += 1;
        while (daysUnchecked > 0) {
            addCommission();
            checkedDaysCounter++;

            if (checkedDaysCounter == 30)
            {
                accrueCommission();
                checkedDaysCounter = 0;
            }

            daysUnchecked--;
        }
        previousCheckDay = currentTime;
    }

    /**
     * Checks if money is valid
     * @param money Money to check
     * @throws BanksException Throws exception if money is incorrect
     */
    private static void check(BigDecimal money) throws BanksException {
        if (money.compareTo(BigDecimal.valueOf(0)) < 0) {
            throw AccountException.negativeMoneyException();
        }
    }
}
