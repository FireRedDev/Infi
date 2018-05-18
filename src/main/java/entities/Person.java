/**
 * Person
 */
package entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A Person(Kind,Leiter,..) that is also a User in our System, with Password and
 * Username
 *
 * @author Christopher G
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Benutzer.listAll", query = "SELECT b FROM Person b")
    ,
    @NamedQuery(name = "Benutzer.list", query = "SELECT b FROM Person b where b.id=:id")
    ,
    @NamedQuery(name = "Benutzer.email", query = "SELECT b.email FROM Person b where b.id=:id")
    ,
    @NamedQuery(name = "Benutzer.login", query = "SELECT b FROM Person b where b.email=:email")
    ,
    @NamedQuery(name = "Benutzer.name", query = "SELECT concat(b.vorname,' ',b.nachname) FROM Person b where b.id=:id")
    ,
    @NamedQuery(name = "Benutzer.jrkEntitaet", query = "SELECT b.jrkentitaet FROM Person b where b.id=:id")
        ,
    @NamedQuery(name = "Benutzer.byjrkEntitaet", query = "SELECT b FROM Person b where b.jrkentitaet=:id")

})
public class Person implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    //username
    private String email;
    private String password;
    private String vorname;
    private String nachname;
    private Role rolle;
    @ManyToOne(fetch = FetchType.LAZY)
    private JRKEntitaet jrkentitaet;
    private boolean passwordChanged;

    public Person() {
    }

    /**
     * Konstruktur mit allen Parametern
     *
     * @param personalnr
     * @param password
     * @param vorname
     * @param nachname
     * @param jrkentitaet
     */
    public Person(String email, String password, String vorname, String nachname, JRKEntitaet jrkentitaet) {
        this.email = email;
        this.password = password;
        this.vorname = vorname;
        this.nachname = nachname;
        this.jrkentitaet = jrkentitaet;
        this.passwordChanged = false;
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
     * @return
     */
    public Role getRolle() {
        return rolle;
    }

    /**
     *
     * @param rolle
     */
    public void setRolle(Role rolle) {
        this.rolle = rolle;
    }

    /**
     *
     * @param email
     * @param password
     * @param vorname
     * @param nachname
     * @param jrkentitaet
     * @param rolle
     */
    public Person(String email, String password, String vorname, String nachname, JRKEntitaet jrkentitaet, Role rolle) {
        this.email = email;
        this.password = password;
        this.vorname = vorname;
        this.nachname = nachname;
        this.rolle = rolle;
        this.jrkentitaet = jrkentitaet;
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
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

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
    public String getVorname() {
        return vorname;
    }

    /**
     *
     * @param vorname
     */
    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    /**
     *
     * @return
     */
    public String getNachname() {
        return nachname;
    }

    /**
     *
     * @param nachname
     */
    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    /**
     *
     * @return
     */
    public JRKEntitaet getJrkentitaet() {
        return jrkentitaet;
    }

    /**
     *
     * @param jrkentitaet
     */
    public void setJrkentitaet(JRKEntitaet jrkentitaet) {
        this.jrkentitaet = jrkentitaet;
    }

    public boolean isPasswordChanged() {
        return passwordChanged;
    }

    public void setPasswordChanged(boolean passwordChanged) {
        this.passwordChanged = passwordChanged;
    }
}
