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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author admin_ai
 */
@Entity
@Table(name = "PRIYOM_CLIENT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PriyomClient.findAll", query = "SELECT p FROM PriyomClient p"),
    @NamedQuery(name = "PriyomClient.findByPrclId", query = "SELECT p FROM PriyomClient p WHERE p.prclId = :prclId"),
    @NamedQuery(name = "PriyomClient.findByPriyomId", query = "SELECT p FROM PriyomClient p WHERE p.priyomId = :priyomId"),
    @NamedQuery(name = "PriyomClient.findByClientId", query = "SELECT p FROM PriyomClient p WHERE p.clientId = :clientId"),
    @NamedQuery(name = "PriyomClient.findByPrclKatcl", query = "SELECT p FROM PriyomClient p WHERE p.prclKatcl = :prclKatcl"),
    @NamedQuery(name = "PriyomClient.findByPrclUdovl", query = "SELECT p FROM PriyomClient p WHERE p.prclUdovl = :prclUdovl"),
    @NamedQuery(name = "PriyomClient.findByClient", query = "SELECT p FROM PriyomClient p WHERE p.clientId = :clientId AND p.prclKatcl = :prclKatcl")})
public class PriyomClient implements Serializable {

    @Column(name = "CLIENT_ID")
    private Integer clientId;
    @Column(name = "PRCL_UDOVL")
    private Integer prclUdovl;
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
    @ManyToOne
    private Users userId;
    @OneToMany(mappedBy = "prclId")
    private Collection<Pmpk> pmpkCollection;
    @JoinColumn(name = "SPRPARENTTYPE_ID", referencedColumnName = "SPRPARENTTYPE_ID")
    @ManyToOne
    private SprParentType sprparenttypeId;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "PRCL_ID")
    @SequenceGenerator(name = "SEQ_PRIYOMCLIENT", sequenceName = "SEQ_PRIYOMCLIENT", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.IDENTITY, generator="SEQ_PRIYOMCLIENT")  
    private Integer prclId;
    @JoinColumn(name = "PRIYOM_ID", referencedColumnName = "PRIYOM_ID")
    @ManyToOne
    private Priyom priyomId;
    @Size(max = 20)
    @Column(name = "PRCL_KATCL")
    private String prclKatcl;
    @Column(name = "DATE_UPD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateUpd;

    public PriyomClient() {
    }

    public PriyomClient(Integer prclId) {
        this.prclId = prclId;
    }

    public Integer getPrclId() {
        return prclId;
    }

    public void setPrclId(Integer prclId) {
        this.prclId = prclId;
    }

    public Priyom getPriyomId() {
        return priyomId;
    }

    public void setPriyomId(Priyom priyomId) {
        this.priyomId = priyomId;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public String getPrclKatcl() {
        return prclKatcl;
    }

    public void setPrclKatcl(String prclKatcl) {
        this.prclKatcl = prclKatcl;
    }

    public Integer getPrclUdovl() {
        return prclUdovl;
    }

    public void setPrclUdovl(Integer prclUdovl) {
        this.prclUdovl = prclUdovl;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (prclId != null ? prclId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PriyomClient)) {
            return false;
        }
        PriyomClient other = (PriyomClient) object;
        if ((this.prclId == null && other.prclId != null) || (this.prclId != null && !this.prclId.equals(other.prclId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.PriyomClient[ prclId=" + prclId + " ]";
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

    @XmlTransient
    public Collection<Pmpk> getPmpkCollection() {
        return pmpkCollection;
    }

    public void setPmpkCollection(Collection<Pmpk> pmpkCollection) {
        this.pmpkCollection = pmpkCollection;
    }
    
    public SprParentType getSprparenttypeId() {
        return sprparenttypeId;
    }

    public void setSprparenttypeId(SprParentType sprparenttypeId) {
        this.sprparenttypeId = sprparenttypeId;
    }

}
