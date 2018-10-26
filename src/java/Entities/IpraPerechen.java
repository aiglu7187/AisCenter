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
@Table(name = "IPRA_PERECHEN")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IpraPerechen.findAll", query = "SELECT i FROM IpraPerechen i"),
    @NamedQuery(name = "IpraPerechen.findByIpraperechenId", query = "SELECT i FROM IpraPerechen i WHERE i.ipraperechenId = :ipraperechenId"),
    @NamedQuery(name = "IpraPerechen.findByIpra18Id", query = "SELECT i FROM IpraPerechen i WHERE i.ipra18Id = :ipra18Id")})
public class IpraPerechen implements Serializable {

    @OneToMany(mappedBy = "ipraperechenId")
    private Collection<IpraEduCondition> ipraEduConditionCollection;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IPRAPERECHEN_ID")
    @SequenceGenerator(name = "SEQ_IPRA_PERECHEN", sequenceName = "SEQ_IPRA_PERECHEN", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SEQ_IPRA_PERECHEN")
    private Integer ipraperechenId;
    @JoinColumn(name = "IPRA18_ID", referencedColumnName = "IPRA18_ID")
    @ManyToOne
    private Ipra18 ipra18Id;
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
    @ManyToOne
    private Users userId;
    @Column(name = "DATE_UPD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateUpd;    
    @JoinColumn(name = "VHCORR_ID", referencedColumnName = "VHCORR_ID")
    @ManyToOne
    private VhCorr vhcorrId;
    
    public IpraPerechen() {
    }

    public IpraPerechen(Integer ipraperechenId) {
        this.ipraperechenId = ipraperechenId;
    }

    public Integer getIpraperechenId() {
        return ipraperechenId;
    }

    public void setIpraperechenId(Integer ipraperechenId) {
        this.ipraperechenId = ipraperechenId;
    }

    public Ipra18 getIpra18Id() {
        return ipra18Id;
    }

    public void setIpra18Id(Ipra18 ipra18Id) {
        this.ipra18Id = ipra18Id;
    }
    
    public VhCorr getVhcorrId() {
        return vhcorrId;
    }

    public void setVhcorrId(VhCorr vhcorrId) {
        this.vhcorrId = vhcorrId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ipraperechenId != null ? ipraperechenId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IpraPerechen)) {
            return false;
        }
        IpraPerechen other = (IpraPerechen) object;
        if ((this.ipraperechenId == null && other.ipraperechenId != null) || (this.ipraperechenId != null && !this.ipraperechenId.equals(other.ipraperechenId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.IpraPerechen[ ipraperechenId=" + ipraperechenId + " ]";
    }

    @XmlTransient
    public Collection<IpraEduCondition> getIpraEduConditionCollection() {
        return ipraEduConditionCollection;
    }

    public void setIpraEduConditionCollection(Collection<IpraEduCondition> ipraEduConditionCollection) {
        this.ipraEduConditionCollection = ipraEduConditionCollection;
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
