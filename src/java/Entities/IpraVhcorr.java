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
@Table(name = "IPRA_VHCORR")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IpraVhcorr.findAll", query = "SELECT i FROM IpraVhcorr i"),
    @NamedQuery(name = "IpraVhcorr.findByIpravhcorrId", query = "SELECT i FROM IpraVhcorr i WHERE i.ipravhcorrId = :ipravhcorrId")})
public class IpraVhcorr implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IPRAVHCORR_ID")
    @SequenceGenerator(name = "SEQ_IPRA_VHCORR", sequenceName = "SEQ_IPRA_VHCORR", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SEQ_IPRA_VHCORR")
    private Integer ipravhcorrId;
    @JoinColumn(name = "VHCORR_ID", referencedColumnName = "VHCORR_ID")
    @ManyToOne
    private VhCorr vhcorrId;
    @JoinColumn(name = "IPRA18_ID", referencedColumnName = "IPRA18_ID")
    @ManyToOne
    private Ipra18N ipra18Id;

    public Integer getIpravhcorrId() {
        return ipravhcorrId;
    }

    public void setIpravhcorrId(Integer ipravhcorrId) {
        this.ipravhcorrId = ipravhcorrId;
    }
    
    public VhCorr getVhcorrId() {
        return vhcorrId;
    }

    public void setVhcorrId(VhCorr vhcorrId) {
        this.vhcorrId = vhcorrId;
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
        hash += (ipravhcorrId != null ? ipravhcorrId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IpraVhcorr)) {
            return false;
        }
        IpraVhcorr other = (IpraVhcorr) object;
        if ((this.ipravhcorrId == null && other.ipravhcorrId != null) || (this.ipravhcorrId != null && !this.ipravhcorrId.equals(other.ipravhcorrId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.IpraVhcorr[ ipravhcorrId=" + ipravhcorrId + " ]";
    }
    
}
