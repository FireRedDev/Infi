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
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/**
 *
 * @author isi
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "JRKEntitaet.listAll", query = "SELECT j FROM JRKEntitaet j")
})
public class JRKEntitaet implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    private String name;
    private String ort;
    private Typ typ;
    
    @JsonIgnore
    @OneToMany(mappedBy = "jrkEntitaet")
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

     public JRKEntitaet(String name,Typ kategorie) {
        this.name = name;
        this.typ= kategorie;
    }
public void addLowerEntitaet (JRKEntitaet ent) {
    jrkentitaet1.add(ent);
}
public void setHigherEntitaet (JRKEntitaet ent) {
    this.setJrkentitaet(ent);
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
}
