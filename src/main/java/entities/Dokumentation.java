/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
* C.G
 */
@Entity
public class Dokumentation implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String externePersonen;
    private String Tätigkeiten;
    private Kategorie kategorie;
    //beziehung zu kindern
    @OneToMany()
    private List<Person> teilnehmer;
    public Long getId() {
        return id;
    }
public Dokumentation() {
    
}
    public Dokumentation(String externePersonen, String Tätigkeiten, Kategorie kategorie, List<Person> teilnehmer) {
        this.externePersonen = externePersonen;
        this.Tätigkeiten = Tätigkeiten;
        this.kategorie = kategorie;
        this.teilnehmer = teilnehmer;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Dokumentation)) {
            return false;
        }
        Dokumentation other = (Dokumentation) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    public String getExternePersonen() {
        return externePersonen;
    }

    public void setExternePersonen(String externePersonen) {
        this.externePersonen = externePersonen;
    }

    public String getTätigkeiten() {
        return Tätigkeiten;
    }

    public void setTätigkeiten(String Tätigkeiten) {
        this.Tätigkeiten = Tätigkeiten;
    }

    public Kategorie getKategorie() {
        return kategorie;
    }

    public void setKategorie(Kategorie kategorie) {
        this.kategorie = kategorie;
    }

    public List<Person> getTeilnehmer() {
        return teilnehmer;
    }

    public void setTeilnehmer(List<Person> teilnehmer) {
        this.teilnehmer = teilnehmer;
    }

    @Override
    public String toString() {
        return "entities.Dokumentation[ id=" + id + " ]";
    }

}
