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
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Aiglu
 */
@Entity
@Table(name = "SPR_INICIATOR")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SprIniciator.findAll", query = "SELECT s FROM SprIniciator s"),
    @NamedQuery(name = "SprIniciator.findBySpriniciatorId", query = "SELECT s FROM SprIniciator s WHERE s.spriniciatorId = :spriniciatorId"),
    @NamedQuery(name = "SprIniciator.findBySpriniciatorName", query = "SELECT s FROM SprIniciator s WHERE s.spriniciatorName = :spriniciatorName"),
    @NamedQuery(name = "SprIniciator.findBySpriniciatorOth", query = "SELECT s FROM SprIniciator s WHERE s.spriniciatorOth = :spriniciatorOth")})
public class SprIniciator implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SPRINICIATOR_ID")
    private Integer spriniciatorId;
    @Size(max = 300)
    @Column(name = "SPRINICIATOR_NAME")
    private String spriniciatorName; 
    @Column(name = "SPRINICIATOR_OTH")
    private Integer spriniciatorOth;

    public Integer getSpriniciatorId() {
        return spriniciatorId;
    }

    public void setSpriniciatorId(Integer spriniciatorId) {
        this.spriniciatorId = spriniciatorId;
    }
    
    public Integer getSpriniciatorOth() {
        return spriniciatorOth;
    }

    public void setSpriniciatorOth(Integer spriniciatorOth) {
        this.spriniciatorOth = spriniciatorOth;
    }
    
    public String getSpriniciatorName() {
        return spriniciatorName;
    }

    public void setSpriniciatorName(String spriniciatorName) {
        this.spriniciatorName = spriniciatorName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (spriniciatorId != null ? spriniciatorId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SprIniciator)) {
            return false;
        }
        SprIniciator other = (SprIniciator) object;
        if ((this.spriniciatorId == null && other.spriniciatorId != null) || 
                (this.spriniciatorId != null && !this.spriniciatorId.equals(other.spriniciatorId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.SprIniciator[ spriniciatorId=" + spriniciatorId + " ]";
    }
    
}
