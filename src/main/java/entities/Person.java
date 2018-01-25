/**
 * Person
 */
package entities;
import java.io.Serializable;
import java.util.*;
import javax.persistence.*;

@Entity
@NamedQueries({
    @NamedQuery(name = "Benutzer.listAll", query = "SELECT b FROM Person b"),
    @NamedQuery(name = "Benutzer.list", query = "SELECT b FROM Person b where b.id=:id"),
    @NamedQuery(name = "Benutzer.personalnr", query = "SELECT b.personalnr FROM Person b where b.id=:id"),
    @NamedQuery(name = "Benutzer.login", query = "SELECT b FROM Person b where b.personalnr=:personalnr"),
    @NamedQuery(name = "Benutzer.name", query = "SELECT concat(b.vorname,' ',b.nachname) FROM Person b where b.id=:id"),
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
    private String telefonnummer;

    @ManyToOne(fetch = FetchType.LAZY)
    private JRKEntitaet jrkentitaet;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<JRKEntitaet> leitet;

    public Person() {
    }

    /**
     * Kinder
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
     * Leiter
     *
     * @param personalnr
     * @param password
     * @param vorname
     * @param nachname
     * @param jrkentitaet
     * @param leitet
     */
    public Person(String personalnr, String password, String vorname, String nachname, JRKEntitaet jrkentitaet, List<JRKEntitaet> leitet) {
        this.personalnr = personalnr;
        this.password = password;
        this.vorname = vorname;
        this.nachname = nachname;
        this.jrkentitaet = jrkentitaet;
        this.leitet = leitet;
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

    public String getPersonalnr() {
        return personalnr;
    }

    public void setPersonalnr(String personalnr) {
        this.personalnr = personalnr;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public String getTelefonnummer() {
        return telefonnummer;
    }

    public void setTelefonnummer(String telefonnummer) {
        this.telefonnummer = telefonnummer;
    }

    public List<JRKEntitaet> getLeitet() {
        return leitet;
    }

    public void setLeitet(List<JRKEntitaet> leitet) {
        this.leitet = leitet;
    }

    public JRKEntitaet getJrkentitaet() {
        return jrkentitaet;
    }

    public void setJrkentitaet(JRKEntitaet jrkentitaet) {
        this.jrkentitaet = jrkentitaet;
    }

}
