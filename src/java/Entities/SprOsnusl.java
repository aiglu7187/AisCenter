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
 * @author admin_ai
 */
@Entity
@Table(name = "SPR_OSNUSL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SprOsnusl.findAll", query = "SELECT s FROM SprOsnusl s"),
    @NamedQuery(name = "SprOsnusl.findBySprosnuslId", query = "SELECT s FROM SprOsnusl s WHERE s.sprosnuslId = :sprosnuslId"),
    @NamedQuery(name = "SprOsnusl.findBySprosnuslName", query = "SELECT s FROM SprOsnusl s WHERE s.sprosnuslName = :sprosnuslName")})
public class SprOsnusl implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SPROSNUSL_ID")
    private Integer sprosnuslId;
    @Size(max = 400)
    @Column(name = "SPROSNUSL_NAME")
    private String sprosnuslName;
    @OneToMany(mappedBy = "sprosnuslId")
    private Collection<SprUsl> sprUslCollection;
    

    public SprOsnusl() {
    }

    public SprOsnusl(Integer sprosnuslId) {
        this.sprosnuslId = sprosnuslId;
    }

    public Integer getSprosnuslId() {
        return sprosnuslId;
    }

    public void setSprosnuslId(Integer sprosnuslId) {
        this.sprosnuslId = sprosnuslId;
    }

    public String getSprosnuslName() {
        return sprosnuslName;
    }

    public void setSprosnuslName(String sprosnuslName) {
        this.sprosnuslName = sprosnuslName;
    }
    
    @XmlTransient
    public Collection<SprUsl> getSprUslCollection() {
        return sprUslCollection;
    }

    public void setSprUslCollection(Collection<SprUsl> sprUslCollection) {
        this.sprUslCollection = sprUslCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sprosnuslId != null ? sprosnuslId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SprOsnusl)) {
            return false;
        }
        SprOsnusl other = (SprOsnusl) object;
        if ((this.sprosnuslId == null && other.sprosnuslId != null) || (this.sprosnuslId != null && !this.sprosnuslId.equals(other.sprosnuslId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.SprOsnusl[ sprosnuslId=" + sprosnuslId + " ]";
    }
    
}
