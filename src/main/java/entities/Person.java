/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import javax.persistence.*;

/**
 *
 * @author isi
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Benutzer.listAll", query = "SELECT b FROM Benutzer b"),
    @NamedQuery(name = "Benutzer.list", query = "SELECT b FROM Benutzer b where b.id=:id"),
    @NamedQuery(name = "Benutzer.username", query = "SELECT b.username FROM Benutzer b where b.id=:id"),
    @NamedQuery(name = "Benutzer.chef", query = "SELECT b FROM Benutzer b where b.benutzer1.id=:id"),
    @NamedQuery(name = "Benutzer.login", query = "SELECT b FROM Benutzer b where b.username=:username")

})
public class Person implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    private String username;
    private String password;
    private String vorname;
    private String nachname;
    private String telefonnummer;
    private String medizinischeInfos;
    
//    private Rolle rolle;

    @ManyToOne
    private JRKEntität ortsstelle;


    public Person() {
    }

    
    public String getUsername() {
        return username;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

//    public Rolle getRolle() {
//        return rolle;
//    }
//
//    public void setRolle(Rolle rolle) {
//        this.rolle = rolle;
//    }


    @Override
    public int hashCode() {
        int hash = 5;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Person other = (Person) obj;
        if (!Objects.equals(this.username, other.username)) {
            return false;
        }
        if (!Objects.equals(this.password, other.password)) {
            return false;
        }
        return true;
    }

    public JRKEntität getJRKEntität() {
        return ortsstelle;
    }

    public void setJRKEntität(JRKEntität ortsstelle) {
        this.ortsstelle = ortsstelle;
    }

  

}
