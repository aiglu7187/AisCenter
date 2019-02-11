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
@Table(name = "SPR_STAT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SprStat.findAll", query = "SELECT s FROM SprStat s"),
    @NamedQuery(name = "SprStat.findBySprstatId", query = "SELECT s FROM SprStat s WHERE s.sprstatId = :sprstatId"),
    @NamedQuery(name = "SprStat.findBySprstatName", query = "SELECT s FROM SprStat s WHERE s.sprstatName = :sprstatName"),
    @NamedQuery(name = "SprStat.findBySprstatV", query = "SELECT s FROM SprStat s WHERE s.sprstatV = :sprstatV ORDER BY s.sprstatId "),
    @NamedQuery(name = "SprStat.findBySprstatMain", query = "SELECT s FROM SprStat s WHERE s.sprstatMain = :sprstatMain ORDER BY s.sprstatId ")})
public class SprStat implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SPRSTAT_ID")
    private Integer sprstatId;
    @Size(max = 50)
    @Column(name = "SPRSTAT_NAME")
    private String sprstatName;    
    @Column(name = "SPRSTAT_V")
    private Integer sprstatV;
    @Column(name = "SPRSTAT_N")
    private Integer sprstatN;
    @Column(name = "SPRSTAT_INV")
    private Integer sprstatInv;
    @Column(name = "SPRSTAT_OVZ")
    private Integer sprstatOvz;
    @Column(name = "SPRSTAT_MAIN")
    private Integer sprstatMain;
    @Size(max = 200)
    @Column(name = "SPRSTAT_FULLNAME")
    private String sprstatFullname;
    
    @OneToMany(mappedBy = "sprstatId")
    private Collection<ChildStat> childStatCollection;
    @OneToMany(mappedBy = "sprstatId")
    private Collection<ChildStatus> childStatusCollection;
    @OneToMany(mappedBy = "sprstatIdMain")
    private Collection<SprStatPod> sprStatPodCollection;
    @OneToMany(mappedBy = "sprstatIdPod")
    private Collection<SprStatPod> sprStatPodCollection1;

    public SprStat() {
    }

    public SprStat(Integer sprstatId) {
        this.sprstatId = sprstatId;
    }

    public Integer getSprstatId() {
        return sprstatId;
    }

    public void setSprstatId(Integer sprstatId) {
        this.sprstatId = sprstatId;
    }

    public String getSprstatName() {
        return sprstatName;
    }

    public void setSprstatName(String sprstatName) {
        this.sprstatName = sprstatName;
    }

    public Integer getSprstatV() {
        return sprstatV;
    }

    public void setSprstatV(Integer sprstatV) {
        this.sprstatV = sprstatV;
    }

    public Integer getSprstatN() {
        return sprstatN;
    }

    public void setSprstatN(Integer sprstatN) {
        this.sprstatN = sprstatN;
    }

    public Integer getSprstatInv() {
        return sprstatInv;
    }

    public void setSprstatInv(Integer sprstatInv) {
        this.sprstatInv = sprstatInv;
    }
    
    public Integer getSprstatOvz() {
        return sprstatOvz;
    }

    public void setSprstatOvz(Integer sprstatOvz) {
        this.sprstatOvz = sprstatOvz;
    }
    
    public Integer getSprstatMain() {
        return sprstatMain;
    }

    public void setSprstatMain(Integer sprstatMain) {
        this.sprstatMain = sprstatMain;
    }
    
    public String getSprstatFullname() {
        return sprstatFullname;
    }

    public void setSprstatFullname(String sprstatFullname) {
        this.sprstatFullname = sprstatFullname;
    }

    @XmlTransient
    public Collection<ChildStat> getChildStatCollection() {
        return childStatCollection;
    }

    public void setChildStatCollection(Collection<ChildStat> childStatCollection) {
        this.childStatCollection = childStatCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sprstatId != null ? sprstatId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SprStat)) {
            return false;
        }
        SprStat other = (SprStat) object;
        if ((this.sprstatId == null && other.sprstatId != null) || (this.sprstatId != null && !this.sprstatId.equals(other.sprstatId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.SprStat[ sprstatId=" + sprstatId + " ]";
    }

    @XmlTransient
    public Collection<SprStatPod> getSprStatPodCollection() {
        return sprStatPodCollection;
    }

    public void setSprStatPodCollection(Collection<SprStatPod> sprStatPodCollection) {
        this.sprStatPodCollection = sprStatPodCollection;
    }

    @XmlTransient
    public Collection<SprStatPod> getSprStatPodCollection1() {
        return sprStatPodCollection1;
    }

    public void setSprStatPodCollection1(Collection<SprStatPod> sprStatPodCollection1) {
        this.sprStatPodCollection1 = sprStatPodCollection1;
    }

    @XmlTransient
    public Collection<ChildStatus> getChildStatusCollection() {
        return childStatusCollection;
    }

    public void setChildStatusCollection(Collection<ChildStatus> childStatusCollection) {
        this.childStatusCollection = childStatusCollection;
    }

}
