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
@Table(name = "IPRA_ISHCORR")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IpraIshcorr.findAll", query = "SELECT i FROM IpraIshcorr i"),
    @NamedQuery(name = "IpraIshcorr.findByIpraishcorrId", query = "SELECT i FROM IpraIshcorr i WHERE i.ipraishcorrId = :ipraishcorrId")})
public class IpraIshcorr implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IPRAISHCORR_ID")
    @SequenceGenerator(name = "SEQ_IPRA_ISHCORR", sequenceName = "SEQ_IPRA_ISHCORR", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SEQ_IPRA_ISHCORR")
    private Integer ipraishcorrId;
    @JoinColumn(name = "ISHCORR_ID", referencedColumnName = "ISHCORR_ID")
    @ManyToOne
    private IshCorr ishcorrId;
    @JoinColumn(name = "IPRA18_ID", referencedColumnName = "IPRA18_ID")
    @ManyToOne
    private Ipra18N ipra18Id;

    public Integer getIpraishcorrId() {
        return ipraishcorrId;
    }

    public void setIpraishcorrId(Integer ipraishcorrId) {
        this.ipraishcorrId = ipraishcorrId;
    }
    
    public IshCorr getVhcorrId() {
        return ishcorrId;
    }

    public void setIshcorrId(IshCorr ishcorrId) {
        this.ishcorrId = ishcorrId;
    }
    
    public Ipra18N getIpra18Id() {
        return ipra18Id;
    }

    public void setIpra18Id(Ipra18N ipra18Id) {
        this.ipra18Id = ipra18Id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ipraishcorrId != null ? ipraishcorrId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IpraIshcorr)) {
            return false;
        }
        IpraIshcorr other = (IpraIshcorr) object;
        if ((this.ipraishcorrId == null && other.ipraishcorrId != null) || (this.ipraishcorrId != null && !this.ipraishcorrId.equals(other.ipraishcorrId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.IpraIshcorr[ id=" + ipraishcorrId + " ]";
    }
    
}
