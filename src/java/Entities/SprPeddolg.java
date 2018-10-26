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
@Table(name = "SPR_PEDDOLG")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SprPeddolg.findAll", query = "SELECT s FROM SprPeddolg s"),
    @NamedQuery(name = "SprPeddolg.findBySprpeddolgId", query = "SELECT s FROM SprPeddolg s WHERE s.sprpeddolgId = :sprpeddolgId"),
    @NamedQuery(name = "SprPeddolg.findBySprpeddolgName", query = "SELECT s FROM SprPeddolg s WHERE s.sprpeddolgName = :sprpeddolgName")})
public class SprPeddolg implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SPRPEDDOLG_ID")
    private Integer sprpeddolgId;
    @Size(max = 100)
    @Column(name = "SPRPEDDOLG_NAME")
    private String sprpeddolgName;
    @OneToMany(mappedBy = "sprpeddolgId")
    private Collection<Ped> pedCollection;

    public SprPeddolg() {
    }

    public SprPeddolg(Integer sprpeddolgId) {
        this.sprpeddolgId = sprpeddolgId;
    }

    public Integer getSprpeddolgId() {
        return sprpeddolgId;
    }

    public void setSprpeddolgId(Integer sprpeddolgId) {
        this.sprpeddolgId = sprpeddolgId;
    }

    public String getSprpeddolgName() {
        return sprpeddolgName;
    }

    public void setSprpeddolgName(String sprpeddolgName) {
        this.sprpeddolgName = sprpeddolgName;
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
        hash += (sprpeddolgId != null ? sprpeddolgId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SprPeddolg)) {
            return false;
        }
        SprPeddolg other = (SprPeddolg) object;
        if ((this.sprpeddolgId == null && other.sprpeddolgId != null) || (this.sprpeddolgId != null && !this.sprpeddolgId.equals(other.sprpeddolgId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.SprPeddolg[ sprpeddolgId=" + sprpeddolgId + " ]";
    }
    
}
