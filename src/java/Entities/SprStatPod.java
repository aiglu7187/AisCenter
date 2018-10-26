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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Aiglu
 */
@Entity
@Table(name = "SPR_STAT_POD")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SprStatPod.findAll", query = "SELECT s FROM SprStatPod s ORDER BY s.sprstatIdMain, s.sprstatpodId"),
    @NamedQuery(name = "SprStatPod.findBySprstatpodId", query = "SELECT s FROM SprStatPod s WHERE s.sprstatpodId = :sprstatpodId"),
    @NamedQuery(name = "SprStatPod.findByStatMain", query = "SELECT s.sprstatIdPod FROM SprStatPod s WHERE s.sprstatIdMain = :sprstatIdMain ORDER BY s.sprstatpodId "),
    @NamedQuery(name = "SprStatPod.findByStatPod", query = "SELECT s.sprstatIdMain FROM SprStatPod s WHERE s.sprstatIdPod = :sprstatIdPod ")})
public class SprStatPod implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SPRSTATPOD_ID")
    private Integer sprstatpodId;
    @JoinColumn(name = "SPRSTAT_ID_MAIN", referencedColumnName = "SPRSTAT_ID")
    @ManyToOne
    private SprStat sprstatIdMain;
    @JoinColumn(name = "SPRSTAT_ID_POD", referencedColumnName = "SPRSTAT_ID")
    @ManyToOne
    private SprStat sprstatIdPod;

    public SprStatPod() {
    }

    public SprStatPod(Integer sprstatpodId) {
        this.sprstatpodId = sprstatpodId;
    }

    public Integer getSprstatpodId() {
        return sprstatpodId;
    }

    public void setSprstatpodId(Integer sprstatpodId) {
        this.sprstatpodId = sprstatpodId;
    }

    public SprStat getSprstatIdMain() {
        return sprstatIdMain;
    }

    public void setSprstatIdMain(SprStat sprstatIdMain) {
        this.sprstatIdMain = sprstatIdMain;
    }

    public SprStat getSprstatIdPod() {
        return sprstatIdPod;
    }

    public void setSprstatIdPod(SprStat sprstatIdPod) {
        this.sprstatIdPod = sprstatIdPod;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sprstatpodId != null ? sprstatpodId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SprStatPod)) {
            return false;
        }
        SprStatPod other = (SprStatPod) object;
        if ((this.sprstatpodId == null && other.sprstatpodId != null) || (this.sprstatpodId != null && !this.sprstatpodId.equals(other.sprstatpodId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.SprStatPod[ sprstatpodId=" + sprstatpodId + " ]";
    }
    
}
