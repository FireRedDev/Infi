/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author isi
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Termin.listAll", query = "SELECT t FROM Termin t"),
    @NamedQuery(name = "Termin.listBenutzer", query = "SELECT t FROM Termin t where t.benutzer.id = :benutzerid")
})
public class Termin implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    private String s_date;

    private String e_date;
    private String title;
    @ManyToOne
    private Benutzer benutzer;
    private String beschreibung;
    private String ort;

    public Termin() {
    }

    public Termin(String s_date, String e_date, String title, Benutzer benutzer, String beschreibung, String ort) {
        this.s_date = s_date;
        this.e_date = e_date;
        this.title = title;
        this.benutzer = benutzer;
        this.beschreibung = beschreibung;
        this.ort = ort;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getS_date() {
        return s_date;
    }

    public void setS_date(String s_date) {
        this.s_date = s_date;
    }

    public String getE_date() {
        return e_date;
    }

    public void setE_date(String e_date) {
        this.e_date = e_date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Benutzer getBenutzer() {
        return benutzer;
    }

    public void setBenutzer(Benutzer benutzer) {
        this.benutzer = benutzer;
    }

    public String getOrt() {
        return ort;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

}
