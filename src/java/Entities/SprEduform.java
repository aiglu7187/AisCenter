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
@Table(name = "SPR_EDUFORM")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SprEduform.findAll", query = "SELECT s FROM SprEduform s"),
    @NamedQuery(name = "SprEduform.findBySpreduformId", query = "SELECT s FROM SprEduform s WHERE s.spreduformId = :spreduformId"),
    @NamedQuery(name = "SprEduform.findBySpreduformName", query = "SELECT s FROM SprEduform s WHERE s.spreduformName = :spreduformName")})
public class SprEduform implements Serializable {

    @OneToMany(mappedBy = "spreduformId")
    private Collection<ChildrenEducond> childrenEducondCollection;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SPREDUFORM_ID")
    @SequenceGenerator(name = "SEQ_SPR_EDUFORM", sequenceName = "SEQ_SPR_EDUFORM", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.IDENTITY, generator="SEQ_SPR_EDUFORM")
    private Integer spreduformId;
    @Size(max = 50)
    @Column(name = "SPREDUFORM_NAME")
    private String spreduformName;
    @OneToMany(mappedBy = "spreduformId")
    private Collection<MonitoringData> monitoringDataCollection;

    public SprEduform() {
    }

    public SprEduform(Integer spreduformId) {
        this.spreduformId = spreduformId;
    }

    public Integer getSpreduformId() {
        return spreduformId;
    }

    public void setSpreduformId(Integer spreduformId) {
        this.spreduformId = spreduformId;
    }

    public String getSpreduformName() {
        return spreduformName;
    }

    public void setSpreduformName(String spreduformName) {
        this.spreduformName = spreduformName;
    }

    @XmlTransient
    public Collection<MonitoringData> getMonitoringDataCollection() {
        return monitoringDataCollection;
    }

    public void setMonitoringDataCollection(Collection<MonitoringData> monitoringDataCollection) {
        this.monitoringDataCollection = monitoringDataCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (spreduformId != null ? spreduformId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SprEduform)) {
            return false;
        }
        SprEduform other = (SprEduform) object;
        if ((this.spreduformId == null && other.spreduformId != null) || (this.spreduformId != null && !this.spreduformId.equals(other.spreduformId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.SprEduform[ spreduformId=" + spreduformId + " ]";
    }

    @XmlTransient
    public Collection<ChildrenEducond> getChildrenEducondCollection() {
        return childrenEducondCollection;
    }

    public void setChildrenEducondCollection(Collection<ChildrenEducond> childrenEducondCollection) {
        this.childrenEducondCollection = childrenEducondCollection;
    }
    
}
