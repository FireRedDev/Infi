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

    /**
     *
     */
    public Info() {
    }

    /**
     *
     * @param ueberschrift
     * @param beschreibung
     * @param imgpath
     */

    public Info(String ueberschrift, String beschreibung, String[] mediapath) {
        this.ueberschrift = ueberschrift;
        this.beschreibung = beschreibung;
        this.mediapath = mediapath;
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
