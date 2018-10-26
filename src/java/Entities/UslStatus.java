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
@Table(name = "USL_STATUS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UslStatus.findAll", query = "SELECT u FROM UslStatus u"),
    @NamedQuery(name = "UslStatus.findByUslstatusId", query = "SELECT u FROM UslStatus u WHERE u.uslstatusId = :uslstatusId"),
    @NamedQuery(name = "UslStatus.findBySprstatId", query = "SELECT u FROM UslStatus u WHERE u.sprstatId = :sprstatId"),
    @NamedQuery(name = "UslStatus.findBySpruslId", query = "SELECT u FROM UslStatus u WHERE u.spruslId = :spruslId"),
    @NamedQuery(name = "UslStatus.findBySpruslIdAndSprstatId", query = 
            "SELECT u FROM UslStatus u WHERE u.spruslId = :spruslId AND u.sprstatId = :sprstatId")})
public class UslStatus implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "USLSTATUS_ID")
    @SequenceGenerator(name = "SEQ_USL_STATUS", sequenceName = "SEQ_USL_STATUS", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SEQ_USL_STATUS")
    private Integer uslstatusId;
    @JoinColumn(name = "SPRSTAT_ID", referencedColumnName = "SPRSTAT_ID")
    @ManyToOne
    private SprStat sprstatId;
    @JoinColumn(name = "SPRUSL_ID", referencedColumnName = "SPRUSL_ID")
    @ManyToOne
    private SprUsl spruslId;
    @Column(name = "USLSTATUS_ENABLED")
    private Integer uslstatusEnabled;

    public Integer getUslstatusId() {
        return uslstatusId;
    }

    public void setUslstatusId(Integer uslstatusId) {
        this.uslstatusId = uslstatusId;
    }

    public SprStat getSprstatId() {
        return sprstatId;
    }

    public void setSprstatId(SprStat sprstatId) {
        this.sprstatId = sprstatId;
    }

    public SprUsl getSpruslId() {
        return spruslId;
    }

    public void setSpruslId(SprUsl spruslId) {
        this.spruslId = spruslId;
    }
    
    public Integer getUslstatusEnabled() {
        return uslstatusEnabled;
    }

    public void setUslstatusEnabled(Integer uslstatusEnabled) {
        this.uslstatusEnabled = uslstatusEnabled;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (uslstatusId != null ? uslstatusId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UslStatus)) {
            return false;
        }
        UslStatus other = (UslStatus) object;
        if ((this.uslstatusId == null && other.uslstatusId != null) || (this.uslstatusId != null && !this.uslstatusId.equals(other.uslstatusId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.UslStatus[ id=" + uslstatusId + " ]";
    }

}
