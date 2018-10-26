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
@Table(name = "MONIT_EQUIP")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MonitEquip.findAll", query = "SELECT m FROM MonitEquip m"),
    @NamedQuery(name = "MonitEquip.findByMonitequipId", query = "SELECT m FROM MonitEquip m WHERE m.monitequipId = :monitequipId")})
public class MonitEquip implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "MONITEQUIP_ID")    
    @SequenceGenerator(name = "SEQ_MONIT_EQUIP", sequenceName = "SEQ_MONIT_EQUIP", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.IDENTITY, generator="SEQ_MONIT_EQUIP")
    private Integer monitequipId;
    @JoinColumn(name = "MONITDATA_ID", referencedColumnName = "MONITDATA_ID")
    @ManyToOne
    private MonitoringData monitdataId;
    @JoinColumn(name = "SPREQUIP_ID", referencedColumnName = "SPREQUIP_ID")
    @ManyToOne
    private SprEquip sprequipId;

    public MonitEquip() {
    }

    public MonitEquip(Integer monitequipId) {
        this.monitequipId = monitequipId;
    }

    public Integer getMonitequipId() {
        return monitequipId;
    }

    public void setMonitequipId(Integer monitequipId) {
        this.monitequipId = monitequipId;
    }
    
    public MonitoringData getMonitdataId() {
        return monitdataId;
    }

    public void setMonitdataId(MonitoringData monitdataId) {
        this.monitdataId = monitdataId;
    }

    public SprEquip getSprequipId() {
        return sprequipId;
    }

    public void setSprequipId(SprEquip sprequipId) {
        this.sprequipId = sprequipId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (monitequipId != null ? monitequipId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MonitEquip)) {
            return false;
        }
        MonitEquip other = (MonitEquip) object;
        if ((this.monitequipId == null && other.monitequipId != null) || (this.monitequipId != null && !this.monitequipId.equals(other.monitequipId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.MonitEquip[ monitequipId=" + monitequipId + " ]";
    }
    
}
