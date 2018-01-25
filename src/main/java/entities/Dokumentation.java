/*
 * Dokumentation
 */
package entities;

import java.io.Serializable;
import javax.persistence.*;

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

    public Dokumentation() {

    }

    public Dokumentation(String[] kinderliste, String[] betreuer, String taetigkeiten, double vzeit, String kategorie) {
        this.kinderliste = kinderliste;
        this.betreuer = betreuer;
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

    public String[] getKinderliste() {
        return kinderliste;
    }

    public void setKinderliste(String[] kinderliste) {
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

    public String[] getBetreuer() {
        return betreuer;
    }

    public void setBetreuer(String[] betreuer) {
        this.betreuer = betreuer;
    }

    public void setKategorie(String kategorie) {
        this.kategorie = kategorie;
    }

}
