/**
 * Person
 */
package entities;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.*;

/**
 * A Person(Kind,Leiter,..) that is also a User in our System, with Password and
 * Username, mail adress and a Role used for checking permissions.
 *
 *
 */
@Entity
@NamedQueries({
    /**
     * Returns All Persons
     */
    @NamedQuery(name = "Benutzer.listAll", query = "SELECT b FROM Person b")
    ,
    /**
     * Returns Person with the ID
     */
    @NamedQuery(name = "Benutzer.list", query = "SELECT b FROM Person b where b.id=:id")
    ,
    /**
     * Returns email of the person with the id
     */
    @NamedQuery(name = "Benutzer.email", query = "SELECT b.email FROM Person b where b.id=:id")
    ,
    /**
     * Returns Person with the email
     */
    @NamedQuery(name = "Benutzer.login", query = "SELECT b FROM Person b where b.email=:email")
    ,
    /**
     * Returns Full Name of Person with the id
     */
    @NamedQuery(name = "Benutzer.name", query = "SELECT concat(b.vorname,' ',b.nachname) FROM Person b where b.id=:id")
    ,
    /**
     * Returns a Persons JRKEntitaet with the Persons id
     */
    @NamedQuery(name = "Benutzer.jrkEntitaet", query = "SELECT b.jrkentitaet FROM Person b where b.id=:id")
    ,
    /**
     * Returns Person whos JRKEntitaet has ID
     */
    @NamedQuery(name = "Benutzer.byjrkEntitaet", query = "SELECT b FROM Person b where b.jrkentitaet.id=:id")
    ,
    /**
     * Finds User by his Full Name
     */
@NamedQuery(name = "Benutzer.findbyname", query = "SELECT b from Person b where CONCAT(b.vorname,' ',b.nachname) like :var")
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
    private String fcmtoken;
    //which JRKEntity does the User/Person belong to
    @ManyToOne(fetch = FetchType.LAZY)
    private JRKEntitaet jrkentitaet;
    //has the password already been changed?
    private boolean passwordChanged;

    /**
     * Default Constructor
     */
    public Person() {
    }

    /**
     * Constructor
     *
     * @param email
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
     * Getter
     *
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     * Getter
     *
     * @return
     */
    public Role getRolle() {
        return rolle;
    }

    /**
     * Setter
     *
     * @param rolle
     */
    public void setRolle(Role rolle) {
        this.rolle = rolle;
    }

    /**
     * Constructor
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
     * Setter
     *
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Getter
     *
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * Setter
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter
     *
     * @return
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter
     *
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Getter
     *
     * @return
     */
    public String getVorname() {
        return vorname;
    }

    /**
     * Setter
     *
     * @param vorname
     */
    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    /**
     * Getter
     *
     * @return
     */
    public String getNachname() {
        return nachname;
    }

    /**
     * Setter
     *
     * @param nachname
     */
    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    /**
     * Getter
     *
     * @return
     */
    public JRKEntitaet getJrkentitaet() {
        return jrkentitaet;
    }

    /**
     * Setter
     *
     * @param jrkentitaet
     */
    public void setJrkentitaet(JRKEntitaet jrkentitaet) {
        this.jrkentitaet = jrkentitaet;
    }

    /**
     * Has the Password been changed already?
     *
     * @return
     */
    public boolean isPasswordChanged() {
        return passwordChanged;
    }

    /**
     * Setter
     *
     * @param passwordChanged
     */
    public void setPasswordChanged(boolean passwordChanged) {
        this.passwordChanged = passwordChanged;
    }

    /**
     * Getter
     *
     * @return
     */
    public String getFcmtoken() {
        return fcmtoken;
    }

    /**
     * Setter
     *
     * @param fcmtoken
     */
    public void setFcmtoken(String fcmtoken) {
        this.fcmtoken = fcmtoken;
    }

    //hashCode
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + this.id;
        hash = 67 * hash + Objects.hashCode(this.email);
        hash = 67 * hash + Objects.hashCode(this.password);
        hash = 67 * hash + Objects.hashCode(this.vorname);
        hash = 67 * hash + Objects.hashCode(this.nachname);
        hash = 67 * hash + Objects.hashCode(this.rolle);
        hash = 67 * hash + Objects.hashCode(this.jrkentitaet);
        return hash;
    }

    //equals
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
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.password, other.password)) {
            return false;
        }
        if (!Objects.equals(this.vorname, other.vorname)) {
            return false;
        }
        if (!Objects.equals(this.nachname, other.nachname)) {
            return false;
        }
        if (this.rolle != other.rolle) {
            return false;
        }
        if (!Objects.equals(this.jrkentitaet, other.jrkentitaet)) {
            return false;
        }
        return true;
    }

}
