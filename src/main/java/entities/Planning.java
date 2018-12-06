/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author isi
 */
@Entity
public class Planning implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String plannung;
    private boolean share = false;

    public Planning() {
    }

    public Planning(String plannung) {
        this.plannung = plannung;
    }

    public Planning(String plannung, boolean share) {
        this.plannung = plannung;
        this.share = share;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlannung() {
        return plannung;
    }

    public void setPlannung(String plannung) {
        this.plannung = plannung;
    }

    public boolean isShare() {
        return share;
    }

    public void setShare(boolean share) {
        this.share = share;
    }

}
