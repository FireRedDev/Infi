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
    public String personalnr;

    /**
     *
     */
    public String password;

    public String getPersonalnr() {
        return personalnr;
    }

    public void setPersonalnr(String personalnr) {
        this.personalnr = personalnr;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public PersonTransferObject(String personalnr, String password) {
        this.personalnr = personalnr;
        this.password = password;
    }

    public PersonTransferObject() {
    }

}
