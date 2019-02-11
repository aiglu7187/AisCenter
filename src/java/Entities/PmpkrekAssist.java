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
@Table(name = "PMPKREK_ASSIST")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PmpkrekAssist.findAll", query = "SELECT p FROM PmpkrekAssist p"),
    @NamedQuery(name = "PmpkrekAssist.findByPmpkrekassistId", query = "SELECT p FROM PmpkrekAssist p WHERE p.pmpkrekassistId = :pmpkrekassistId"),
    @NamedQuery(name = "PmpkrekAssist.findByPmpkId", query = "SELECT p FROM PmpkrekAssist p WHERE p.pmpkId = :pmpkId")})
public class PmpkrekAssist implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "PMPKREKASSIST_ID")
    @SequenceGenerator(name = "SEQ_PMPKREK_ASSIST", sequenceName = "SEQ_PMPKREK_ASSIST", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SEQ_PMPKREK_ASSIST")
    private Integer pmpkrekassistId;
    @JoinColumn(name = "PMPK_ID", referencedColumnName = "PMPK_ID")
    @ManyToOne
    private Pmpk pmpkId;
    @JoinColumn(name = "SPRASSIST_ID", referencedColumnName = "SPRASSIST_ID")
    @ManyToOne
    private SprAssist sprassistId;

    public Integer getPmpkrekassistId() {
        return pmpkrekassistId;
    }

    public void setPmpkrekassistId(Integer pmpkrekassistId) {
        this.pmpkrekassistId = pmpkrekassistId;
    }
    
    public Pmpk getPmpkId() {
        return pmpkId;
    }

    public void setPmpkId(Pmpk pmpkId) {
        this.pmpkId = pmpkId;
    }
    
    public SprAssist getSprassistId() {
        return sprassistId;
    }

    public void setSprassistId(SprAssist sprassistId) {
        this.sprassistId = sprassistId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pmpkrekassistId != null ? pmpkrekassistId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PmpkrekAssist)) {
            return false;
        }
        PmpkrekAssist other = (PmpkrekAssist) object;
        if ((this.pmpkrekassistId == null && other.pmpkrekassistId != null) 
                || (this.pmpkrekassistId != null && !this.pmpkrekassistId.equals(other.pmpkrekassistId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.PmpkrekAssist[ pmpkrekassistId=" + pmpkrekassistId + " ]";
    }
    
}
