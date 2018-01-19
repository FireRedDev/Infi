/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * C.G
 */
@Entity
public class Dokumentation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String title;
    private String beschreibung;
    private String ort;
    private String s_date;
    private String e_date;
    private String kinderliste;
    private String taetigkeiten;
    private double vzeit;
    private String kategorie;

    public Dokumentation() {

    }

    public Dokumentation(String title, String beschreibung, String ort, String s_date, String e_date, String kinderliste, String taetigkeiten, double vzeit, String kategorie) {
        this.title = title;
        this.beschreibung = beschreibung;
        this.ort = ort;
        this.s_date = s_date;
        this.e_date = e_date;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public String getOrt() {
        return ort;
    }

    public void setOrt(String ort) {
        this.ort = ort;
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
