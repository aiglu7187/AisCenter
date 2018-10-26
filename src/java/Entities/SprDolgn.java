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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "SPR_DOLGN")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SprDolgn.findAll", query = "SELECT s FROM SprDolgn s ORDER BY s.sprdolgnName"),
    @NamedQuery(name = "SprDolgn.findBySprdolgnId", query = "SELECT s FROM SprDolgn s WHERE s.sprdolgnId = :sprdolgnId"),
    @NamedQuery(name = "SprDolgn.findBySprdolgnName", query = "SELECT s FROM SprDolgn s WHERE s.sprdolgnName = :sprdolgnName"),
    @NamedQuery(name = "SprDolgn.findBySprdolgnType", query = "SELECT s FROM SprDolgn s WHERE s.sprdolgnType = :sprdolgnType")})
public class SprDolgn implements Serializable {

    @OneToMany(mappedBy = "sprdolgnId")
    private Collection<SotrudDolgn> sotrudDolgnCollection;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SPRDOLGN_ID")
    private Integer sprdolgnId;
    @Size(max = 50)
    @Column(name = "SPRDOLGN_NAME")
    private String sprdolgnName;
    
    @JoinColumn(name = "SPRDOLGN_TYPE", referencedColumnName = "SPRDOLGNTYPE_ID")
    @ManyToOne
    private SprDolgnType sprdolgnType;
    
    @OneToMany(mappedBy = "sprdolgnId")    
    private Collection<Sotrud> sotrudCollection;

    public SprDolgn() {
    }

    public SprDolgn(Integer sprdolgnId) {
        this.sprdolgnId = sprdolgnId;
    }

    public Integer getSprdolgnId() {
        return sprdolgnId;
    }

    public void setSprdolgnId(Integer sprdolgnId) {
        this.sprdolgnId = sprdolgnId;
    }

    public String getSprdolgnName() {
        return sprdolgnName;
    }

    public void setSprdolgnName(String sprdolgnName) {
        this.sprdolgnName = sprdolgnName;
    }

    public SprDolgnType getSprdolgnType() {
        return sprdolgnType;
    }

    public void setSprdolgnType(SprDolgnType sprdolgnType) {
        this.sprdolgnType = sprdolgnType;
    }

    @XmlTransient
    public Collection<Sotrud> getSotrudCollection() {
        return sotrudCollection;
    }

    public void setSotrudCollection(Collection<Sotrud> sotrudCollection) {
        this.sotrudCollection = sotrudCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sprdolgnId != null ? sprdolgnId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SprDolgn)) {
            return false;
        }
        SprDolgn other = (SprDolgn) object;
        if ((this.sprdolgnId == null && other.sprdolgnId != null) || (this.sprdolgnId != null && !this.sprdolgnId.equals(other.sprdolgnId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.SprDolgn[ sprdolgnId=" + sprdolgnId + " ]";
    }

    @XmlTransient
    public Collection<SotrudDolgn> getSotrudDolgnCollection() {
        return sotrudDolgnCollection;
    }

    public void setSotrudDolgnCollection(Collection<SotrudDolgn> sotrudDolgnCollection) {
        this.sotrudDolgnCollection = sotrudDolgnCollection;
    }
    
}
