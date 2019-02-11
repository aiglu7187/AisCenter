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
@Table(name = "PMPKREK_CONDITION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PmpkrekCondition.findAll", query = "SELECT p FROM PmpkrekCondition p"),
    @NamedQuery(name = "PmpkrekCondition.findByPmpkrekconditionId", query = "SELECT p FROM PmpkrekCondition p WHERE p.pmpkrekconditionId = :pmpkrekconditionId"),
    @NamedQuery(name = "PmpkrekCondition.findByPmpkId", query = "SELECT p FROM PmpkrekCondition p WHERE p.pmpkId = :pmpkId")})
public class PmpkrekCondition implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "PMPKREKCONDITION_ID")
    @SequenceGenerator(name = "SEQ_PMPKREK_CONDITION", sequenceName = "SEQ_PMPKREK_CONDITION", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SEQ_PMPKREK_CONDITION")
    private Integer pmpkrekconditionId;
    @JoinColumn(name = "PMPK_ID", referencedColumnName = "PMPK_ID")
    @ManyToOne
    private Pmpk pmpkId;
    @JoinColumn(name = "SPRCONDITION_ID", referencedColumnName = "SPRCONDITION_ID")
    @ManyToOne
    private SprCondition sprconditionId;

    public Integer getPmpkrekconditionId() {
        return pmpkrekconditionId;
    }

    public void setPmpkrekconditionId(Integer pmpkrekconditionId) {
        this.pmpkrekconditionId = pmpkrekconditionId;
    }
    
    public Pmpk getPmpkId() {
        return pmpkId;
    }

    public void setPmpkId(Pmpk pmpkId) {
        this.pmpkId = pmpkId;
    }
    
    public SprCondition getSprconditionId() {
        return sprconditionId;
    }

    public void setSprconditionId(SprCondition sprconditionId) {
        this.sprconditionId = sprconditionId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pmpkrekconditionId != null ? pmpkrekconditionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PmpkrekCondition)) {
            return false;
        }
        PmpkrekCondition other = (PmpkrekCondition) object;
        if ((this.pmpkrekconditionId == null && other.pmpkrekconditionId != null) 
                || (this.pmpkrekconditionId != null && !this.pmpkrekconditionId.equals(other.pmpkrekconditionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.PmpkrekCondition[ pmpkrekconditionId=" + pmpkrekconditionId + " ]";
    }
    
}
