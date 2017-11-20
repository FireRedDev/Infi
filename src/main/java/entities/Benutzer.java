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
public class Benutzer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    private String username;
    private String password;
//    private Rolle rolle;
    @OneToMany(mappedBy = "benutzer")
    private List<Termin> termine = new LinkedList<Termin>();
    @OneToMany(mappedBy = "benutzer1",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Benutzer> benutzer;
    @ManyToOne
    private Ortsstelle ortsstelle;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "benuzter1_id")
    private Benutzer benutzer1;

    public Benutzer() {
    }

    public Benutzer(String username, String password, Benutzer benutzer1, Ortsstelle ortsstelle) {
        this.username = username;
        this.password = password;
        benutzer = new LinkedList();
        this.benutzer1 = benutzer1;
        this.ortsstelle = ortsstelle;
    }
    
    public String getUsername() {
        return username;
    }

    public void addTermin(Termin termin) {
        termine.add(termin);
    }

    public void removeTermin(Termin termin) {
        termine.remove(termin);
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
    public List<Termin> getTermine() {
        return termine;
    }

    public void setTermine(List<Termin> termine) {
        this.termine = termine;
    }

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
        final Benutzer other = (Benutzer) obj;
        if (!Objects.equals(this.username, other.username)) {
            return false;
        }
        if (!Objects.equals(this.password, other.password)) {
            return false;
        }
        return true;
    }

    public Ortsstelle getOrtsstelle() {
        return ortsstelle;
    }

    public void setOrtsstelle(Ortsstelle ortsstelle) {
        this.ortsstelle = ortsstelle;
    }

    public void addBenutzer(Benutzer besitzer) {
        if (!this.benutzer.contains(besitzer)) {
            this.benutzer.add(besitzer);
            besitzer.setBenutzer1(this);
        }
    }

    public Benutzer getBenutzer1() {
        return benutzer1;
    }

    public void setBenutzer1(Benutzer benutzer1) {
        this.benutzer1 = benutzer1;
    }

    public void removeBenutzer(Benutzer besitzer) {
        if (this.benutzer.contains(besitzer)) {
            this.benutzer.remove(besitzer);
            besitzer.setBenutzer1(this);
        }
    }

    public List<Benutzer> getBenutzer() {
        return benutzer;
    }

    public void setBenutzer(List<Benutzer> benutzer) {
        this.benutzer = benutzer;
    }

}
