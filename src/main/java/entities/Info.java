package entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Contains information for a Post on the Newsfeed
 *
 * @author isi
 */
@Entity
public class Info {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    private String ueberschrift;
    private String beschreibung;
    private String[] mediapath;
    private String datum;

    /**
     *
     */
    public Info() {
    }

    /**
     *
     * @param ueberschrift
     * @param beschreibung
     * @param mediapath
     * @param datum
     */

    public Info(String ueberschrift, String beschreibung, String[] mediapath, String datum) {
        this.ueberschrift = ueberschrift;
        this.beschreibung = beschreibung;
        this.mediapath = mediapath;
        this.datum = datum;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
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
    public String getUeberschrift() {
        return ueberschrift;
    }

    /**
     *
     * @param ueberschrift
     */
    public void setUeberschrift(String ueberschrift) {
        this.ueberschrift = ueberschrift;
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

    public String[] getMediapath() {
        return mediapath;
    }

    public void setMediapath(String[] mediapath) {
        this.mediapath = mediapath;
    }

}
