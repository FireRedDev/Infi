/*
 * JRKEnitaet
 */
package entities;

import java.io.Serializable;
import java.util.*;
import javax.persistence.*;
import org.eclipse.persistence.annotations.CascadeOnDelete;

/**
 * An JRKENTITY is an organisational unit/team, like a Jugendgruppe or a
 * Bezirksstelle. As it corresponds to an actual group, it has a location
 * property. Each JRKEntitaet has a list of its (personal) appointments and news
 * articles. Each also has a relationship to its higher and lower ranking entity
 *
 * 
 */
@Entity
@NamedQueries({
    //returns all JRKEntitaets
    @NamedQuery(name = "JRKEntitaet.listAll", query = "SELECT j FROM JRKEntitaet j")
    ,
    //returns an entitys lower ranking entity
    @NamedQuery(name = "JRKEntitaet.layerDown", query = "SELECT j FROM JRKEntitaet j where j.superordinateJRKEntitaet=:jrkentitaet")
    ,
    //returns an entitys higher ranking entity
    @NamedQuery(name = "JRKEntitaet.layerUp", query = "SELECT j.superordinateJRKEntitaet FROM JRKEntitaet j where j=:jrkentitaet")
})
public class JRKEntitaet implements Serializable {

    @Id
    int id;
    private String name;
    private String ort;
    private JRKEntitaetType typ;
    //each JRKENTITÄT has its own list of Termine and info
    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true)
    //group appointments
    private List<Termin> termine = new LinkedList<Termin>();
    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true)
    //group news
    private List<Info> info = new LinkedList<Info>();

    //übergeordnet
    @ManyToOne
    private JRKEntitaet superordinateJRKEntitaet;

    /**
     * Default Constructor
     */
    public JRKEntitaet() {

    }

    /**
     * Constructor
     *
     * @param id
     * @param name
     * @param typ
     * @param jrkentitaet
     */
    public JRKEntitaet(int id, String name, JRKEntitaetType typ, JRKEntitaet jrkentitaet) {
        this.id = id;
        this.name = name;
        this.typ = typ;
        this.superordinateJRKEntitaet = jrkentitaet;
    }

    /**
     * Getter
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Setter
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
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
     * Getter
     *
     * @return
     */
    public JRKEntitaetType getTyp() {
        return typ;
    }

    /**
     * Setter
     *
     * @param typ
     */
    public void setTyp(JRKEntitaetType typ) {
        this.typ = typ;
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
    public List<Termin> getTermine() {
        return termine;
    }

    /**
     * Setter
     *
     * @param termine
     */
    public void setTermine(List<Termin> termine) {
        this.termine = termine;
    }

    /**
     * Getter
     *
     * @return
     */
    public List<Info> getInfo() {
        return info;
    }

    /**
     * Setter
     *
     * @param info
     */
    public void setInfo(List<Info> info) {
        this.info = info;
    }

    /**
     * Getter
     *
     * @return
     */
    public JRKEntitaet getSuperordinateJRKEntitaet() {
        return superordinateJRKEntitaet;
    }

    /**
     * Setter
     *
     * @param superordinateJRKEntitaet
     */
    public void setSuperordinateJRKEntitaet(JRKEntitaet superordinateJRKEntitaet) {
        this.superordinateJRKEntitaet = superordinateJRKEntitaet;
    }

    /**
     * Adds an appointment
     *
     * @param termin
     */
    public void addTermin(Termin termin) {
        termine.add(termin);
    }

    /**
     * Removes an Appointment
     *
     * @param termin
     */
    public void removeTermin(Termin termin) {
        termine.remove(termin);
    }

    /**
     * Adds Blog Post
     *
     * @param info
     */
    public void addInfo(Info info) {
        this.info.add(info);
    }

    /**
     * Removes Blog Post
     *
     * @param info
     */
    public void removeInfo(Info info) {
        this.info.remove(info);
    }
}
