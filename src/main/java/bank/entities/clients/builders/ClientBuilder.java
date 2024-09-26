package bank.entities.clients.builders;

import bank.entities.clients.Client;

/**
 * Builder with build() method interface
 * @author ISKor
 * @version 1.0
 */
public interface ClientBuilder {
    /**
     * Adds address
     * @param address Address
     * @return This builder
     */
    ClientBuilder withAddress(String address);

    /**
     * Add passport number
     * @param number Passport number
     * @return This builder
     */
    ClientBuilder withPassportNumber(Integer number);

    /**
     * Method client build
     * @return Returns finish client
     */
    Client build();
}
