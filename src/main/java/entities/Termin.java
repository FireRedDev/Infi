/**
 * Termin
 */

package entities;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@NamedQueries({
    @NamedQuery(name = "Termin.listAll", query = "SELECT t FROM Termin t"),
    @NamedQuery(name = "Termin.getOpenDoko", query = "SELECT t FROM Termin t where t.doko IS NULL"),
})
public class Termin implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    private String s_date;

    private String e_date;
    private String title;

    private String beschreibung;
    private String ort;
    @OneToOne
    private Dokumentation doko;

    public Termin() {
    }

    /**
     * Konstruktor ohne Dokumentation
     * 
     * @param s_date
     * @param e_date
     * @param title
     * @param beschreibung
     * @param ort 
     */
    public Termin(String s_date, String e_date, String title, String beschreibung, String ort) {
        this.s_date = s_date;
        this.e_date = e_date;
        this.title = title;
        this.beschreibung = beschreibung;
        this.ort = ort;
    }

    /**
     * Konstruktor mit allen Parametern
     * 
     * @param s_date
     * @param e_date
     * @param title
     * @param beschreibung
     * @param ort
     * @param doko 
     */
    public Termin(String s_date, String e_date, String title, String beschreibung, String ort, Dokumentation doko) {
        this.s_date = s_date;
        this.e_date = e_date;
        this.title = title;
        this.beschreibung = beschreibung;
        this.ort = ort;
        this.doko = doko;
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

    public Dokumentation getDoko() {
        return doko;
    }

    public void setDoko(Dokumentation doko) {
        this.doko = doko;
    }

}
