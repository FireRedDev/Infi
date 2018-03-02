/*
 * Dokumentation
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
    private String[] kinderliste;
    private String[] betreuer;
    private String taetigkeiten;
    private double vzeit;
    private String kategorie;

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
}
