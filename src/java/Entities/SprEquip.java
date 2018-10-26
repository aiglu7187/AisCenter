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
@Table(name = "SPR_EQUIP")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SprEquip.findAll", query = "SELECT s FROM SprEquip s"),
    @NamedQuery(name = "SprEquip.findBySprequipId", query = "SELECT s FROM SprEquip s WHERE s.sprequipId = :sprequipId"),
    @NamedQuery(name = "SprEquip.findBySprequipName", query = "SELECT s FROM SprEquip s WHERE s.sprequipName = :sprequipName")})
public class SprEquip implements Serializable {

    @OneToMany(mappedBy = "sprequipId")
    private Collection<ChildrenEducondEq> childrenEducondEqCollection;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SPREQUIP_ID")
    @SequenceGenerator(name = "SEQ_SPR_EQUIP", sequenceName = "SEQ_SPR_EQUIP", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.IDENTITY, generator="SEQ_SPR_EQUIP")
    private Integer sprequipId;
    @Size(max = 300)
    @Column(name = "SPREQUIP_NAME")
    private String sprequipName;   

    public SprEquip() {
    }

    public SprEquip(Integer sprequipId) {
        this.sprequipId = sprequipId;
    }

    public Integer getSprequipId() {
        return sprequipId;
    }

    public void setSprequipId(Integer sprequipId) {
        this.sprequipId = sprequipId;
    }

    public String getSprequipName() {
        return sprequipName;
    }

    public void setSprequipName(String sprequipName) {
        this.sprequipName = sprequipName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sprequipId != null ? sprequipId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SprEquip)) {
            return false;
        }
        SprEquip other = (SprEquip) object;
        if ((this.sprequipId == null && other.sprequipId != null) || (this.sprequipId != null && !this.sprequipId.equals(other.sprequipId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.SprEquip[ sprequipId=" + sprequipId + " ]";
    }

    @XmlTransient
    public Collection<ChildrenEducondEq> getChildrenEducondEqCollection() {
        return childrenEducondEqCollection;
    }

    public void setChildrenEducondEqCollection(Collection<ChildrenEducondEq> childrenEducondEqCollection) {
        this.childrenEducondEqCollection = childrenEducondEqCollection;
    }
    
}
