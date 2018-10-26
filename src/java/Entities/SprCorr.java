/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Aiglu
 */
@Entity
@Table(name = "SPR_CORR")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SprCorr.findAll", query = "SELECT s FROM SprCorr s"),
    @NamedQuery(name = "SprCorr.findBySprcorrId", query = "SELECT s FROM SprCorr s WHERE s.sprcorrId = :sprcorrId")})

public class SprCorr implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SPRCORR_ID")
    @SequenceGenerator(name = "SEQ_SPR_CORR", sequenceName = "SEQ_SPR_CORR", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SEQ_SPR_CORR")
    private Integer sprcorrId;
    @Size(max = 50)
    @Column(name = "SPRCORR_TABLE")
    private String sprcorrTable;
    @Column(name = "SPRCORR_CORR_ID")
    private Integer sprcorrCorrId;
    @Size(max = 500)
    @Column(name = "SPRCORR_NAME")
    private String sprcorrName;

    public Integer getSprcorrId() {
        return sprcorrId;
    }

    public void setSprcorrId(Integer sprcorrId) {
        this.sprcorrId = sprcorrId;
    }
    
    public String getSprcorrTable() {
        return sprcorrTable;
    }

    public void setSprcorrTable(String sprcorrTable) {
        this.sprcorrTable = sprcorrTable;
    }
    
    public Integer getSprcorrCorrId() {
        return sprcorrCorrId;
    }

    public void setSprcorrCorrId(Integer sprcorrCorrId) {
        this.sprcorrCorrId = sprcorrCorrId;
    }    
    
    public String getSprcorrName() {
        return sprcorrName;
    }

    public void setSprcorrName(String sprcorrName) {
        this.sprcorrName = sprcorrName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sprcorrId != null ? sprcorrId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SprCorr)) {
            return false;
        }
        SprCorr other = (SprCorr) object;
        if ((this.sprcorrId == null && other.sprcorrId != null) || (this.sprcorrId != null && !this.sprcorrId.equals(other.sprcorrId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.SprCorr[ sprcorrId=" + sprcorrId + " ]";
    }
    
}
