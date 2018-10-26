/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Aiglu
 */
@Entity
@Table(name = "IPRA_PERECHEN_N")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IpraPerechenN.findAll", query = "SELECT i FROM IpraPerechenN i"),
    @NamedQuery(name = "IpraPerechenN.findByIpraperechennId", query = "SELECT i FROM IpraPerechenN i WHERE i.ipraperechennId = :ipraperechennId"),
    @NamedQuery(name = "IpraPerechenN.findByIpra18Id", query = "SELECT i FROM IpraPerechenN i WHERE i.ipra18Id = :ipra18Id")})
public class IpraPerechenN implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IPRAPERECHENN_ID")
    @SequenceGenerator(name = "SEQ_IPRA_PERECHEN_N", sequenceName = "SEQ_IPRA_PERECHEN_N", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SEQ_IPRA_PERECHEN_N")
    private Integer ipraperechennId;
    @JoinColumn(name = "IPRA18_ID", referencedColumnName = "IPRA18_ID")
    @ManyToOne
    private Ipra18N ipra18Id;
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
    @ManyToOne
    private Users userId;
    @Column(name = "DATE_UPD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateUpd;    
    @JoinColumn(name = "VHCORR_ID", referencedColumnName = "VHCORR_ID")
    @ManyToOne
    private VhCorr vhcorrId;
    @JoinColumn(name = "ISHCORR_ID", referencedColumnName = "ISHCORR_ID")
    @ManyToOne
    private IshCorr ishcorrId;
    
    
    public IpraPerechenN() {
    }

    public IpraPerechenN(Integer ipraperechennId) {
        this.ipraperechennId = ipraperechennId;
    }

    public Integer getIpraperechennId() {
        return ipraperechennId;
    }

    public void setIpraperechennId(Integer ipraperechennId) {
        this.ipraperechennId = ipraperechennId;
    }

    public Ipra18N getIpra18Id() {
        return ipra18Id;
    }

    public void setIpra18Id(Ipra18N ipra18Id) {
        this.ipra18Id = ipra18Id;
    }
    
    public VhCorr getVhcorrId() {
        return vhcorrId;
    }

    public void setVhcorrId(VhCorr vhcorrId) {
        this.vhcorrId = vhcorrId;
    }
    
    public IshCorr getIshcorrId() {
        return ishcorrId;
    }

    public void setIshcorrId(IshCorr ishcorrId) {
        this.ishcorrId = ishcorrId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ipraperechennId != null ? ipraperechennId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IpraPerechenN)) {
            return false;
        }
        IpraPerechenN other = (IpraPerechenN) object;
        if ((this.ipraperechennId == null && other.ipraperechennId != null) || (this.ipraperechennId != null && !this.ipraperechennId.equals(other.ipraperechennId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.IpraPerechenN[ ipraperechennId=" + ipraperechennId + " ]";
    }

    public Users getUserId() {
        return userId;
    }

    public void setUserId(Users userId) {
        this.userId = userId;
    }

    public Date getDateUpd() {
        return dateUpd;
    }

    public void setDateUpd(Date dateUpd) {
        this.dateUpd = dateUpd;
    }
}
