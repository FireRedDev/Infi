/**
 * Person
 */
package entities;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.*;

/**
 * A Person(Kind,Leiter,..) that is also a User in our System, with Password and
 * Username, mail adress and a role used for checking permissions.
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
    @NamedQuery(name = "Benutzer.byjrkEntitaet", query = "SELECT b FROM Person b where b.jrkentitaet.id=:id"),
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
    @ManyToOne(fetch = FetchType.LAZY)
    private JRKEntitaet jrkentitaet;
    //has the password already been changed?
    private boolean passwordChanged;

    /**
     *
     */
    public Person() {
    }

    /**
     * Konstruktur mit allen Parametern
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

    /**
     * 
     * @return 
     */
    public boolean isPasswordChanged() {
        return passwordChanged;
    }

    /**
     * 
     * @param passwordChanged 
     */
    public void setPasswordChanged(boolean passwordChanged) {
        this.passwordChanged = passwordChanged;
    }

    public String getFcmtoken() {
        return fcmtoken;
}

    public void setFcmtoken(String fcmtoken) {
        this.fcmtoken = fcmtoken;
    }

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
