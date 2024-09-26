package bank.entities.clients.builders;

/**
 * Builder with name interface
 * @author ISKor
 * @version 1.0
 */
public interface ClientNameBuilder {
    /**
     * Add name
     * @param name Name
     * @return Builder with surname
     */
    ClientSurnameBuilder withName(String name);
}