/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RestResponseClasses;

/**
 * Has the Property Email and Password
 */
public class PersonTransferObject {

    /**
     *
     */
    public String email;

    /**
     *
     */
    public String password;

    /**
     * Getter
     * @return
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Getter
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Constructor
     * @param email
     * @param password
     */
    public PersonTransferObject(String email, String password) {
        this.email = email;
        this.password = password;
    }

    /**
     * Default Constructor
     */
    public PersonTransferObject() {
    }

}
