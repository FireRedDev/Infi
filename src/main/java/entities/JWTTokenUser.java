/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
* C.G
 */
@Entity
public class JWTTokenUser implements Serializable {

    private static final long serialVersionUID = 1L;

    private String token;
    @Id
    @GeneratedValue
    private Person person;

    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public JWTTokenUser() {
    }

    public JWTTokenUser(String token, Person person) {
        this.token = token;
        this.person = person;
    }
 
 
}
