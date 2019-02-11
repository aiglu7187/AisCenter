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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Aiglu
 */
@Entity
@Table(name = "REGION_PLACES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RegionPlaces.findAll", query = "SELECT r FROM RegionPlaces r"),
    @NamedQuery(name = "RegionPlaces.findByRegionplaceId", query = "SELECT r FROM RegionPlaces r WHERE r.regionplaceId = :regionplaceId"),
    @NamedQuery(name = "RegionPlaces.findBySprplaceId", query = "SELECT r FROM RegionPlaces r WHERE r.sprplaceId = :sprplaceId"),
    @NamedQuery(name = "RegionPlaces.findBySprregId", query = "SELECT r FROM RegionPlaces r WHERE r.sprregId = :sprregId")})
public class RegionPlaces implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "REGIONPLACE_ID")
    @SequenceGenerator(name = "SEQ_REGION_PLACES", sequenceName = "SEQ_REGION_PLACES", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SEQ_REGION_PLACES")
    private Integer regionplaceId;
    @JoinColumn(name = "SPRPLACE_ID", referencedColumnName = "SPRPLACE_ID")
    @ManyToOne
    private SprPlaces sprplaceId;
    @JoinColumn(name = "SPRREG_ID", referencedColumnName = "SPRREG_ID")
    @ManyToOne
    private SprRegion sprregId;

    public Integer getRegionplaceId() {
        return regionplaceId;
    }

    public void setRegionplaceId(Integer regionplaceId) {
        this.regionplaceId = regionplaceId;
    }

    public SprPlaces getSprplaceId() {
        return sprplaceId;
    }

    public void setSprplaceId(SprPlaces sprplaceId) {
        this.sprplaceId = sprplaceId;
    }

    public SprRegion getSprregId() {
        return sprregId;
    }

    public void setSprregId(SprRegion sprregId) {
        this.sprregId = sprregId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (regionplaceId != null ? regionplaceId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RegionPlaces)) {
            return false;
        }
        RegionPlaces other = (RegionPlaces) object;
        if ((this.regionplaceId == null && other.regionplaceId != null) || (this.regionplaceId != null && !this.regionplaceId.equals(other.regionplaceId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.RegionPlaces[ id=" + regionplaceId + " ]";
    }

}
