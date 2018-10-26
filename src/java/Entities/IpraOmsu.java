/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Aiglu
 */
@Entity
@Table(name = "IPRA_OMSU")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IpraOmsu.findAll", query = "SELECT i FROM IpraOmsu i"),
    @NamedQuery(name = "IpraOmsu.findByIpraomsuId", query = "SELECT i FROM IpraOmsu i WHERE i.ipraomsuId = :ipraomsuId")})
public class IpraOmsu implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IPRAOMSU_ID")
    @SequenceGenerator(name = "SEQ_IPRA_OMSU", sequenceName = "SEQ_IPRA_OMSU", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SEQ_IPRA_OMSU")
    private Integer ipraomsuId;
    @JoinColumn(name = "SPROMSU_ID", referencedColumnName = "SPROMSU_ID")
    @ManyToOne
    private SprOmsu spromsuId;
    @Size(max = 50)
    @Column(name = "IPRAOMSU_ISH_N")
    private String ipraomsuIshN;
    @Column(name = "IPRAOMSU_ISH_D")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ipraomsuIshD;
    @Size(max = 50)
    @Column(name = "IPRAOMSU_VH_N")
    private String ipraomsuVhN;
    @Column(name = "IPRAOMSU_VH_D")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ipraomsuVhD;
    @Column(name = "IPRAOMSU_REQ")    
    private Integer ipraomsuReq;
    @JoinColumn(name = "IPRA18_ID", referencedColumnName = "IPRA18_ID")
    @ManyToOne
    private Ipra18N ipra18Id;
    @Column(name = "DATE_UPD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateUpd;
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
    @ManyToOne
    private Users userId;

    public Integer getIpraomsuId() {
        return ipraomsuId;
    }

    public void setIpraomsuId(Integer ipraomsuId) {
        this.ipraomsuId = ipraomsuId;
    }
    
    public SprOmsu getSpromsuId() {
        return spromsuId;
    }

    public void setSpromsuId(SprOmsu spromsuId) {
        this.spromsuId = spromsuId;
    }
    
    public String getIpraomsuIshN() {
        return ipraomsuIshN;
    }

    public void setIpraomsuIshN(String ipraomsuIshN) {
        this.ipraomsuIshN = ipraomsuIshN;
    }
    
    public Date getIpraomsuIshD() {
        return ipraomsuIshD;
    }

    public void setIpraomsuIshD(Date ipraomsuIshD) {
        this.ipraomsuIshD = ipraomsuIshD;
    }
    
    public String getIpraomsuVhN() {
        return ipraomsuVhN;
    }

    public void setIpraomsuVhN(String ipraomsuVhN) {
        this.ipraomsuVhN = ipraomsuVhN;
    }
    
    public Date getIpraomsuVhD() {
        return ipraomsuVhD;
    }

    public void setIpraomsuVhD(Date ipraomsuVhD) {
        this.ipraomsuVhD = ipraomsuVhD;
    }
    
    public Integer getIpraomsuReq() {
        return ipraomsuReq;
    }

    public void setIpraomsuReq(Integer ipraomsuReq) {
        this.ipraomsuReq = ipraomsuReq;
    }
    
    public Ipra18N getIpra18Id() {
        return ipra18Id;
    }

    public void setIpra18Id(Ipra18N ipra18Id) {
        this.ipra18Id = ipra18Id;
    }
    
    public Date getDateUpd() {
        return dateUpd;
    }

    public void setDateUpd(Date dateUpd) {
        this.dateUpd = dateUpd;
    }
    
    public Users getUserId() {
        return userId;
    }

    public void setUserId(Users userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ipraomsuId != null ? ipraomsuId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IpraOmsu)) {
            return false;
        }
        IpraOmsu other = (IpraOmsu) object;
        if ((this.ipraomsuId == null && other.ipraomsuId != null) || (this.ipraomsuId != null && !this.ipraomsuId.equals(other.ipraomsuId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.IpraOmsu[ ipraomsuId=" + ipraomsuId + " ]";
    }
    
}
