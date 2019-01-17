/*
 * Dokumentation
 */
package entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Documentation of a Termin(Appointment)
 * Contains a List of Childen and a List of Adults to show who participated in the group activity the documentation documents
 * Also contains time spent on preparing for the activity(appointment) and its category
 */
@Entity
public class Dokumentation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    //attending children, String array because non-server-known children can join the group activity
    private String[] kinderliste;
    //attending adults, String array because non-server-known adults can join the group activity
    private String[] betreuer;
    private String taetigkeiten;
    private double vzeit;
    private String kategorie;

    /**
     * Default Contructor
     */
    public Dokumentation() {

    }

    /**
     * Constructor
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

    /**
     * Setter
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter
     * @return
     */
    public String[] getKinderliste() {
        return kinderliste;
    }

    /**
     * Setter, Contains a List of Childrens
     * @param kinderliste
     */
    public void setKinderliste(String[] kinderliste) {
        this.kinderliste = kinderliste;
    }

    /**
     * Getter
     * @return
     */
    public String getTaetigkeiten() {
        return taetigkeiten;
    }

    /**
     * Setter
     * @param taetigkeiten
     */
    public void setTaetigkeiten(String taetigkeiten) {
        this.taetigkeiten = taetigkeiten;
    }

    /**
     * Getter
     * @return
     */
    public double getVzeit() {
        return vzeit;
    }

    /**
     * Setter
     * @param vzeit
     */
    public void setVzeit(double vzeit) {
        this.vzeit = vzeit;
    }

    /**
     * Getter
     * @return
     */
    public String getKategorie() {
        return kategorie;
    }

    /**
     * Getter
     * @return
     */
    public String[] getBetreuer() {
        return betreuer;
    }

    /** 
     * Setter
     * @param betreuer
     */
    public void setBetreuer(String[] betreuer) {
        this.betreuer = betreuer;
    }

    /**
     * Setter
     * @param kategorie
     */
    public void setKategorie(String kategorie) {
        this.kategorie = kategorie;
    }
}
