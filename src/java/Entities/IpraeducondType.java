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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
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
@Table(name = "IPRAEDUCOND_TYPE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IpraeducondType.findAll", query = "SELECT i FROM IpraeducondType i"),
    @NamedQuery(name = "IpraeducondType.findByIpraeducondtypeId", query = "SELECT i FROM IpraeducondType i WHERE i.ipraeducondtypeId = :ipraeducondtypeId"),
    @NamedQuery(name = "IpraeducondType.findByIpraeducondtypeName", query = "SELECT i FROM IpraeducondType i WHERE i.ipraeducondtypeName = :ipraeducondtypeName")})
public class IpraeducondType implements Serializable {

    @Column(name = "IPRAEDUCONDTYPE_OP")
    private Integer ipraeducondtypeOp;
    /*@Column(name = "IPRAEDUCONDTYPE_OP")
    private Integer ipraeducondtypeOp;*/

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IPRAEDUCONDTYPE_ID")
    @SequenceGenerator(name = "SEQ_IPRAEDUCOND_TYPE", sequenceName = "SEQ_IPRAEDUCOND_TYPE", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.IDENTITY, generator="SEQ_IPRAEDUCOND_TYPE")
    private Integer ipraeducondtypeId;
    @Size(max = 255)
    @Column(name = "IPRAEDUCONDTYPE_NAME")
    private String ipraeducondtypeName;
    @OneToMany(mappedBy = "ipraeducondtypeId")
    private Collection<SprIpraeducond> sprIpraeducondCollection;
    @OneToMany(mappedBy = "ipraeducondtypeId")
    private Collection<IpraEduCondition> ipraEduConditionCollection;

    public IpraeducondType() {
    }

    public IpraeducondType(Integer ipraeducondtypeId) {
        this.ipraeducondtypeId = ipraeducondtypeId;
    }

    public Integer getIpraeducondtypeId() {
        return ipraeducondtypeId;
    }

    public void setIpraeducondtypeId(Integer ipraeducondtypeId) {
        this.ipraeducondtypeId = ipraeducondtypeId;
    }

    public String getIpraeducondtypeName() {
        return ipraeducondtypeName;
    }

    public void setIpraeducondtypeName(String ipraeducondtypeName) {
        this.ipraeducondtypeName = ipraeducondtypeName;
    }

    @XmlTransient
    public Collection<SprIpraeducond> getSprIpraeducondCollection() {
        return sprIpraeducondCollection;
    }

    public void setSprIpraeducondCollection(Collection<SprIpraeducond> sprIpraeducondCollection) {
        this.sprIpraeducondCollection = sprIpraeducondCollection;
    }

    @XmlTransient
    public Collection<IpraEduCondition> getIpraEduConditionCollection() {
        return ipraEduConditionCollection;
    }

    public void setIpraEduConditionCollection(Collection<IpraEduCondition> ipraEduConditionCollection) {
        this.ipraEduConditionCollection = ipraEduConditionCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ipraeducondtypeId != null ? ipraeducondtypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IpraeducondType)) {
            return false;
        }
        IpraeducondType other = (IpraeducondType) object;
        if ((this.ipraeducondtypeId == null && other.ipraeducondtypeId != null) || (this.ipraeducondtypeId != null && !this.ipraeducondtypeId.equals(other.ipraeducondtypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.IpraeducondType[ ipraeducondtypeId=" + ipraeducondtypeId + " ]";
    }

    public Integer getIpraeducondtypeOp() {
        return ipraeducondtypeOp;
    }

    public void setIpraeducondtypeOp(Integer ipraeducondtypeOp) {
        this.ipraeducondtypeOp = ipraeducondtypeOp;
    }
    
    
}
