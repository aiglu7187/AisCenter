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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
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
@Table(name = "MONITORING_DATA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MonitoringData.findAll", query = "SELECT m FROM MonitoringData m"),
    @NamedQuery(name = "MonitoringData.findByMonitdataId", query = "SELECT m FROM MonitoringData m WHERE m.monitdataId = :monitdataId"),
    @NamedQuery(name = "MonitoringData.findByMonitdataZakl", query = "SELECT m FROM MonitoringData m WHERE m.monitdataZakl = :monitdataZakl"),
    @NamedQuery(name = "MonitoringData.findByMonitdataPrich", query = "SELECT m FROM MonitoringData m WHERE m.monitdataPrich = :monitdataPrich")})
public class MonitoringData implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "MONITDATA_ID")
    @SequenceGenerator(name = "SEQ_MONITORING_DATA", sequenceName = "SEQ_MONITORING_DATA", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.IDENTITY, generator="SEQ_MONITORING_DATA")
    private Integer monitdataId;
    @Column(name = "MONITDATA_ZAKL")
    private Integer monitdataZakl;
    @Size(max = 500)
    @Column(name = "MONITDATA_PRICH")
    private String monitdataPrich;
    @JoinColumn(name = "CHILDEDUPLACE_ID", referencedColumnName = "CHILDEDUPLACE_ID")
    @ManyToOne
    private ChildEduplace childeduplaceId;
    @JoinColumn(name = "CHILD_ID", referencedColumnName = "CHILD_ID")
    @ManyToOne
    private Children childId;
    @JoinColumn(name = "MONITORING_ID", referencedColumnName = "MONITORING_ID")
    @ManyToOne
    private Monitoring monitoringId;
    @JoinColumn(name = "PMPK_ID", referencedColumnName = "PMPK_ID")
    @ManyToOne
    private Pmpk pmpkId;
    @JoinColumn(name = "SPREDUFORM_ID", referencedColumnName = "SPREDUFORM_ID")
    @ManyToOne
    private SprEduform spreduformId;
    @JoinColumn(name = "SPROBR_ID", referencedColumnName = "SPROBR_ID")
    @ManyToOne
    private SprObr sprobrId;
    @JoinColumn(name = "SPROBRVAR_ID", referencedColumnName = "SPROBRVAR_ID")
    @ManyToOne
    private SprObrVar sprobrvarId;
    @Size(max = 50)
    @Column(name = "MONITORING_RESULT")
    private String monitoringResult;
    @OneToMany(mappedBy = "monitdataId")
    private Collection<MonitRekomend> monitRekomendCollection;

    public MonitoringData() {
    }

    public MonitoringData(Integer monitdataId) {
        this.monitdataId = monitdataId;
    }

    public Integer getMonitdataId() {
        return monitdataId;
    }

    public void setMonitdataId(Integer monitdataId) {
        this.monitdataId = monitdataId;
    }

    public Integer getMonitdataZakl() {
        return monitdataZakl;
    }

    public void setMonitdataZakl(Integer monitdataZakl) {
        this.monitdataZakl = monitdataZakl;
    }

    public String getMonitdataPrich() {
        return monitdataPrich;
    }

    public void setMonitdataPrich(String monitdataPrich) {
        this.monitdataPrich = monitdataPrich;
    }

    public ChildEduplace getChildeduplaceId() {
        return childeduplaceId;
    }

    public void setChildeduplaceId(ChildEduplace childeduplaceId) {
        this.childeduplaceId = childeduplaceId;
    }

    public Children getChildId() {
        return childId;
    }

    public void setChildId(Children childId) {
        this.childId = childId;
    }

    public Monitoring getMonitoringId() {
        return monitoringId;
    }

    public void setMonitoringId(Monitoring monitoringId) {
        this.monitoringId = monitoringId;
    }

    public Pmpk getPmpkId() {
        return pmpkId;
    }

    public void setPmpkId(Pmpk pmpkId) {
        this.pmpkId = pmpkId;
    }

    public SprEduform getSpreduformId() {
        return spreduformId;
    }

    public void setSpreduformId(SprEduform spreduformId) {
        this.spreduformId = spreduformId;
    }

    public SprObr getSprobrId() {
        return sprobrId;
    }

    public void setSprobrId(SprObr sprobrId) {
        this.sprobrId = sprobrId;
    }

    public SprObrVar getSprobrvarId() {
        return sprobrvarId;
    }

    public void setSprobrvarId(SprObrVar sprobrvarId) {
        this.sprobrvarId = sprobrvarId;
    }
    
    public String getMonitoringResult() {
        return monitoringResult;
    }

    public void setMonitoringResult(String monitoringResult) {
        this.monitoringResult = monitoringResult;
    }

    @XmlTransient
    public Collection<MonitRekomend> getMonitRekomendCollection() {
        return monitRekomendCollection;
    }

    public void setMonitRekomendCollection(Collection<MonitRekomend> monitRekomendCollection) {
        this.monitRekomendCollection = monitRekomendCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (monitdataId != null ? monitdataId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MonitoringData)) {
            return false;
        }
        MonitoringData other = (MonitoringData) object;
        if ((this.monitdataId == null && other.monitdataId != null) || (this.monitdataId != null && !this.monitdataId.equals(other.monitdataId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.MonitoringData[ monitdataId=" + monitdataId + " ]";
    }
    
}
