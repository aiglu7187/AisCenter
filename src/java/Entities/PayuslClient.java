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
 * @author Aiglu
 */
@Entity
@Table(name = "PAYUSL_CLIENT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PayuslClient.findAll", query = "SELECT p FROM PayuslClient p"),
    @NamedQuery(name = "PayuslClient.findByPayuslclientId", query = "SELECT p FROM PayuslClient p WHERE p.payuslclientId = :payuslclientId"),
    @NamedQuery(name = "PayuslClient.findByPayuslclientKatcl", query = "SELECT p FROM PayuslClient p WHERE p.payuslclientKatcl = :payuslclientKatcl"),
    @NamedQuery(name = "PayuslClient.findByClientId", query = "SELECT p FROM PayuslClient p WHERE p.clientId = :clientId"),
    @NamedQuery(name = "PayuslClient.findByPayuslId", query = "SELECT p FROM PayuslClient p WHERE p.payuslId = :payuslId"),
    @NamedQuery(name = "PayuslClient.findByDateUpd", query = "SELECT p FROM PayuslClient p WHERE p.dateUpd = :dateUpd")})
public class PayuslClient implements Serializable {

    @Column(name = "CLIENT_ID")
    private Integer clientId;
    @OneToMany(mappedBy = "payuslclientId")
    private Collection<Payusllespos> payusllesposCollection;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "PAYUSLCLIENT_ID")
    @SequenceGenerator(name = "SEQ_PAYUSL_CLIENT", sequenceName = "SEQ_PAYUSL_CLIENT", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.IDENTITY, generator="SEQ_PAYUSL_CLIENT")
    private Integer payuslclientId;
    @Size(max = 30)
    @Column(name = "PAYUSLCLIENT_KATCL")
    private String payuslclientKatcl;
    @Column(name = "DATE_UPD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateUpd;
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
    @ManyToOne
    private Users userId;
    @JoinColumn(name = "PAYUSL_ID", referencedColumnName = "PAYUSL_ID")
    @ManyToOne
    private PayUsl payuslId;
    @Column(name = "PAYUSL_DATEN")
    @Temporal(TemporalType.TIMESTAMP)
    private Date payuslDaten;
    @Column(name = "PAYUSL_DATEK")
    @Temporal(TemporalType.TIMESTAMP)
    private Date payuslDatek;

    public PayuslClient() {
    }

    public PayuslClient(Integer payuslclientId) {
        this.payuslclientId = payuslclientId;
    }

    public Integer getPayuslclientId() {
        return payuslclientId;
    }

    public void setPayuslclientId(Integer payuslclientId) {
        this.payuslclientId = payuslclientId;
    }

    public String getPayuslclientKatcl() {
        return payuslclientKatcl;
    }

    public void setPayuslclientKatcl(String payuslclientKatcl) {
        this.payuslclientKatcl = payuslclientKatcl;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
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
    
    public PayUsl getPayuslId(){
        return payuslId;
    }
    
    public void setPayuslId(PayUsl payuslId){
        this.payuslId = payuslId;
    }
    
    public Date getPayuslDaten() {
        return payuslDaten;
    }
    
    public void setPayuslDaten(Date payuslDaten) {
        this.payuslDaten = payuslDaten;
    }
    
    public Date getPayuslDatek() {
        return payuslDatek;
    }

    public void setPayuslDatek(Date payuslDatek) {
        this.payuslDatek = payuslDatek;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (payuslclientId != null ? payuslclientId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PayuslClient)) {
            return false;
        }
        PayuslClient other = (PayuslClient) object;
        if ((this.payuslclientId == null && other.payuslclientId != null) || (this.payuslclientId != null && !this.payuslclientId.equals(other.payuslclientId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.PayuslClient[ payuslclientId=" + payuslclientId + " ]";
    }

    @XmlTransient
    public Collection<Payusllespos> getPayusllesposCollection() {
        return payusllesposCollection;
    }

    public void setPayusllesposCollection(Collection<Payusllespos> payusllesposCollection) {
        this.payusllesposCollection = payusllesposCollection;
    }

}
