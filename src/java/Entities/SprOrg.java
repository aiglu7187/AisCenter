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
@Table(name = "SPR_ORG")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SprOrg.findAll", query = "SELECT s FROM SprOrg s"),
    @NamedQuery(name = "SprOrg.findBySprorgId", query = "SELECT s FROM SprOrg s WHERE s.sprorgId = :sprorgId"),
    @NamedQuery(name = "SprOrg.findBySprorgName", query = "SELECT s FROM SprOrg s WHERE s.sprorgName = :sprorgName")})
public class SprOrg implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SPRORG_ID")
    private Integer sprorgId;
    @Size(max = 100)
    @Column(name = "SPRORG_NAME")
    private String sprorgName;
    @OneToMany(mappedBy = "sprorgId")
    private Collection<Ped> pedCollection;

    public SprOrg() {
    }

    public SprOrg(Integer sprorgId) {
        this.sprorgId = sprorgId;
    }

    public Integer getSprorgId() {
        return sprorgId;
    }

    public void setSprorgId(Integer sprorgId) {
        this.sprorgId = sprorgId;
    }

    public String getSprorgName() {
        return sprorgName;
    }

    public void setSprorgName(String sprorgName) {
        this.sprorgName = sprorgName;
    }

    @XmlTransient
    public Collection<Ped> getPedCollection() {
        return pedCollection;
    }

    public void setPedCollection(Collection<Ped> pedCollection) {
        this.pedCollection = pedCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sprorgId != null ? sprorgId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SprOrg)) {
            return false;
        }
        SprOrg other = (SprOrg) object;
        if ((this.sprorgId == null && other.sprorgId != null) || (this.sprorgId != null && !this.sprorgId.equals(other.sprorgId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.SprOrg[ sprorgId=" + sprorgId + " ]";
    }
    
}
