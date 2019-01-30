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
 * Preparation for a Group Activity, contains WYIWYG Text Field String to
 * prepare.
 *
 * @author isi
 */
@Entity
public class Planning implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    //Content
    private String plannung;
    //is the planning shared with others or private?
    private boolean share = false;

    /**
     * Default Constructor
     */
    public Planning() {
    }

    /**
     * Constructor which leaves shared on false
     *
     * @param plannung
     */
    public Planning(String plannung) {
        this.plannung = plannung;
    }

    /**
     * Constructor
     *
     * @param plannung
     * @param share
     */
    public Planning(String plannung, boolean share) {
        this.plannung = plannung;
        this.share = share;
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
    public String getPlannung() {
        return plannung;
    }

    /**
     * Setter
     *
     * @param plannung
     */
    public void setPlannung(String plannung) {
        this.plannung = plannung;
    }

    /**
     * is shared?
     *
     * @return
     */
    public boolean isShare() {
        return share;
    }

    /**
     * setter
     *
     * @param share
     */
    public void setShare(boolean share) {
        this.share = share;
    }

}
