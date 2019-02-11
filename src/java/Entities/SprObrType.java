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
@Table(name = "SPR_OBR_TYPE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SprObrType.findAll", query = "SELECT s FROM SprObrType s ORDER BY s.sprobrtypeId "),
    @NamedQuery(name = "SprObrType.findBySprobrtypeId", query = "SELECT s FROM SprObrType s WHERE s.sprobrtypeId = :sprobrtypeId"),
    @NamedQuery(name = "SprObrType.findBySprobrtypeName", query = "SELECT s FROM SprObrType s WHERE s.sprobrtypeName = :sprobrtypeName")})
public class SprObrType implements Serializable {

    @OneToMany(mappedBy = "sprobrtypeId")
    private Collection<KeySprObrType> keySprObrTypeCollection;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SPROBRTYPE_ID")
    private Integer sprobrtypeId;
    @Size(max = 150)
    @Column(name = "SPROBRTYPE_NAME")
    private String sprobrtypeName;
    @OneToMany(mappedBy = "sprobrtypeId")
    private Collection<SprObr> sprObrCollection;

    public SprObrType() {
    }

    public SprObrType(Integer sprobrtypeId) {
        this.sprobrtypeId = sprobrtypeId;
    }

    public Integer getSprobrtypeId() {
        return sprobrtypeId;
    }

    public void setSprobrtypeId(Integer sprobrtypeId) {
        this.sprobrtypeId = sprobrtypeId;
    }

    public String getSprobrtypeName() {
        return sprobrtypeName;
    }

    public void setSprobrtypeName(String sprobrtypeName) {
        this.sprobrtypeName = sprobrtypeName;
    }

    @XmlTransient
    public Collection<SprObr> getSprObrCollection() {
        return sprObrCollection;
    }

    public void setSprObrCollection(Collection<SprObr> sprObrCollection) {
        this.sprObrCollection = sprObrCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sprobrtypeId != null ? sprobrtypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SprObrType)) {
            return false;
        }
        SprObrType other = (SprObrType) object;
        if ((this.sprobrtypeId == null && other.sprobrtypeId != null) || (this.sprobrtypeId != null && !this.sprobrtypeId.equals(other.sprobrtypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.SprObrType[ sprobrtypeId=" + sprobrtypeId + " ]";
    }

    @XmlTransient
    public Collection<KeySprObrType> getKeySprObrTypeCollection() {
        return keySprObrTypeCollection;
    }

    public void setKeySprObrTypeCollection(Collection<KeySprObrType> keySprObrTypeCollection) {
        this.keySprObrTypeCollection = keySprObrTypeCollection;
    }
    
}
