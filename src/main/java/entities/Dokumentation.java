/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * C.G
 */
@Entity
public class Dokumentation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String kinderliste;
    private String taetigkeiten;
    private double vzeit;
    private String kategorie;

    public Dokumentation() {

    }

    public Dokumentation(String kinderliste, String taetigkeiten, double vzeit, String kategorie) {
        this.kinderliste = kinderliste;
        this.taetigkeiten = taetigkeiten;
        this.vzeit = vzeit;
        this.kategorie = kategorie;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKinderliste() {
        return kinderliste;
    }

    public void setKinderliste(String kinderliste) {
        this.kinderliste = kinderliste;
    }

    public String getTaetigkeiten() {
        return taetigkeiten;
    }

    public void setTaetigkeiten(String taetigkeiten) {
        this.taetigkeiten = taetigkeiten;
    }

    public double getVzeit() {
        return vzeit;
    }

    public void setVzeit(double vzeit) {
        this.vzeit = vzeit;
    }

    public String getKategorie() {
        return kategorie;
    }

    public void setKategorie(String kategorie) {
        this.kategorie = kategorie;
    }

}
