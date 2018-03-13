package RestResponseClasses;

import entities.Person;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * C.G Transports a Token and a User to client or Server
 */
@Entity
public class JWTTokenUser implements Serializable {

    private static final long serialVersionUID = 1L;

    private String token;
    @Id
    @GeneratedValue
    private Person person;

    /**
     *
     * @return
     */
    public String getToken() {
        return token;
    }

    /**
     *
     * @param token
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     *
     * @return
     */
    public Person getPerson() {
        return person;
    }

    /**
     *
     * @param person
     */
    public void setPerson(Person person) {
        this.person = person;
    }

    /**
     *
     */
    public JWTTokenUser() {
    }

    /**
     *
     * @param token
     * @param person
     */
    public JWTTokenUser(String token, Person person) {
        this.token = token;
        this.person = person;
    }

}
