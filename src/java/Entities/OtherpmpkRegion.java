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
@Table(name = "OTHERPMPK_REGION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OtherpmpkRegion.findAll", query = "SELECT o FROM OtherpmpkRegion o"),
    @NamedQuery(name = "OtherpmpkRegion.findByOtherpmpkregionId", query = "SELECT o FROM OtherpmpkRegion o WHERE o.otherpmpkregionId = :otherpmpkregionId"),
    @NamedQuery(name = "OtherpmpkRegion.findBySprregId", query = "SELECT o FROM OtherpmpkRegion o WHERE o.sprregId = :sprregId"),
    @NamedQuery(name = "OtherpmpkRegion.findBySprotherpmpkId", query = "SELECT o FROM OtherpmpkRegion o WHERE o.sprotherpmpkId = :sprotherpmpkId")})
public class OtherpmpkRegion implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "OTHERPMPKREGION_ID")
    @SequenceGenerator(name = "SEQ_OTHERPMPK_REGION", sequenceName = "SEQ_OTHERPMPK_REGION", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SEQ_OTHERPMPK_REGION")
    private Integer otherpmpkregionId;
    @JoinColumn(name = "SPROTHERPMPK_ID", referencedColumnName = "SPROTHERPMPK_ID")
    @ManyToOne
    private SprOtherPmpk sprotherpmpkId;
    @JoinColumn(name = "SPRREG_ID", referencedColumnName = "SPRREG_ID")
    @ManyToOne
    private SprRegion sprregId;

    public OtherpmpkRegion() {
    }

    public OtherpmpkRegion(Integer otherpmpkregionId) {
        this.otherpmpkregionId = otherpmpkregionId;
    }

    public Integer getOtherpmpkregionId() {
        return otherpmpkregionId;
    }

    public void setOtherpmpkregionId(Integer otherpmpkregionId) {
        this.otherpmpkregionId = otherpmpkregionId;
    }

    public SprOtherPmpk getSprotherpmpkId() {
        return sprotherpmpkId;
    }

    public void setSprotherpmpkId(SprOtherPmpk sprotherpmpkId) {
        this.sprotherpmpkId = sprotherpmpkId;
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
        hash += (otherpmpkregionId != null ? otherpmpkregionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OtherpmpkRegion)) {
            return false;
        }
        OtherpmpkRegion other = (OtherpmpkRegion) object;
        if ((this.otherpmpkregionId == null && other.otherpmpkregionId != null) || (this.otherpmpkregionId != null && !this.otherpmpkregionId.equals(other.otherpmpkregionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.OtherpmpkRegion_1[ otherpmpkregionId=" + otherpmpkregionId + " ]";
    }

}
