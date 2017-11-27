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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author isi
 */
@Entity
public class JRKEntität implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    private String name;
    private String Strasse;
    @OneToMany(mappedBy = "benutzer")
    private List<Termin> termine = new LinkedList<Termin>();
        @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "jrkentitaet_id")
    private Person jrkentitaet;
            
    @OneToMany(mappedBy = "jrkentitaet",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Person> jrkentitaet1;

    public JRKEntität() {
    }

    public JRKEntität(String name) {
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


}
