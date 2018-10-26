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
@Table(name = "MONIT_REKOMEND")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MonitRekomend.findAll", query = "SELECT m FROM MonitRekomend m"),
    @NamedQuery(name = "MonitRekomend.findByMonitrekId", query = "SELECT m FROM MonitRekomend m WHERE m.monitrekId = :monitrekId")})
public class MonitRekomend implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "MONITREK_ID")
    @SequenceGenerator(name = "SEQ_MONIT_REKOMEND", sequenceName = "SEQ_MONIT_REKOMEND", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.IDENTITY, generator="SEQ_MONIT_REKOMEND")
    private Integer monitrekId;
    @JoinColumn(name = "MONITDATA_ID", referencedColumnName = "MONITDATA_ID")
    @ManyToOne
    private MonitoringData monitdataId;
    @JoinColumn(name = "SPRREK_ID", referencedColumnName = "SPRREK_ID")
    @ManyToOne
    private SprRekomend sprrekId;

    public MonitRekomend() {
    }

    public MonitRekomend(Integer monitrekId) {
        this.monitrekId = monitrekId;
    }

    public Integer getMonitrekId() {
        return monitrekId;
    }

    public void setMonitrekId(Integer monitrekId) {
        this.monitrekId = monitrekId;
    }

    public MonitoringData getMonitdataId() {
        return monitdataId;
    }

    public void setMonitdataId(MonitoringData monitdataId) {
        this.monitdataId = monitdataId;
    }

    public SprRekomend getSprrekId() {
        return sprrekId;
    }

    public void setSprrekId(SprRekomend sprrekId) {
        this.sprrekId = sprrekId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (monitrekId != null ? monitrekId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MonitRekomend)) {
            return false;
        }
        MonitRekomend other = (MonitRekomend) object;
        if ((this.monitrekId == null && other.monitrekId != null) || (this.monitrekId != null && !this.monitrekId.equals(other.monitrekId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.MonitRekomend[ monitrekId=" + monitrekId + " ]";
    }
    
}
