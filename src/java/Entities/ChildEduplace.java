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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Aiglu
 */
@Entity
@Table(name = "CHILD_EDUPLACE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ChildEduplace.findAll", query = "SELECT c FROM ChildEduplace c"),
    @NamedQuery(name = "ChildEduplace.findByChildeduplaceId", query = "SELECT c FROM ChildEduplace c WHERE c.childeduplaceId = :childeduplaceId"),
    @NamedQuery(name = "ChildEduplace.findByChildeduplaceDaten", query = "SELECT c FROM ChildEduplace c WHERE c.childeduplaceDaten = :childeduplaceDaten"),
    @NamedQuery(name = "ChildEduplace.findByChildeduplaceDatek", query = "SELECT c FROM ChildEduplace c WHERE c.childeduplaceDatek = :childeduplaceDatek"),
    @NamedQuery(name = "ChildEduplace.findByChild", query = "SELECT c FROM ChildEduplace c WHERE c.childId = :childId")})
public class ChildEduplace implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "CHILDEDUPLACE_ID")
    @SequenceGenerator(name = "SEQ_CHILD_EDUPLACE", sequenceName = "SEQ_CHILD_EDUPLACE", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.IDENTITY, generator="SEQ_CHILD_EDUPLACE")
    private Integer childeduplaceId;
    @Column(name = "CHILDEDUPLACE_DATEN")
    @Temporal(TemporalType.TIMESTAMP)
    private Date childeduplaceDaten;
    @Column(name = "CHILDEDUPLACE_DATEK")
    @Temporal(TemporalType.TIMESTAMP)
    private Date childeduplaceDatek;
    @Column(name = "CHILDEDUPLACE_BEGINEDU")
    @Temporal(TemporalType.TIMESTAMP)
    private Date childeduplaceBeginedu;
    @OneToMany(mappedBy = "childeduplaceId")
    private Collection<MonitoringData> monitoringDataCollection;
    @JoinColumn(name = "CHILD_ID", referencedColumnName = "CHILD_ID")
    @ManyToOne
    private Children childId;
    @JoinColumn(name = "SPROO_ID", referencedColumnName = "SPROO_ID")
    @ManyToOne
    private SprOo sprooId;
    @JoinColumn(name = "SPRSTAGE_ID", referencedColumnName = "SPRSTAGE_ID")
    @ManyToOne
    private SprStage sprstageId;

    public ChildEduplace() {
    }

    public ChildEduplace(Integer childeduplaceId) {
        this.childeduplaceId = childeduplaceId;
    }

    public Integer getChildeduplaceId() {
        return childeduplaceId;
    }

    public void setChildeduplaceId(Integer childeduplaceId) {
        this.childeduplaceId = childeduplaceId;
    }

    public Date getChildeduplaceDaten() {
        return childeduplaceDaten;
    }

    public void setChildeduplaceDaten(Date childeduplaceDaten) {
        this.childeduplaceDaten = childeduplaceDaten;
    }

    public Date getChildeduplaceDatek() {
        return childeduplaceDatek;
    }

    public void setChildeduplaceDatek(Date childeduplaceDatek) {
        this.childeduplaceDatek = childeduplaceDatek;
    }
    
    public Date getChildeduplaceBeginedu() {
        return childeduplaceBeginedu;
    }

    public void setChildeduplaceBeginedu(Date childeduplaceBeginedu) {
        this.childeduplaceBeginedu = childeduplaceBeginedu;
    }

    @XmlTransient
    public Collection<MonitoringData> getMonitoringDataCollection() {
        return monitoringDataCollection;
    }

    public void setMonitoringDataCollection(Collection<MonitoringData> monitoringDataCollection) {
        this.monitoringDataCollection = monitoringDataCollection;
    }

    public Children getChildId() {
        return childId;
    }

    public void setChildId(Children childId) {
        this.childId = childId;
    }

    public SprOo getSprooId() {
        return sprooId;
    }

    public void setSprooId(SprOo sprooId) {
        this.sprooId = sprooId;
    }

    public SprStage getSprstageId() {
        return sprstageId;
    }

    public void setSprstageId(SprStage sprstageId) {
        this.sprstageId = sprstageId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (childeduplaceId != null ? childeduplaceId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ChildEduplace)) {
            return false;
        }
        ChildEduplace other = (ChildEduplace) object;
        if ((this.childeduplaceId == null && other.childeduplaceId != null) || (this.childeduplaceId != null && !this.childeduplaceId.equals(other.childeduplaceId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.ChildEduplace[ childeduplaceId=" + childeduplaceId + " ]";
    }
    
    public String getFormatDateK(){
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("dd.MM.yyyy");
        Date d = this.getChildeduplaceDatek();
        String strD = "";
        try{
            strD  = format.format(d);
        }
        catch(Exception ex){            
        }
        return strD;
    }
    
}
