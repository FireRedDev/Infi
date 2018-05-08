/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RestResponseClasses;

/**
 * Transfers Persons C.G
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public PersonTransferObject(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public PersonTransferObject() {
    }

}
