/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RestResponseClasses;

/**
 * Transfers Persons --> Rest
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
     *
     * @return
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     *
     * @param email
     * @param password
     */
    public PersonTransferObject(String email, String password) {
        this.email = email;
        this.password = password;
    }

    /**
     *
     */
    public PersonTransferObject() {
    }

}
