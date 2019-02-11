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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Aiglu
 */
@Entity
@Table(name = "PMPK_REKOMEND")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PmpkRekomend.findAll", query = "SELECT p FROM PmpkRekomend p"),
    @NamedQuery(name = "PmpkRekomend.findByPmpkrekomendId", query = "SELECT p FROM PmpkRekomend p WHERE p.pmpkrekomendId = :pmpkrekomendId"),
    @NamedQuery(name = "PmpkRekomend.findByPmpkId", query = "SELECT p FROM PmpkRekomend p WHERE p.pmpkId = :pmpkId")})
public class PmpkRekomend implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "PMPKREKOMEND_ID")
    @SequenceGenerator(name = "SEQ_PMPK_REKOMEND", sequenceName = "SEQ_PMPK_REKOMEND", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SEQ_PMPK_REKOMEND")
    private Integer pmpkrekomendId;
    @JoinColumn(name = "PMPK_ID", referencedColumnName = "PMPK_ID")
    @ManyToOne
    private Pmpk pmpkId;
    @Column(name = "PMPKREKOMEND_INDPROF")
    private Integer pmpkrekomendIndprof;

    public Integer getPmpkrekomendId() {
        return pmpkrekomendId;
    }

    public void setPmpkrekomendId(Integer pmpkrekomendId) {
        this.pmpkrekomendId = pmpkrekomendId;
    }
    
    public Pmpk getPmpkId() {
        return pmpkId;
    }

    public void setPmpkId(Pmpk pmpkId) {
        this.pmpkId = pmpkId;
    }
    
    public Integer getPmpkrekomendIndprof() {
        return pmpkrekomendIndprof;
    }

    public void setPmpkrekomendIndprof(Integer pmpkrekomendIndprof) {
        this.pmpkrekomendIndprof = pmpkrekomendIndprof;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pmpkrekomendId != null ? pmpkrekomendId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PmpkRekomend)) {
            return false;
        }
        PmpkRekomend other = (PmpkRekomend) object;
        if ((this.pmpkrekomendId == null && other.pmpkrekomendId != null) 
                || (this.pmpkrekomendId != null && !this.pmpkrekomendId.equals(other.pmpkrekomendId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.PmpkRekomend[ pmpkrekomendId=" + pmpkrekomendId + " ]";
    }
    
}
