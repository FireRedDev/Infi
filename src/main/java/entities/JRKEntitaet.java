/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.*;

/**
 *
 * @author isi
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "JRKEntitaet.listAll", query = "SELECT j FROM JRKEntitaet j"),
    @NamedQuery(name = "JRKEntitaet.layerDown", query = "SELECT j FROM JRKEntitaet j where j.jrkentitaet=:jrkentitaet"),
    @NamedQuery(name = "JRKEntitaet.layerUp", query = "SELECT j FROM JRKEntitaet j where j.jrkentitaet1=:jrkentitaet")
})
public class JRKEntitaet implements Serializable {

    @Id
    int id;
    private String name;
    private String ort;
    private Typ typ;
    @OneToMany(fetch = FetchType.LAZY)
    private List<Termin> termine = new LinkedList<Termin>();

    public void addTermin(Termin termin) {
        termine.add(termin);
    }
    //übergeordnet
    @ManyToOne
    private JRKEntitaet jrkentitaet;

    //untergeordnet
    @OneToMany(mappedBy = "jrkentitaet")
    private List<JRKEntitaet> jrkentitaet1;

    @ManyToMany(mappedBy = "leitet")
    private List<Person> persons;
    @OneToMany(mappedBy = "jrkentitaet")
    private List<Person> persons1;

    public JRKEntitaet() {

    }

    public JRKEntitaet(int id, String name, Typ typ, JRKEntitaet jrkentitaet) {
        this.id = id;
        this.name = name;
        this.typ = typ;
        this.jrkentitaet = jrkentitaet;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public Typ getTyp() {
        return typ;
    }

    public void setTyp(Typ typ) {
        this.typ = typ;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrt() {
        return ort;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }

    public List<Termin> getTermine() {
        return termine;
    }

    public void setTermine(List<Termin> termine) {
        this.termine = termine;
    }

    public JRKEntitaet getJrkentitaet() {
        return jrkentitaet;
    }

    public void setJrkentitaet(JRKEntitaet jrkentitaet) {
        this.jrkentitaet = jrkentitaet;
    }

    public List<JRKEntitaet> getJrkentitaet1() {
        return jrkentitaet1;
    }

    public void setJrkentitaet1(List<JRKEntitaet> jrkentitaet1) {
        this.jrkentitaet1 = jrkentitaet1;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    public List<Person> getPersons1() {
        return persons1;
    }

    public void setPersons1(List<Person> persons1) {
        this.persons1 = persons1;
    }
    
    public void addJRKEntitaet(JRKEntitaet newJRK) {
        if (!this.jrkentitaet1.contains(newJRK)) {
            this.jrkentitaet1.add(newJRK);
            newJRK.setJrkentitaet(this);
        }
    }

    public void removeJRKEntitaet(JRKEntitaet old) {
        if (this.jrkentitaet1.contains(old)) {
            this.jrkentitaet1.remove(old);
            old.setJrkentitaet(this);
        }
    }
}
