package entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author isi
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Termin.listAll", query = "SELECT t FROM Termin t"),
    @NamedQuery(name = "Termin.listBenutzer", query = "SELECT t FROM Termin t where t.jrkEntitaet = :jrkentitaet"),
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
    @ManyToOne(fetch = FetchType.LAZY)
    private JRKEntitaet jrkEntitaet;
    @OneToOne
    private Dokumentation doko;

    public Termin() {
    }

    public Termin(String s_date, String e_date, String title, String beschreibung, String ort, JRKEntitaet jrkEntitaet) {
        this.s_date = s_date;
        this.e_date = e_date;
        this.title = title;
        this.beschreibung = beschreibung;
        this.ort = ort;
        this.jrkEntitaet = jrkEntitaet;
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

    public JRKEntitaet getJrkEntitaet() {
        return jrkEntitaet;
    }

    public void setJrkEntitaet(JRKEntitaet jrkEntitaet) {
        this.jrkEntitaet = jrkEntitaet;
    }

    public Dokumentation getDoko() {
        return doko;
    }

    public void setDoko(Dokumentation doko) {
        this.doko = doko;
    }

}
