/**
 * Appointment, has an optional Documentation class(to document it) and/or an optional Planning(to prepare for it) attached.
 * An Appointment has a Start and End Date, a title, a describtion, an optional thumbnail and people that are planned to be there.
 *
 */
package entities;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@NamedQueries({
    /**
     * Returns all Appointments
     */
    @NamedQuery(name = "Termin.listAll", query = "SELECT t FROM Termin t")
    ,
    /**
     * Returns not yet made documentations
     */
    @NamedQuery(name = "Termin.getOpenDoko", query = "SELECT t FROM Termin t where t.doko IS NULL")})
/**
 * Appointment, has an optional Documentation class(to document it) and/or an
 * optional Planning(to prepare for it) attached. An Appointment has a Start and
 * End Date, a title, a location, a describtion, an optional thumbnail and
 * people that are planned to be there.
 *
 * @author Christopher G
 */
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
    private String teilnehmer = "";

    @OneToOne(cascade = CascadeType.ALL,orphanRemoval = true)
    private Dokumentation doko;
<<<<<<< src/main/java/entities/Termin.java

    @OneToOne
=======
    
    @OneToOne(cascade = CascadeType.ALL,orphanRemoval = true)
>>>>>>> src/main/java/entities/Termin.java
    private Planning planning;

    /**
     * Default Constructor
     */
    public Termin() {
    }

    /**
     * Constructor
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
     * Construktor
     *
     * @param s_date
     * @param e_date
     * @param title
     * @param beschreibung
     * @param ort
     * @param teilnehmer
     */
    public Termin(String s_date, String e_date, String title, String beschreibung, String ort, String teilnehmer) {
        this.s_date = s_date;
        this.e_date = e_date;
        this.title = title;
        this.beschreibung = beschreibung;
        this.ort = ort;
        this.teilnehmer = teilnehmer;
    }

    /**
     * Constructor
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
     * Constructor
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
     * Getter
     *
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * Setter
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter
     *
     * @return
     */
    public String getS_date() {
        return s_date;
    }

    /**
     * Setter
     *
     * @param s_date
     */
    public void setS_date(String s_date) {
        this.s_date = s_date;
    }

    /**
     * Getter
     *
     * @return
     */
    public String getE_date() {
        return e_date;
    }

    /**
     * Setter
     *
     * @param e_date
     */
    public void setE_date(String e_date) {
        this.e_date = e_date;
    }

    /**
     * Getter
     *
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     * Setter
     *
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Getter
     *
     * @return
     */
    public String getOrt() {
        return ort;
    }

    /**
     * Setter
     *
     * @param ort
     */
    public void setOrt(String ort) {
        this.ort = ort;
    }

    /**
     * Getter
     *
     * @return
     */
    public String getBeschreibung() {
        return beschreibung;
    }

    /**
     * Setter
     *
     * @param beschreibung
     */
    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    /**
     * Getter
     *
     * @return
     */
    public Dokumentation getDoko() {
        return doko;
    }

    /**
     * Setter
     *
     * @param doko
     */
    public void setDoko(Dokumentation doko) {
        this.doko = doko;
    }

    /**
     * Getter
     *
     * @return
     */
    public String getImgpath() {
        return imgpath;
    }

    /**
     * Setter
     *
     * @param imgpath
     */
    public void setImgpath(String imgpath) {
        this.imgpath = imgpath;
    }

    /**
     * Getter
     *
     * @return
     */
    public String getTeilnehmer() {
        return teilnehmer;
    }

    /**
     * Setter
     *
     * @param teilnehmer
     */
    public void setTeilnehmer(String teilnehmer) {
        this.teilnehmer = teilnehmer;
    }

    /**
     * Add participating Person to list
     *
     * @param t
     */
    public void addTeilnehmer(String t) {
        this.teilnehmer = this.teilnehmer + t + ";";
    }

    /**
     * Remove participating Person to list
     *
     * @param t
     */
    public void removeTeilnehmer(String t) {
        this.teilnehmer = this.teilnehmer.replace(t, "");
    }

    /**
     * Getter
     *
     * @return
     */
    public Planning getPlanning() {
        return planning;
    }

    /**
     * Setter
     *
     * @param planning
     */
    public void setPlanning(Planning planning) {
        this.planning = planning;
    }

}
