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
 * @author admin_ai
 */
@Entity
@Table(name = "SPR_REGION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SprRegion.findAll", query = "SELECT s FROM SprRegion s"),
    @NamedQuery(name = "SprRegion.findBySprregId", query = "SELECT s FROM SprRegion s WHERE s.sprregId = :sprregId"),
    @NamedQuery(name = "SprRegion.findBySprregName", query = "SELECT s FROM SprRegion s WHERE trim(s.sprregName) = trim(:sprregName)"),
    @NamedQuery(name = "SprRegion.findBySprregCenter", query = "SELECT s FROM SprRegion s WHERE s.sprregCenter = :sprregCenter"),
    @NamedQuery(name = "SprRegion.findBySprregOth", query = "SELECT s FROM SprRegion s WHERE s.sprregOth = :sprregOth")})
public class SprRegion implements Serializable {

    @Column(name = "SPRREG_CENTER")
    private Integer sprregCenter;
    /*@Column(name = "SPRREG_CENTER")
    private Integer sprregCenter;*/
    @Column(name = "SPRREG_OTH")
    private Integer sprregOth;
    /*@Column(name = "SPRREG_OTH")
    private Integer sprregOth;*/
    @Column(name = "SPRREG_ISTOWN")
    private Integer sprregIstown;
    @OneToMany(mappedBy = "sprregId")
    private Collection<SprOu> sprOuCollection;
    @OneToMany(mappedBy = "sprregId")
    private Collection<SprOmsu> sprOmsuCollection;
    /*@Column(name = "SPRREG_ISTOWN")
    private Integer sprregIstown;*/
    @Size(max = 100)
    @Column(name = "SPRREG_PRINTNAME")
    private String sprregPrintname;
    
    @OneToMany(mappedBy = "sprregId")
    private Collection<OtherpmpkRegion> otherpmpkRegionCollection;

    @OneToMany(mappedBy = "sprregId")
    private Collection<ParentsReg> parentsRegCollection;
    @OneToMany(mappedBy = "sprregId")
    private Collection<PedReg> pedRegCollection;
    @OneToMany(mappedBy = "sprregId")
    private Collection<ChildrenReg> childrenRegCollection;
    @OneToMany(mappedBy = "sprregId")
    private Collection<Monitoring> monitoringCollection;
    @OneToMany(mappedBy = "sprregId")
    private Collection<SprOo> sprOoCollection;
    @OneToMany(mappedBy = "sprregId")
    private Collection<UsersRegion> usersRegionCollection;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SPRREG_ID")
    private Integer sprregId;
    @Size(max = 50)
    @Column(name = "SPRREG_NAME")
    private String sprregName;
    @OneToMany(mappedBy = "sprregId")    
    private Collection<Parents> parentsCollection;
    @OneToMany(mappedBy = "sprregId")
    private Collection<Ped> pedCollection;
    @OneToMany(mappedBy = "sprregId")
    private Collection<Children> childrenCollection;
    @OneToMany(mappedBy = "sprregId")
    private Collection<Priyom> priyomCollection;

    public SprRegion() {
    }

    public SprRegion(Integer sprregId) {
        this.sprregId = sprregId;
    }

    public Integer getSprregId() {
        return sprregId;
    }

    public void setSprregId(Integer sprregId) {
        this.sprregId = sprregId;
    }

    public String getSprregName() {
        return sprregName;
    }

    public void setSprregName(String sprregName) {
        this.sprregName = sprregName;
    }

    public Integer getSprregCenter() {
        return sprregCenter;
    }

    public void setSprregCenter(Integer sprregCenter) {
        this.sprregCenter = sprregCenter;
    }

    @XmlTransient
    public Collection<Parents> getParentsCollection() {
        return parentsCollection;
    }

    public void setParentsCollection(Collection<Parents> parentsCollection) {
        this.parentsCollection = parentsCollection;
    }

    @XmlTransient
    public Collection<Ped> getPedCollection() {
        return pedCollection;
    }

