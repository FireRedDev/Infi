/**
 * Termin, has an optional Documentation class attached.
 */
package entities;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.*;

/**
 * Appointment consists of title, beschreibung, ort, startdate, enddate, imgpath
 * @author Christopher G
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Termin.listAll", query = "SELECT t FROM Termin t"),
    @NamedQuery(name = "Termin.getOpenDoko", query = "SELECT t FROM Termin t where t.doko IS NULL")})
public class Termin implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    private String s_date;
    private String e_date;
    private String title;
    private String beschreibung;
    private String ort;
    private String imgpath;
    @OneToOne
    private Dokumentation doko;
    LinkedList Teilnehmer = new LinkedList();

    /**
     *
     */
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

    public LinkedList getTeilnehmer() {
        return Teilnehmer;
    }

    public void setTeilnehmer(LinkedList Teilnehmer) {
        this.Teilnehmer = Teilnehmer;
    }

   
     public void addTeilnehmer(String t) {
         this.Teilnehmer.add(t);
     }
public void removeTeilnehmer(String t) {
         this.Teilnehmer.remove(t);
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

    /**
     *
     * @param s_date
     * @param e_date
     * @param title
     * @param beschreibung
     * @param ort
     * @param imgpath
     * @param doko
     */
    public Termin(String s_date, String e_date, String title, String beschreibung, String ort, String imgpath, Dokumentation doko) {
        this.s_date = s_date;
        this.e_date = e_date;
        this.title = title;
        this.beschreibung = beschreibung;
        this.ort = ort;
        this.imgpath = imgpath;
        this.doko = doko;
    }

    /**
     *
     * @return
     */
    public int getId() {
        return id;
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
    public String getS_date() {
        return s_date;
    }

    /**
     *
     * @param s_date
     */
    public void setS_date(String s_date) {
        this.s_date = s_date;
    }

    /**
     *
     * @return
     */
    public String getE_date() {
        return e_date;
    }

    /**
     *
     * @param e_date
     */
    public void setE_date(String e_date) {
        this.e_date = e_date;
    }

    /**
     *
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return
     */
    public String getOrt() {
        return ort;
    }

    /**
     *
     * @param ort
     */
    public void setOrt(String ort) {
        this.ort = ort;
    }

    /**
     *
     * @return
     */
    public String getBeschreibung() {
        return beschreibung;
    }

    /**
     *
     * @param beschreibung
     */
    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    /**
     *
     * @return
     */
    public Dokumentation getDoko() {
        return doko;
    }

    /**
     *
     * @param doko
     */
    public void setDoko(Dokumentation doko) {
        this.doko = doko;
    }

    /**
     * 
     * @return 
     */
    public String getImgpath() {
        return imgpath;
    }

    /**
     * 
     * @param imgpath 
     */
    public void setImgpath(String imgpath) {
        this.imgpath = imgpath;
    }

}
