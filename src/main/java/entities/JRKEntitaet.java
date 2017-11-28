/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author isi
 */
@Entity
public class JRKEntitaet implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    private String name;
    private String ort;
    
    @OneToMany(mappedBy = "jrkEntitaet")
    private List<Termin> termine = new LinkedList<Termin>();
    
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

    public JRKEntitaet(String name) {
        this.name = name;
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
}
