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
@Table(name = "SPR_OBR")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SprObr.findAll", query = "SELECT s FROM SprObr s ORDER BY s.sprobrName"),
    @NamedQuery(name = "SprObr.findAllAct", query = "SELECT s FROM SprObr s WHERE s.sprobrActive = :sprobrActive ORDER BY s.sprobrName"),
    @NamedQuery(name = "SprObr.findBySprobrId", query = "SELECT s FROM SprObr s WHERE s.sprobrId = :sprobrId ORDER BY s.sprobrName"),
    @NamedQuery(name = "SprObr.findBySprobrName", query = "SELECT s FROM SprObr s WHERE s.sprobrName = :sprobrName"),
    @NamedQuery(name = "SprObr.findBySprobrShname", query = "SELECT s FROM SprObr s WHERE s.sprobrShname = :sprobrShname"),
    @NamedQuery(name = "SprObr.findBySprobrtypeId", query = "SELECT s FROM SprObr s WHERE s.sprobrtypeId = :sprobrtypeId ORDER BY s.sprobrShname"),
    @NamedQuery(name = "SprObr.findBySprobrvidId", query = "SELECT s FROM SprObr s WHERE s.sprobrvidId = :sprobrvidId ORDER BY s.sprobrShname")})
public class SprObr implements Serializable {

    @Column(name = "SPROBR_ACTIVE")
    private Integer sprobrActive;
    @OneToMany(mappedBy = "sprobrId")
    private Collection<ChildrenEducond> childrenEducondCollection;
    /*@Column(name = "SPROBR_ACTIVE")
    private Integer sprobrActive;*/
    @OneToMany(mappedBy = "sprobrId")
    private Collection<ObrSpec> obrSpecCollection;
    @JoinColumn(name = "SPROBRVID_ID", referencedColumnName = "SPROBRVID_ID")
    @ManyToOne
    private SprObrVid sprobrvidId;
    
    @OneToMany(mappedBy = "sprobrId")
    private Collection<KeySprObr> keySprObrCollection;

    @OneToMany(mappedBy = "sprobrIdMain")
    private Collection<ObrObr> obrObrCollection;
    @OneToMany(mappedBy = "sprobrIdSoo")
    private Collection<ObrObr> obrObrCollection1;

    @OneToMany(mappedBy = "sprobrId")
    private Collection<MonitoringData> monitoringDataCollection;

    @OneToMany(mappedBy = "sprobrId")
    private Collection<Pmpk> pmpkCollection;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SPROBR_ID")
    private Integer sprobrId;
    @Size(max = 500)
    @Column(name = "SPROBR_NAME")
    private String sprobrName;
    @Size(max = 150)
    @Column(name = "SPROBR_SHNAME")
    private String sprobrShname;
    @JoinColumn(name = "SPROBRTYPE_ID", referencedColumnName = "SPROBRTYPE_ID")
    @ManyToOne
    private SprObrType sprobrtypeId;
    @JoinColumn(name = "SPROBRSPEC_ID", referencedColumnName = "SPROBRSPEC_ID")
    @ManyToOne
    private SprObrSpec sprobrspecId;
    @OneToMany(mappedBy = "sprobrId")
    private Collection<SprObrVar> sprObrVarCollection;    
    @Size(max = 150)
    @Column(name = "SPROBR_FULLNAME")
    private String sprobrFullname;

    public SprObr() {
    }

    public SprObr(Integer sprobrId) {
        this.sprobrId = sprobrId;
    }

    public Integer getSprobrId() {
        return sprobrId;
    }

    public void setSprobrId(Integer sprobrId) {
        this.sprobrId = sprobrId;
    }

    public String getSprobrName() {
        return sprobrName;
    }

    public void setSprobrName(String sprobrName) {
        this.sprobrName = sprobrName;
    }

    public String getSprobrShname() {
        return sprobrShname;
    }

    public void setSprobrShname(String sprobrShname) {
        this.sprobrShname = sprobrShname;
    }

    public SprObrType getSprobrtypeId() {
        return sprobrtypeId;
    }

    public void setSprobrtypeId(SprObrType sprobrtypeId) {
        this.sprobrtypeId = sprobrtypeId;
    }
    
    public SprObrSpec getSprobrspecId() {
        return sprobrspecId;
    }

    public void setSprobrspecId(SprObrSpec sprobrspecId) {
        this.sprobrspecId = sprobrspecId;
    }

    @XmlTransient
    public Collection<SprObrVar> getSprObrVarCollection() {
        return sprObrVarCollection;
    }

    public void setSprObrVarCollection(Collection<SprObrVar> sprObrVarCollection) {
        this.sprObrVarCollection = sprObrVarCollection;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sprobrId != null ? sprobrId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SprObr)) {
            return false;
        }
        SprObr other = (SprObr) object;
        if ((this.sprobrId == null && other.sprobrId != null) || (this.sprobrId != null && !this.sprobrId.equals(other.sprobrId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.SprObr[ sprobrId=" + sprobrId + " ]";
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

    @XmlTransient
    public Collection<ObrObr> getObrObrCollection() {
        return obrObrCollection;
    }

    public void setObrObrCollection(Collection<ObrObr> obrObrCollection) {
        this.obrObrCollection = obrObrCollection;
    }

    @XmlTransient
    public Collection<ObrObr> getObrObrCollection1() {
        return obrObrCollection1;
    }

    public void setObrObrCollection1(Collection<ObrObr> obrObrCollection1) {
        this.obrObrCollection1 = obrObrCollection1;
    }
    
    public Integer getSprobrActive() {
        return sprobrActive;
    }

    public void setSprobrActive(Integer sprobrActive) {
        this.sprobrActive = sprobrActive;
    }

    @XmlTransient
    public Collection<KeySprObr> getKeySprObrCollection() {
        return keySprObrCollection;
    }

    public void setKeySprObrCollection(Collection<KeySprObr> keySprObrCollection) {
        this.keySprObrCollection = keySprObrCollection;
    }

    @XmlTransient
    public Collection<ObrSpec> getObrSpecCollection() {
        return obrSpecCollection;
    }

    public void setObrSpecCollection(Collection<ObrSpec> obrSpecCollection) {
        this.obrSpecCollection = obrSpecCollection;
    }

    public SprObrVid getSprobrvidId() {
        return sprobrvidId;
    }

    public void setSprobrvidId(SprObrVid sprobrvidId) {
        this.sprobrvidId = sprobrvidId;
    }
    
    public String getSprobrFullname() {
        return sprobrFullname;
    }

    public void setSprobrFullname(String sprobrFullname) {
        this.sprobrFullname = sprobrFullname;
    }

    @XmlTransient
    public Collection<ChildrenEducond> getChildrenEducondCollection() {
        return childrenEducondCollection;
    }

    public void setChildrenEducondCollection(Collection<ChildrenEducond> childrenEducondCollection) {
        this.childrenEducondCollection = childrenEducondCollection;
    }

}
