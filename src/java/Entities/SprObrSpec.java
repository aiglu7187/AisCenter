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
@Table(name = "SPR_OBR_SPEC")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SprObrSpec.findAll", query = "SELECT s FROM SprObrSpec s"),
    @NamedQuery(name = "SprObrSpec.findBySprobrspecId", query = "SELECT s FROM SprObrSpec s WHERE s.sprobrspecId = :sprobrspecId"),
    @NamedQuery(name = "SprObrSpec.findBySprobrspecName", query = "SELECT s FROM SprObrSpec s WHERE s.sprobrspecName = :sprobrspecName")})
public class SprObrSpec implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SPROBRSPEC_ID")
    private Integer sprobrspecId;
    @Size(max = 100)
    @Column(name = "SPROBRSPEC_NAME")
    private String sprobrspecName;
    @OneToMany(mappedBy = "sprobrspecId")
    private Collection<KeySprObrSpec> keySprObrSpecCollection;
    @OneToMany(mappedBy = "sprobrspecId")
    private Collection<ObrSpec> obrSpecCollection;

    public SprObrSpec() {
    }

    public SprObrSpec(Integer sprobrspecId) {
        this.sprobrspecId = sprobrspecId;
    }

    public Integer getSprobrspecId() {
        return sprobrspecId;
    }

    public void setSprobrspecId(Integer sprobrspecId) {
        this.sprobrspecId = sprobrspecId;
    }

    public String getSprobrspecName() {
        return sprobrspecName;
    }

    public void setSprobrspecName(String sprobrspecName) {
        this.sprobrspecName = sprobrspecName;
    }

    @XmlTransient
    public Collection<KeySprObrSpec> getKeySprObrSpecCollection() {
        return keySprObrSpecCollection;
    }

    public void setKeySprObrSpecCollection(Collection<KeySprObrSpec> keySprObrSpecCollection) {
        this.keySprObrSpecCollection = keySprObrSpecCollection;
    }

    @XmlTransient
    public Collection<ObrSpec> getObrSpecCollection() {
        return obrSpecCollection;
    }

    public void setObrSpecCollection(Collection<ObrSpec> obrSpecCollection) {
        this.obrSpecCollection = obrSpecCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sprobrspecId != null ? sprobrspecId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SprObrSpec)) {
            return false;
        }
        SprObrSpec other = (SprObrSpec) object;
        if ((this.sprobrspecId == null && other.sprobrspecId != null) || (this.sprobrspecId != null && !this.sprobrspecId.equals(other.sprobrspecId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.SprObrSpec[ sprobrspecId=" + sprobrspecId + " ]";
    }
    
}
