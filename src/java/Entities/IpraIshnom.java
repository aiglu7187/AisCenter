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
@Table(name = "IPRA_ISHNOM")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IpraIshnom.findAll", query = "SELECT i FROM IpraIshnom i"),
    @NamedQuery(name = "IpraIshnom.findByIpraishnomId", query = "SELECT i FROM IpraIshnom i WHERE i.ipraishnomId = :ipraishnomId")})
public class IpraIshnom implements Serializable {

    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IPRAISHNOM_ID")
    @SequenceGenerator(name = "SEQ_IPRA_ISHNOM", sequenceName = "SEQ_IPRA_ISHNOM", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.IDENTITY, generator="SEQ_IPRA_ISHNOM")
    private Integer ipraishnomId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "IPRAISHNOM_NOM")
    private Integer ipraishnomNom;
    @Size(max = 10)
    @Column(name = "IPRAISHNOM_SUFFIX")
    private String ipraishnomSuffix;

    private static final long serialVersionUID = 1L;    

    public IpraIshnom() {
    }

    public IpraIshnom(Integer ipraishnomId) {
        this.ipraishnomId = ipraishnomId;
    } 

    public IpraIshnom(Integer ipraishnomId, Integer ipraishnomNom) {
        this.ipraishnomId = ipraishnomId;
        this.ipraishnomNom = ipraishnomNom;
    }

    public Integer getIpraishnomId() {
        return ipraishnomId;
    }

    public void setIpraishnomId(Integer ipraishnomId) {
        this.ipraishnomId = ipraishnomId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ipraishnomId != null ? ipraishnomId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IpraIshnom)) {
            return false;
        }
        IpraIshnom other = (IpraIshnom) object;
        if ((this.ipraishnomId == null && other.ipraishnomId != null) || (this.ipraishnomId != null && !this.ipraishnomId.equals(other.ipraishnomId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.IpraIshnom[ ipraishnom=" + ipraishnomId + " ]";
    }

    public String getIpraishnomSuffix() {
        return ipraishnomSuffix;
    }

    public void setIpraishnomSuffix(String ipraishnomSuffix) {
        this.ipraishnomSuffix = ipraishnomSuffix;
    }

    public Integer getIpraishnomNom() {
        return ipraishnomNom;
    }

    public void setIpraishnomNom(Integer ipraishnomNom) {
        this.ipraishnomNom = ipraishnomNom;
    }
    
}
