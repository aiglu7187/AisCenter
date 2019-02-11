/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Aiglu
 */
@Entity
@Table(name = "SPR_PLACES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SprPlaces.findAll", query = "SELECT s FROM SprPlaces s"),
    @NamedQuery(name = "SprPlaces.findBySprplaceId", query = "SELECT s FROM SprPlaces s WHERE s.sprplaceId = :sprplaceId"),
    @NamedQuery(name = "SprPlaces.findBySprplaceName", query = "SELECT s FROM SprPlaces s WHERE s.sprplaceName = :sprplaceName")})
public class SprPlaces implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SPRPLACE_ID")
    @SequenceGenerator(name = "SEQ_SPR_PLACES", sequenceName = "SEQ_SPR_PLACES", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SEQ_SPR_PLACES")
    private Integer sprplaceId;
    @Size(max = 300)
    @Column(name = "SPRPLACE_NAME ")
    private String sprplaceName;

    public Integer getSprplaceId() {
        return sprplaceId;
    }

    public void setSprplaceId(Integer sprplaceId) {
        this.sprplaceId = sprplaceId;
    }

    public String getSprplaceName() {
        return sprplaceName;
    }

    public void setSprplaceName(String sprplaceName) {
        this.sprplaceName = sprplaceName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sprplaceId != null ? sprplaceId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SprPlaces)) {
            return false;
        }
        SprPlaces other = (SprPlaces) object;
        if ((this.sprplaceId == null && other.sprplaceId != null) || (this.sprplaceId != null && !this.sprplaceId.equals(other.sprplaceId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.SprPlaces[ id=" + sprplaceId + " ]";
    }

}
