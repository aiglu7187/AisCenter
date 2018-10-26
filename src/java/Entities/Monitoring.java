/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Aiglu
 */
@Entity
@Table(name = "MONITORING")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Monitoring.findAll", query = "SELECT m FROM Monitoring m"),
    @NamedQuery(name = "Monitoring.findByMonitoringId", query = "SELECT m FROM Monitoring m WHERE m.monitoringId = :monitoringId"),
    @NamedQuery(name = "Monitoring.findByMonitoringDate", query = "SELECT m FROM Monitoring m WHERE m.monitoringDate = :monitoringDate")})
public class Monitoring implements Serializable {

    @OneToMany(mappedBy = "monitoringId")
    private Collection<MonitoringEducond> monitoringEducondCollection;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "MONITORING_ID")
    @SequenceGenerator(name = "SEQ_MONITORING", sequenceName = "SEQ_MONITORING", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.IDENTITY, generator="SEQ_MONITORING")
    private Integer monitoringId;
    @Column(name = "MONITORING_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date monitoringDate;
    @OneToMany(mappedBy = "monitoringId")
    private Collection<MonitoringData> monitoringDataCollection;
    @JoinColumn(name = "SPRREG_ID", referencedColumnName = "SPRREG_ID")
    @ManyToOne
    private SprRegion sprregId;

    public Monitoring() {
    }

    public Monitoring(Integer monitoringId) {
        this.monitoringId = monitoringId;
    }

    public Integer getMonitoringId() {
        return monitoringId;
    }

    public void setMonitoringId(Integer monitoringId) {
        this.monitoringId = monitoringId;
    }

    public Date getMonitoringDate() {
        return monitoringDate;
    }

    public void setMonitoringDate(Date monitoringDate) {
        this.monitoringDate = monitoringDate;
    }

    @XmlTransient
    public Collection<MonitoringData> getMonitoringDataCollection() {
        return monitoringDataCollection;
    }

    public void setMonitoringDataCollection(Collection<MonitoringData> monitoringDataCollection) {
        this.monitoringDataCollection = monitoringDataCollection;
    }

    public SprRegion getSprregId() {
        return sprregId;
    }

    public void setSprregId(SprRegion sprregId) {
        this.sprregId = sprregId;
    }
    
    public String getFormatDate(){
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("dd.MM.yyyy");
        Date d = this.getMonitoringDate();
        String strD = "";
        try{
            strD  = format.format(d);
        }
        catch(Exception ex){            
        }
        return strD;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (monitoringId != null ? monitoringId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Monitoring)) {
            return false;
        }
        Monitoring other = (Monitoring) object;
        if ((this.monitoringId == null && other.monitoringId != null) || (this.monitoringId != null && !this.monitoringId.equals(other.monitoringId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Monitoring[ monitoringId=" + monitoringId + " ]";
    }

    @XmlTransient
    public Collection<MonitoringEducond> getMonitoringEducondCollection() {
        return monitoringEducondCollection;
    }

    public void setMonitoringEducondCollection(Collection<MonitoringEducond> monitoringEducondCollection) {
        this.monitoringEducondCollection = monitoringEducondCollection;
    }
    
}
