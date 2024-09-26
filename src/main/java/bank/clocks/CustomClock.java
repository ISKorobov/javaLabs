package bank.clocks;

import bank.exceptions.BanksException;
import bank.exceptions.ClockException;
import bank.observers.TimeObserver;
import lombok.Getter;

import java.util.*;

/**
 * Class for work with virtual time
 * @author ISKor
 * @version 1.0
 */

@Getter
public class CustomClock implements Clock {
    private final List<TimeObserver> observers;
    private final Timer timer;

    /**
     * Creates new clock and sets a timer
     */
    public CustomClock() {
        time = Calendar.getInstance().getTime();
        timer = new Timer();
        observers = new ArrayList<TimeObserver>();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                addDays(1);
            }
        };
        timer.scheduleAtFixedRate(task, 1000L, 1000L * 60L * 60L * 24);
    }

    /**
     * Store time
     */
    public Date time;

    @Override
    public void addDays(int days) throws BanksException {
        if (days < 0) {
            throw ClockException.clockNegativeDaysException();
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        calendar.add(Calendar.DATE, days);
        time = calendar.getTime();
        notify(time);
    }

    @Override
    public void addObserver(TimeObserver timeObserver) {
        observers.add(timeObserver);
    }

    @Override
    public void notify(Date time) {
        for (TimeObserver observer : observers) {
            observer.update(time);
        }
    }
}
