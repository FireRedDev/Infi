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
 * Bezirksstelle
 *
 * @author Christopher G
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "JRKEntitaet.listAll", query = "SELECT j FROM JRKEntitaet j")
    ,
    @NamedQuery(name = "JRKEntitaet.layerDown", query = "SELECT j FROM JRKEntitaet j where j.jrkentitaet=:jrkentitaet")
    ,
    @NamedQuery(name = "JRKEntitaet.layerUp", query = "SELECT j FROM JRKEntitaet j where j.jrkentitaet1=:jrkentitaet")
})
public class JRKEntitaet implements Serializable {

    @Id
    int id;
    private String name;
    private String ort;
    private JRKEntitaetType typ;
    //each JRKENTITÄT has its own list of Termine and info
    @CascadeOnDelete
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Termin> termine = new LinkedList<Termin>();
    @CascadeOnDelete
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Info> info = new LinkedList<Info>();

    //übergeordnet
    @ManyToOne
    private JRKEntitaet jrkentitaet;

    //untergeordnet
    @OneToMany(mappedBy = "jrkentitaet")
    private List<JRKEntitaet> jrkentitaet1;

    /**
     *
     */
    public JRKEntitaet() {

    }

    /**
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
        this.jrkentitaet = jrkentitaet;
    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
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
     * @return
     */
    public JRKEntitaetType getTyp() {
        return typ;
    }

    /**
     *
     * @param typ
     */
    public void setTyp(JRKEntitaetType typ) {
        this.typ = typ;
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
    public List<Termin> getTermine() {
        return termine;
    }

    /**
     *
     * @param termine
     */
    public void setTermine(List<Termin> termine) {
        this.termine = termine;
    }

    /**
     *
     * @return
     */
    public List<Info> getInfo() {
        return info;
    }

    /**
     *
     * @param info
     */
    public void setInfo(List<Info> info) {
        this.info = info;
    }

    /**
     *
     * @return
     */
    public JRKEntitaet getJrkentitaet() {
        return jrkentitaet;
    }

    /**
     *
     * @param jrkentitaet
     */
    public void setJrkentitaet(JRKEntitaet jrkentitaet) {
        this.jrkentitaet = jrkentitaet;
    }

    /**
     *
     * @return
     */
    public List<JRKEntitaet> getJrkentitaet1() {
        return jrkentitaet1;
    }

    /**
     *
     * @param jrkentitaet1
     */
    public void setJrkentitaet1(List<JRKEntitaet> jrkentitaet1) {
        this.jrkentitaet1 = jrkentitaet1;
    }

    /**
     *
     * @param newJRK
     */
    public void addJRKEntitaet(JRKEntitaet newJRK) {
        if (!this.jrkentitaet1.contains(newJRK)) {
            this.jrkentitaet1.add(newJRK);
            newJRK.setJrkentitaet(this);
        }
    }

    /**
     *
     * @param old
     */
    public void removeJRKEntitaet(JRKEntitaet old) {
        if (this.jrkentitaet1.contains(old)) {
            this.jrkentitaet1.remove(old);
            old.setJrkentitaet(this);
        }
    }

    /**
     *
     * @param termin
     */
    public void addTermin(Termin termin) {
        termine.add(termin);
    }

    /**
     *
     * @param termin
     */
    public void removeTermin(Termin termin) {
        termine.remove(termin);
    }

    /**
     *
     * @param info
     */
    public void addInfo(Info info) {
        this.info.add(info);
    }

    /**
     *
     * @param info
     */
    public void removeInfo(Info info) {
        this.info.remove(info);
    }
}
