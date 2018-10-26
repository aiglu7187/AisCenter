/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Aiglu
 */
@Entity
@Table(name = "SPR_OBR_VAR")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SprObrVar.findAll", query = "SELECT s FROM SprObrVar s"),
    @NamedQuery(name = "SprObrVar.findBySprobrvarId", query = "SELECT s FROM SprObrVar s WHERE s.sprobrvarId = :sprobrvarId"),
    @NamedQuery(name = "SprObrVar.findBySprobrvarName", query = "SELECT s FROM SprObrVar s WHERE s.sprobrvarName = :sprobrvarName"),
    @NamedQuery(name = "SprObrVar.findBySprobrId", query = "SELECT s FROM SprObrVar s WHERE s.sprobrId = :sprobrId ORDER BY s.sprobrvarName")})
public class SprObrVar implements Serializable {

    @OneToMany(mappedBy = "sprobrvarId")
    private Collection<MonitoringData> monitoringDataCollection;

    @OneToMany(mappedBy = "sprobrvarId")
    private Collection<Pmpk> pmpkCollection;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SPROBRVAR_ID")
    private Integer sprobrvarId;
    @Size(max = 100)
    @Column(name = "SPROBRVAR_NAME")
    private String sprobrvarName;
    @JoinColumn(name = "SPROBR_ID", referencedColumnName = "SPROBR_ID")
    @ManyToOne
    private SprObr sprobrId;

    public SprObrVar() {
    }

    public SprObrVar(Integer sprobrvarId) {
        this.sprobrvarId = sprobrvarId;
    }

    public Integer getSprobrvarId() {
        return sprobrvarId;
    }

    public void setSprobrvarId(Integer sprobrvarId) {
        this.sprobrvarId = sprobrvarId;
    }

    public String getSprobrvarName() {
        return sprobrvarName;
    }

    public void setSprobrvarName(String sprobrvarName) {
        this.sprobrvarName = sprobrvarName;
    }

    public SprObr getSprobrId() {
        return sprobrId;
    }

    public void setSprobrId(SprObr sprobrId) {
        this.sprobrId = sprobrId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sprobrvarId != null ? sprobrvarId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SprObrVar)) {
            return false;
        }
        SprObrVar other = (SprObrVar) object;
        if ((this.sprobrvarId == null && other.sprobrvarId != null) || (this.sprobrvarId != null && !this.sprobrvarId.equals(other.sprobrvarId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.SprObrVar[ sprobrvarId=" + sprobrvarId + " ]";
    }

    @XmlTransient
    public Collection<Pmpk> getPmpkCollection() {
        return pmpkCollection;
    }

    public void setPmpkCollection(Collection<Pmpk> pmpkCollection) {
        this.pmpkCollection = pmpkCollection;
    }

    @XmlTransient
    public Collection<MonitoringData> getMonitoringDataCollection() {
        return monitoringDataCollection;
    }

    public void setMonitoringDataCollection(Collection<MonitoringData> monitoringDataCollection) {
        this.monitoringDataCollection = monitoringDataCollection;
    }
    
}
