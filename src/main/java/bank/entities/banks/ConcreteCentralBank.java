package bank.entities.banks;

import bank.clocks.Clock;
import bank.clocks.CustomClock;
import bank.entities.accounts.Account;
import bank.exceptions.BanksException;
import bank.exceptions.CentralBankException;
import bank.models.CreditConditions;
import bank.models.DebitPercent;
import bank.models.DepositPercent;
import bank.models.SuspiciousLimit;
import bank.models.transaction.AddMoney;
import bank.models.transaction.TransactionExecutor;
import bank.models.transaction.Transfer;
import bank.models.transaction.Withdraw;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Class concrete central bank
 * @author ISKor
 * @version 1.0
 */
@Getter
public class ConcreteCentralBank implements CentralBank {
    private final List<Bank> banks;
    private final TransactionExecutor transactionExecutor;
    private Clock clock = new CustomClock();

    /**
     * Creates concrete central bank
     */
    public ConcreteCentralBank() {
        banks = new ArrayList<>();
        transactionExecutor = new TransactionExecutor();
    }

    @Override
    public Bank addBank(String name, DebitPercent debitInterest, List<DepositPercent> depositInterests, CreditConditions creditConditions, SuspiciousLimit suspiciousClientLimit) throws BanksException {
        for (Bank bank : banks) {
            if (bank.getName().equals(name)) {
                throw CentralBankException.alreadyExists(name);
            }
        }

        Bank bank = new ConcreteBank(name, debitInterest, depositInterests, creditConditions, suspiciousClientLimit, clock);
        banks.add(bank);
        return bank;
    }

    @Override
    public UUID addMoneyToAccount(Account account, BigDecimal money) {
        return transactionExecutor.executeTransaction(new AddMoney(account, money));
    }

    @Override
    public UUID withdraw(Account account, BigDecimal money) {
        return transactionExecutor.executeTransaction(new Withdraw(account, money));
    }

    @Override
    public UUID transfer(Account accountSender, Account accountReceiver, BigDecimal money) {
        return transactionExecutor.executeTransaction(new Transfer(accountSender, accountReceiver, money));
    }

    @Override
    public void cancelTransaction(UUID transactionId) {
        transactionExecutor.cancelTransaction(transactionId);
    }

    @Override
    public void changeClock(Clock newClock) {
        clock = newClock;
        banks.forEach(bank -> bank.changeClock(newClock));
    }

    @Override
    public void addDays(int days) {
        clock.addDays(days);
    }

    @Override
    public Bank getBank(String name) throws BanksException {
        for (Bank bank : banks) {
            if (bank.getName().equals(name)) {
                return bank;
            }
        }

        throw CentralBankException.noBankException(name);
    }
}
