/**
 * Person
 */
package entities;

import java.io.Serializable;
import javax.persistence.*;
import service.Role;

/**
 *
 * @author Christopher G
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Benutzer.listAll", query = "SELECT b FROM Person b")
    ,
    @NamedQuery(name = "Benutzer.list", query = "SELECT b FROM Person b where b.id=:id")
    ,
    @NamedQuery(name = "Benutzer.personalnr", query = "SELECT b.personalnr FROM Person b where b.id=:id")
    ,
    @NamedQuery(name = "Benutzer.login", query = "SELECT b FROM Person b where b.personalnr=:personalnr")
    ,
    @NamedQuery(name = "Benutzer.name", query = "SELECT concat(b.vorname,' ',b.nachname) FROM Person b where b.id=:id")
    ,
    @NamedQuery(name = "Benutzer.jrkEntitaet", query = "SELECT b.jrkentitaet FROM Person b where b.id=:id")

})
public class Person implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    private String personalnr;
    private String password;
    private String vorname;
    private String nachname;
    private Role rolle;
    @ManyToOne(fetch = FetchType.LAZY)
    private JRKEntitaet jrkentitaet;

    /**
     *
     */
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
    public Person(String personalnr, String password, String vorname, String nachname, JRKEntitaet jrkentitaet) {
        this.personalnr = personalnr;
        this.password = password;
        this.vorname = vorname;
        this.nachname = nachname;
        this.jrkentitaet = jrkentitaet;
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
     * @param personalnr
     * @param password
     * @param vorname
     * @param nachname
     * @param jrkentitaet
     * @param rolle
     */
    public Person(String personalnr, String password, String vorname, String nachname, JRKEntitaet jrkentitaet, Role rolle) {
        this.personalnr = personalnr;
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
    public String getPersonalnr() {
        return personalnr;
    }

    /**
     *
     * @param personalnr
     */
    public void setPersonalnr(String personalnr) {
        this.personalnr = personalnr;
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

}
