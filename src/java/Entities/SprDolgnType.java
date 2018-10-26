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
@Table(name = "SPR_DOLGN_TYPE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SprDolgnType.findAll", query = "SELECT s FROM SprDolgnType s"),
    @NamedQuery(name = "SprDolgnType.findBySprdolgntypeId", query = "SELECT s FROM SprDolgnType s WHERE s.sprdolgntypeId = :sprdolgntypeId"),
    @NamedQuery(name = "SprDolgnType.findBySprdolgntypeName", query = "SELECT s FROM SprDolgnType s WHERE s.sprdolgntypeName = :sprdolgntypeName")})
public class SprDolgnType implements Serializable {

    @OneToMany(mappedBy = "sprdolgntypeId")
    private Collection<PayuslDolgntype> payuslDolgntypeCollection;

    @OneToMany(mappedBy = "sprdolgnType")
    private Collection<SprDolgn> sprDolgnCollection;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SPRDOLGNTYPE_ID")
    private Integer sprdolgntypeId;
    @Size(max = 50)
    @Column(name = "SPRDOLGNTYPE_NAME")
    private String sprdolgntypeName;

    public SprDolgnType() {
    }

    public SprDolgnType(Integer sprdolgntypeId) {
        this.sprdolgntypeId = sprdolgntypeId;
    }

    public Integer getSprdolgntypeId() {
        return sprdolgntypeId;
    }

    public void setSprdolgntypeId(Integer sprdolgntypeId) {
        this.sprdolgntypeId = sprdolgntypeId;
    }

    public String getSprdolgntypeName() {
        return sprdolgntypeName;
    }

    public void setSprdolgntypeName(String sprdolgntypeName) {
        this.sprdolgntypeName = sprdolgntypeName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sprdolgntypeId != null ? sprdolgntypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SprDolgnType)) {
            return false;
        }
        SprDolgnType other = (SprDolgnType) object;
        if ((this.sprdolgntypeId == null && other.sprdolgntypeId != null) || (this.sprdolgntypeId != null && !this.sprdolgntypeId.equals(other.sprdolgntypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.SprDolgnType[ sprdolgntypeId=" + sprdolgntypeId + " ]";
    }

    @XmlTransient
    public Collection<SprDolgn> getSprDolgnCollection() {
        return sprDolgnCollection;
    }

    public void setSprDolgnCollection(Collection<SprDolgn> sprDolgnCollection) {
        this.sprDolgnCollection = sprDolgnCollection;
    }

    @XmlTransient
    public Collection<PayuslDolgntype> getPayuslDolgntypeCollection() {
        return payuslDolgntypeCollection;
    }

    public void setPayuslDolgntypeCollection(Collection<PayuslDolgntype> payuslDolgntypeCollection) {
        this.payuslDolgntypeCollection = payuslDolgntypeCollection;
    }
    
}
