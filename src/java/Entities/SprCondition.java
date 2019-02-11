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
@Table(name = "SPR_CONDITION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SprCondition.findAll", query = "SELECT s FROM SprCondition s"),
    @NamedQuery(name = "SprCondition.findBySprconditionId", query = "SELECT s FROM SprCondition s WHERE s.sprconditionId = :sprconditionId"),
    @NamedQuery(name = "SprCondition.findBySprconditionName", query = "SELECT s FROM SprCondition s WHERE s.sprconditionName = :sprconditionName")})
public class SprCondition implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SPRCONDITION_ID")
    @SequenceGenerator(name = "SEQ_SPR_CONDITION", sequenceName = "SEQ_SPR_CONDITION", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.IDENTITY, generator="SEQ_SPR_CONDITION")
    private Integer sprconditionId;
    @Size(max = 500)
    @Column(name = "SPRCONDITION_NAME")
    private String sprconditionName;

    public Integer getSprconditionId() {
        return sprconditionId;
    }

    public void setSprconditionId(Integer sprconditionId) {
        this.sprconditionId = sprconditionId;
    }
    
    public String getSprconditionName() {
        return sprconditionName;
    }

    public void setSprconditionName(String sprconditionName) {
        this.sprconditionName = sprconditionName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sprconditionId != null ? sprconditionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SprCondition)) {
            return false;
        }
        SprCondition other = (SprCondition) object;
        if ((this.sprconditionId == null && other.sprconditionId != null) || (this.sprconditionId != null && !this.sprconditionId.equals(other.sprconditionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.SprCondition[ sprconditionId=" + sprconditionId + " ]";
    }
    
}
