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
@Table(name = "SPR_REKOMEND")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SprRekomend.findAll", query = "SELECT s FROM SprRekomend s"),
    @NamedQuery(name = "SprRekomend.findBySprrekId", query = "SELECT s FROM SprRekomend s WHERE s.sprrekId = :sprrekId"),
    @NamedQuery(name = "SprRekomend.findBySprrekName", query = "SELECT s FROM SprRekomend s WHERE s.sprrekName = :sprrekName"),
    @NamedQuery(name = "SprRekomend.findBySprrekShortname", query = "SELECT s FROM SprRekomend s WHERE s.sprrekShortname = :sprrekShortname")})
public class SprRekomend implements Serializable {

    @OneToMany(mappedBy = "sprrekId")
    private Collection<ChildrenEducondRek> childrenEducondRekCollection;

    @OneToMany(mappedBy = "sprrekId")
    private Collection<MonitRekomend> monitRekomendCollection;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SPRREK_ID")
    private Integer sprrekId;
    @Size(max = 100)
    @Column(name = "SPRREK_NAME")
    private String sprrekName;
    @Size(max = 50)
    @Column(name = "SPRREK_SHORTNAME")
    private String sprrekShortname;
    @OneToMany(mappedBy = "sprrekId")
    private Collection<PmpkRek> pmpkRekCollection;

    public SprRekomend() {
    }

    public SprRekomend(Integer sprrekId) {
        this.sprrekId = sprrekId;
    }

    public Integer getSprrekId() {
        return sprrekId;
    }

    public void setSprrekId(Integer sprrekId) {
        this.sprrekId = sprrekId;
    }

    public String getSprrekName() {
        return sprrekName;
    }

    public void setSprrekName(String sprrekName) {
        this.sprrekName = sprrekName;
    }
    
    public String getSprrekShortname() {
        return sprrekShortname;
    }

    public void setSprrekShortname(String sprrekShortname) {
        this.sprrekShortname = sprrekShortname;
    }

    @XmlTransient
    public Collection<PmpkRek> getPmpkRekCollection() {
        return pmpkRekCollection;
    }

    public void setPmpkRekCollection(Collection<PmpkRek> pmpkRekCollection) {
        this.pmpkRekCollection = pmpkRekCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sprrekId != null ? sprrekId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SprRekomend)) {
            return false;
        }
        SprRekomend other = (SprRekomend) object;
        if ((this.sprrekId == null && other.sprrekId != null) || (this.sprrekId != null && !this.sprrekId.equals(other.sprrekId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.SprRekomend[ sprrekId=" + sprrekId + " ]";
    }

    @XmlTransient
    public Collection<MonitRekomend> getMonitRekomendCollection() {
        return monitRekomendCollection;
    }

    public void setMonitRekomendCollection(Collection<MonitRekomend> monitRekomendCollection) {
        this.monitRekomendCollection = monitRekomendCollection;
    }

    @XmlTransient
    public Collection<ChildrenEducondRek> getChildrenEducondRekCollection() {
        return childrenEducondRekCollection;
    }

    public void setChildrenEducondRekCollection(Collection<ChildrenEducondRek> childrenEducondRekCollection) {
        this.childrenEducondRekCollection = childrenEducondRekCollection;
    }
    
}