    public void setPedCollection(Collection<Ped> pedCollection) {
        this.pedCollection = pedCollection;
    }

    @XmlTransient
    public Collection<Children> getChildrenCollection() {
        return childrenCollection;
    }

    public void setChildrenCollection(Collection<Children> childrenCollection) {
        this.childrenCollection = childrenCollection;
    }

    @XmlTransient
    public Collection<Priyom> getPriyomCollection() {
        return priyomCollection;
    }

    public void setPriyomCollection(Collection<Priyom> priyomCollection) {
        this.priyomCollection = priyomCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sprregId != null ? sprregId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SprRegion)) {
            return false;
        }
        SprRegion other = (SprRegion) object;
        if ((this.sprregId == null && other.sprregId != null) || (this.sprregId != null && !this.sprregId.equals(other.sprregId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.SprRegion[ sprregId=" + sprregId + " ]";
    }

    public Integer getSprregOth() {
        return sprregOth;
    }

    public void setSprregOth(Integer sprregOth) {
        this.sprregOth = sprregOth;
    }

    @XmlTransient
    public Collection<UsersRegion> getUsersRegionCollection() {
        return usersRegionCollection;
    }

    public void setUsersRegionCollection(Collection<UsersRegion> usersRegionCollection) {
        this.usersRegionCollection = usersRegionCollection;
    }

    @XmlTransient
    public Collection<Monitoring> getMonitoringCollection() {
        return monitoringCollection;
    }

    public void setMonitoringCollection(Collection<Monitoring> monitoringCollection) {
        this.monitoringCollection = monitoringCollection;
    }

    @XmlTransient
    public Collection<SprOo> getSprOoCollection() {
        return sprOoCollection;
    }

    public void setSprOoCollection(Collection<SprOo> sprOoCollection) {
        this.sprOoCollection = sprOoCollection;
    }

    @XmlTransient
    public Collection<ChildrenReg> getChildrenRegCollection() {
        return childrenRegCollection;
    }

    public void setChildrenRegCollection(Collection<ChildrenReg> childrenRegCollection) {
        this.childrenRegCollection = childrenRegCollection;
    }

    @XmlTransient
    public Collection<ParentsReg> getParentsRegCollection() {
        return parentsRegCollection;
    }

    public void setParentsRegCollection(Collection<ParentsReg> parentsRegCollection) {
        this.parentsRegCollection = parentsRegCollection;
    }

    @XmlTransient
    public Collection<PedReg> getPedRegCollection() {
        return pedRegCollection;
    }

    public void setPedRegCollection(Collection<PedReg> pedRegCollection) {
        this.pedRegCollection = pedRegCollection;
    } 

    @XmlTransient
    public Collection<OtherpmpkRegion> getOtherpmpkRegionCollection() {
        return otherpmpkRegionCollection;
    }

    public void setOtherpmpkRegionCollection(Collection<OtherpmpkRegion> otherpmpkRegionCollection) {
        this.otherpmpkRegionCollection = otherpmpkRegionCollection;
    }

    public Integer getSprregIstown() {
        return sprregIstown;
    }

    public void setSprregIstown(Integer sprregIstown) {
        this.sprregIstown = sprregIstown;
    }

    public String getSprregPrintname() {
        return sprregPrintname;
    }

    public void setSprregPrintname(String sprregPrintname) {
        this.sprregPrintname = sprregPrintname;
    }

    @XmlTransient
    public Collection<SprOmsu> getSprOmsuCollection() {
        return sprOmsuCollection;
    }

    public void setSprOmsuCollection(Collection<SprOmsu> sprOmsuCollection) {
        this.sprOmsuCollection = sprOmsuCollection;
    }

    @XmlTransient
    public Collection<SprOu> getSprOuCollection() {
        return sprOuCollection;
    }

    public void setSprOuCollection(Collection<SprOu> sprOuCollection) {
        this.sprOuCollection = sprOuCollection;
    }

}
