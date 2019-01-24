package entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * Contains information for a Post on the Newsfeed.
 * A Post has a heading, content, a publishing date and a thumbnail/a gallery
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
    //array of urls to images used in info blog posts
    private String[] mediapath;
    private String datum;
    /**
     * Default Constructor
     */
    public Info() {
    }

    /**
     * Constructor
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

    /** 
     * Getter
     * @return  
     */
    public String getDatum() {
        return datum;
    }

    /**
     * Setter
     * @param datum 
     */
    public void setDatum(String datum) {
        this.datum = datum;
    }

    /**
     * Getter
     * @return
     */
    public int getId() {
        return id;
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
    public String getUeberschrift() {
        return ueberschrift;
    }

    /**
     * Setter
     * @param ueberschrift
     */
    public void setUeberschrift(String ueberschrift) {
        this.ueberschrift = ueberschrift;
    }

    /**
     * Getter
     * @return
     */
    public String getBeschreibung() {
        return beschreibung;
    }

    /**
     * Setter
     * @param beschreibung
     */
    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    /**
     * Getter
     * @return 
     */
    public String[] getMediapath() {
        return mediapath;
    }

    /**
     * Setter
     * @param mediapath 
     */
    public void setMediapath(String[] mediapath) {
        this.mediapath = mediapath;
    }

}
