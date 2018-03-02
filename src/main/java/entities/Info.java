/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
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
    private String imgpath;

    public Info() {
    }

    public Info(String ueberschrift, String beschreibung, String imgpath) {
        this.ueberschrift = ueberschrift;
        this.beschreibung = beschreibung;
        this.imgpath = imgpath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUeberschrift() {
        return ueberschrift;
    }

    public void setUeberschrift(String ueberschrift) {
        this.ueberschrift = ueberschrift;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public String getImgpath() {
        return imgpath;
    }

    public void setImgpath(String imgpath) {
        this.imgpath = imgpath;
    }

}
