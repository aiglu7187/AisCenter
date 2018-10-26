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
@Table(name = "SPR_OBR_VID")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SprObrVid.findAll", query = "SELECT s FROM SprObrVid s"),
    @NamedQuery(name = "SprObrVid.findBySprobrvidId", query = "SELECT s FROM SprObrVid s WHERE s.sprobrvidId = :sprobrvidId"),
    @NamedQuery(name = "SprObrVid.findBySprobrvidName", query = "SELECT s FROM SprObrVid s WHERE s.sprobrvidName = :sprobrvidName")})
public class SprObrVid implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SPROBRVID_ID")
    private Integer sprobrvidId;
    @Size(max = 100)
    @Column(name = "SPROBRVID_NAME")
    private String sprobrvidName;
    @OneToMany(mappedBy = "sprobrvidId")
    private Collection<SprObr> sprObrCollection;
    @OneToMany(mappedBy = "sprobrvidId")
    private Collection<KeySprObrVid> keySprObrVidCollection;

    public SprObrVid() {
    }

    public SprObrVid(Integer sprobrvidId) {
        this.sprobrvidId = sprobrvidId;
    }

    public Integer getSprobrvidId() {
        return sprobrvidId;
    }

    public void setSprobrvidId(Integer sprobrvidId) {
        this.sprobrvidId = sprobrvidId;
    }

    public String getSprobrvidName() {
        return sprobrvidName;
    }

    public void setSprobrvidName(String sprobrvidName) {
        this.sprobrvidName = sprobrvidName;
    }

    @XmlTransient
    public Collection<SprObr> getSprObrCollection() {
        return sprObrCollection;
    }

    public void setSprObrCollection(Collection<SprObr> sprObrCollection) {
        this.sprObrCollection = sprObrCollection;
    }

    @XmlTransient
    public Collection<KeySprObrVid> getKeySprObrVidCollection() {
        return keySprObrVidCollection;
    }

    public void setKeySprObrVidCollection(Collection<KeySprObrVid> keySprObrVidCollection) {
        this.keySprObrVidCollection = keySprObrVidCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sprobrvidId != null ? sprobrvidId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SprObrVid)) {
            return false;
        }
        SprObrVid other = (SprObrVid) object;
        if ((this.sprobrvidId == null && other.sprobrvidId != null) || (this.sprobrvidId != null && !this.sprobrvidId.equals(other.sprobrvidId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.SprObrVid[ sprobrvidId=" + sprobrvidId + " ]";
    }
    
}
