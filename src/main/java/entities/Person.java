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
 * @NamedQuery(name = "Benutzer.chef", query = "SELECT b FROM Person b where b.benutzer1.id=:id"),
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Benutzer.listAll", query = "SELECT b FROM Person b"),
    @NamedQuery(name = "Benutzer.list", query = "SELECT b FROM Person b where b.id=:id"),
    @NamedQuery(name = "Benutzer.personalnr", query = "SELECT b.personalnr FROM Person b where b.id=:id"),

    @NamedQuery(name = "Benutzer.login", query = "SELECT b FROM Person b where b.personalnr=:personalnr")

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

    @ManyToOne
    private JRKEntitaet jrkentitaet;
    
    @ManyToMany
    private List<JRKEntitaet> leitet;


    public Person() {
    }

    /**
     * Kinder
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
     * leitet
     * @param personalnr
     * @param password
     * @param vorname
     * @param nachname
     * @param telefonnummer
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
