package RestResponseClasses;

import entities.Person;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * C.G Contains a Token and a User, used for Rest
 */
@Entity
public class JWTTokenUser implements Serializable {

    private static final long serialVersionUID = 1L;

    private String token;
    @Id
    @GeneratedValue
    private Person person;

    /**
     * Getter
     * @return
     */
    public String getToken() {
        return token;
    }

    /**
     * Setter
     * @param token
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Getter
     * @return
     */
    public Person getPerson() {
        return person;
    }

    /**
     * Setter
     * @param person
     */
    public void setPerson(Person person) {
        this.person = person;
    }

    /**
     * Default Constructor
     */
    public JWTTokenUser() {
    }

    /**
     * Constructor
     * @param token
     * @param person
     */
    public JWTTokenUser(String token, Person person) {
        this.token = token;
        this.person = person;
    }

}
