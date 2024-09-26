package bank.entities.clients.builders;

/**
 * Builder with surname interface
 * @author ISKor
 * @version 1.0
 */
public interface ClientSurnameBuilder {
    /**
     * Add surname
     * @param surname Surname
     * @return Builder with build
     */
    ClientBuilder withSurname(String surname);
}
