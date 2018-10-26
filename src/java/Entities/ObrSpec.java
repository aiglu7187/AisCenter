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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Aiglu
 */
@Entity
@Table(name = "OBR_SPEC")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ObrSpec.findAll", query = "SELECT o FROM ObrSpec o"),
    @NamedQuery(name = "ObrSpec.findByObrspecId", query = "SELECT o FROM ObrSpec o WHERE o.obrspecId = :obrspecId")})
public class ObrSpec implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "OBRSPEC_ID")
    private Integer obrspecId;
    @JoinColumn(name = "SPROBR_ID", referencedColumnName = "SPROBR_ID")
    @ManyToOne
    private SprObr sprobrId;
    @JoinColumn(name = "SPROBRSPEC_ID", referencedColumnName = "SPROBRSPEC_ID")
    @ManyToOne
    private SprObrSpec sprobrspecId;

    public ObrSpec() {
    }

    public ObrSpec(Integer obrspecId) {
        this.obrspecId = obrspecId;
    }

    public Integer getObrspecId() {
        return obrspecId;
    }

    public void setObrspecId(Integer obrspecId) {
        this.obrspecId = obrspecId;
    }

    public SprObr getSprobrId() {
        return sprobrId;
    }

    public void setSprobrId(SprObr sprobrId) {
        this.sprobrId = sprobrId;
    }

    public SprObrSpec getSprobrspecId() {
        return sprobrspecId;
    }

    public void setSprobrspecId(SprObrSpec sprobrspecId) {
        this.sprobrspecId = sprobrspecId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (obrspecId != null ? obrspecId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ObrSpec)) {
            return false;
        }
        ObrSpec other = (ObrSpec) object;
        if ((this.obrspecId == null && other.obrspecId != null) || (this.obrspecId != null && !this.obrspecId.equals(other.obrspecId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.ObrSpec[ obrspecId=" + obrspecId + " ]";
    }
    
}
