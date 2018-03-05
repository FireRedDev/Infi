/*
 * JRKEnitaet
 */
package entities;

import java.io.Serializable;
import java.util.*;
import javax.persistence.*;

/**
 * An JRKENTITY is an organisational unit/team, like a Jugengruppe or a
 * Bezirksleitstelle
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
public class OrganisationalEntity implements Serializable {

    @Id
    int id;
    private String name;
    private String ort;
    private OrganisationEntityType typ;
    //each JRKENTITÄT has its own list of Termine and info
    @OneToMany
    private List<Termin> termine = new LinkedList<Termin>();
    @OneToMany
    private List<Info> info = new LinkedList<Info>();

    //übergeordnet
    @ManyToOne
    private OrganisationalEntity jrkentitaet;

    //untergeordnet
    @OneToMany(mappedBy = "jrkentitaet")
    private List<OrganisationalEntity> jrkentitaet1;

    /**
     *
     */
    public OrganisationalEntity() {

    }

    /**
     *
     * @param id
     * @param name
     * @param typ
     * @param jrkentitaet
     */
    public OrganisationalEntity(int id, String name, OrganisationEntityType typ, OrganisationalEntity jrkentitaet) {
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
    public OrganisationEntityType getTyp() {
        return typ;
    }

    /**
     *
     * @param typ
     */
    public void setTyp(OrganisationEntityType typ) {
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
    public OrganisationalEntity getJrkentitaet() {
        return jrkentitaet;
    }

    /**
     *
     * @param jrkentitaet
     */
    public void setJrkentitaet(OrganisationalEntity jrkentitaet) {
        this.jrkentitaet = jrkentitaet;
    }

    /**
     *
     * @return
     */
    public List<OrganisationalEntity> getJrkentitaet1() {
        return jrkentitaet1;
    }

    /**
     *
     * @param jrkentitaet1
     */
    public void setJrkentitaet1(List<OrganisationalEntity> jrkentitaet1) {
        this.jrkentitaet1 = jrkentitaet1;
    }

    /**
     *
     * @param newJRK
     */
    public void addJRKEntitaet(OrganisationalEntity newJRK) {
        if (!this.jrkentitaet1.contains(newJRK)) {
            this.jrkentitaet1.add(newJRK);
            newJRK.setJrkentitaet(this);
        }
    }

    /**
     *
     * @param old
     */
    public void removeJRKEntitaet(OrganisationalEntity old) {
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
     * @param info
     */
    public void addInfo(Info info) {
        this.info.add(info);
    }
}
