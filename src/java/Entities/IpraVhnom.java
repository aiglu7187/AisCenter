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
@Table(name = "IPRA_VHNOM")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IpraVhnom.findAll", query = "SELECT i FROM IpraVhnom i"),
    @NamedQuery(name = "IpraVhnom.findByIpravhnomId", query = "SELECT i FROM IpraVhnom i WHERE i.ipravhnomId = :ipravhnomId")})

public class IpraVhnom implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IPRAVHNOM_ID")
    @SequenceGenerator(name = "SEQ_IPRA_VHNOM", sequenceName = "SEQ_IPRA_VHNOM", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SEQ_IPRA_VHNOM")
    private Integer ipravhnomId;
    @Column(name = "IPRAVHNOM_NOM")
    private Integer ipravhnomNom;
    @Size(max = 10)
    @Column(name = "IPRAISHNOM_SUFFIX")
    private String ipraishnomSuffix;

    public Integer getIpravhnomId() {
        return ipravhnomId;
    }

    public void setIpravhnomId(Integer ipravhnomId) {
        this.ipravhnomId = ipravhnomId;
    }
    
    public Integer getIpravhnomNom() {
        return ipravhnomNom;
    }

    public void setIpravhnomNom(Integer ipravhnomNom) {
        this.ipravhnomNom = ipravhnomNom;
    }
    
    public String getIpraishnomSuffix() {
        return ipraishnomSuffix;
    }

    public void setIpraishnomSuffix(String ipraishnomSuffix) {
        this.ipraishnomSuffix = ipraishnomSuffix;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ipravhnomId != null ? ipravhnomId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IpraVhnom)) {
            return false;
        }
        IpraVhnom other = (IpraVhnom) object;
        if ((this.ipravhnomId == null && other.ipravhnomId != null) || (this.ipravhnomId != null && !this.ipravhnomId.equals(other.ipravhnomId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.IpraVhnom[ ipravhnomId=" + ipravhnomId + " ]";
    }
    
}
