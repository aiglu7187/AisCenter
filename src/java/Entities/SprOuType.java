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
@Table(name = "SPR_OU_TYPE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SprOuType.findAll", query = "SELECT s FROM SprOuType s"),
    @NamedQuery(name = "SprOuType.findBySproutypeId", query = "SELECT s FROM SprOuType s WHERE s.sproutypeId = :sproutypeId"),
    @NamedQuery(name = "SprOuType.findBySproutypeName", query = "SELECT s FROM SprOuType s WHERE s.sproutypeName = :sproutypeName")})
public class SprOuType implements Serializable {

    @OneToMany(mappedBy = "sproutypeId")
    private Collection<KeySprOuType> keySprOuTypeCollection;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SPROUTYPE_ID")
    private Integer sproutypeId;
    @Size(max = 255)
    @Column(name = "SPROUTYPE_NAME")
    private String sproutypeName;
    @OneToMany(mappedBy = "sproutypeId")
    private Collection<SprOu> sprOuCollection;

    public SprOuType() {
    }

    public SprOuType(Integer sproutypeId) {
        this.sproutypeId = sproutypeId;
    }

    public Integer getSproutypeId() {
        return sproutypeId;
    }

    public void setSproutypeId(Integer sproutypeId) {
        this.sproutypeId = sproutypeId;
    }

    public String getSproutypeName() {
        return sproutypeName;
    }

    public void setSproutypeName(String sproutypeName) {
        this.sproutypeName = sproutypeName;
    }

    @XmlTransient
    public Collection<SprOu> getSprOuCollection() {
        return sprOuCollection;
    }

    public void setSprOuCollection(Collection<SprOu> sprOuCollection) {
        this.sprOuCollection = sprOuCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sproutypeId != null ? sproutypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SprOuType)) {
            return false;
        }
        SprOuType other = (SprOuType) object;
        if ((this.sproutypeId == null && other.sproutypeId != null) || (this.sproutypeId != null && !this.sproutypeId.equals(other.sproutypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.SprOuType[ sproutypeId=" + sproutypeId + " ]";
    }

    @XmlTransient
    public Collection<KeySprOuType> getKeySprOuTypeCollection() {
        return keySprOuTypeCollection;
    }

    public void setKeySprOuTypeCollection(Collection<KeySprOuType> keySprOuTypeCollection) {
        this.keySprOuTypeCollection = keySprOuTypeCollection;
    }
    
}
