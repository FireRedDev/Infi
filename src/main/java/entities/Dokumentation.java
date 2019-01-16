/*
 * Dokumentation
 */
package entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Documentation of a Termin(Appointment), each one has a list of children,
 * adults caring for them and things like spent time or stuff they did, or which
 * category the lesson is
 */
@Entity
public class Dokumentation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String[] kinderliste;
    private String[] betreuer;
    private String taetigkeiten;
    private double vzeit;
    private String kategorie;
    private String erkenntnisse;
    private String spiele;
    private String materialien;
    private String inhalt;

    /**
     *
     */
    public Dokumentation() {

    }

    /**
     *
     * @param kinderliste
     * @param betreuer
     * @param taetigkeiten
     * @param vzeit
     * @param kategorie
     */
    public Dokumentation(String[] kinderliste, String[] betreuer, String taetigkeiten, double vzeit, String kategorie) {
        this.kinderliste = kinderliste;
        this.betreuer = betreuer;
        this.taetigkeiten = taetigkeiten;
        this.vzeit = vzeit;
        this.kategorie = kategorie;
    }

    public Dokumentation(String[] kinderliste, String[] betreuer, String taetigkeiten, double vzeit, String kategorie, String erkenntnisse, String spiele, String materialien, String inhalt) {
        this.kinderliste = kinderliste;
        this.betreuer = betreuer;
        this.taetigkeiten = taetigkeiten;
        this.vzeit = vzeit;
        this.kategorie = kategorie;
        this.erkenntnisse = erkenntnisse;
        this.spiele = spiele;
        this.materialien = materialien;
        this.inhalt = inhalt;
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
    public String[] getKinderliste() {
        return kinderliste;
    }

    /**
     *
     * @param kinderliste
     */
    public void setKinderliste(String[] kinderliste) {
        this.kinderliste = kinderliste;
    }

    /**
     *
     * @return
     */
    public String getTaetigkeiten() {
        return taetigkeiten;
    }

    /**
     *
     * @param taetigkeiten
     */
    public void setTaetigkeiten(String taetigkeiten) {
        this.taetigkeiten = taetigkeiten;
    }

    /**
     *
     * @return
     */
    public double getVzeit() {
        return vzeit;
    }

    /**
     *
     * @param vzeit
     */
    public void setVzeit(double vzeit) {
        this.vzeit = vzeit;
    }

    /**
     *
     * @return
     */
    public String getKategorie() {
        return kategorie;
    }

    /**
     *
     * @return
     */
    public String[] getBetreuer() {
        return betreuer;
    }

    /**
     *
     * @param betreuer
     */
    public void setBetreuer(String[] betreuer) {
        this.betreuer = betreuer;
    }

    /**
     *
     * @param kategorie
     */
    public void setKategorie(String kategorie) {
        this.kategorie = kategorie;
    }

    public String getErkenntnisse() {
        return erkenntnisse;
    }

    public void setErkenntnisse(String erkenntnisse) {
        this.erkenntnisse = erkenntnisse;
    }

    public String getSpiele() {
        return spiele;
    }

    public void setSpiele(String spiele) {
        this.spiele = spiele;
    }

    public String getMaterialien() {
        return materialien;
    }

    public void setMaterialien(String materialien) {
        this.materialien = materialien;
    }

    public String getInhalt() {
        return inhalt;
    }

    public void setInhalt(String inhalt) {
        this.inhalt = inhalt;
    }
    
    
}
