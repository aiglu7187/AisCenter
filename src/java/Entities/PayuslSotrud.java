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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Aiglu
 */
@Entity
@Table(name = "PAYUSL_SOTRUD")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PayuslSotrud.findAll", query = "SELECT p FROM PayuslSotrud p"),
    @NamedQuery(name = "PayuslSotrud.findByPayuslsotrId", query = "SELECT p FROM PayuslSotrud p WHERE p.payuslsotrId = :payuslsotrId"),
    @NamedQuery(name = "PayuslSotrud.findByDateUpd", query = "SELECT p FROM PayuslSotrud p WHERE p.dateUpd = :dateUpd"),
    @NamedQuery(name = "PayuslSotrud.findByPayuslId", query = "SELECT p FROM PayuslSotrud p WHERE p.payuslId = :payuslId")})
public class PayuslSotrud implements Serializable {

    @JoinColumn(name = "SOTRUDDOLGN_ID", referencedColumnName = "SOTRUDDOLGN_ID")
    @ManyToOne
    private SotrudDolgn sotruddolgnId;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "PAYUSLSOTR_ID")
    @SequenceGenerator(name = "SEQ_PAYUSL_SOTRUD", sequenceName = "SEQ_PAYUSL_SOTRUD", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.IDENTITY, generator="SEQ_PAYUSL_SOTRUD")
    private Integer payuslsotrId;
    @Column(name = "DATE_UPD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateUpd;
    @JoinColumn(name = "PAYUSL_ID", referencedColumnName = "PAYUSL_ID")
    @ManyToOne
    private PayUsl payuslId;
    @JoinColumn(name = "SOTRUD_ID", referencedColumnName = "SOTRUD_ID")
    @ManyToOne
    private Sotrud sotrudId;
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
    @ManyToOne
    private Users userId;

    public PayuslSotrud() {
    }

    public PayuslSotrud(Integer payuslsotrId) {
        this.payuslsotrId = payuslsotrId;
    }

    public Integer getPayuslsotrId() {
        return payuslsotrId;
    }

    public void setPayuslsotrId(Integer payuslsotrId) {
        this.payuslsotrId = payuslsotrId;
    }

    public Date getDateUpd() {
        return dateUpd;
    }

    public void setDateUpd(Date dateUpd) {
        this.dateUpd = dateUpd;
    }

    public PayUsl getPayuslId() {
        return payuslId;
    }

    public void setPayuslId(PayUsl payuslId) {
        this.payuslId = payuslId;
    }

    public Sotrud getSotrudId() {
        return sotrudId;
    }

    public void setSotrudId(Sotrud sotrudId) {
        this.sotrudId = sotrudId;
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
        hash += (payuslsotrId != null ? payuslsotrId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PayuslSotrud)) {
            return false;
        }
        PayuslSotrud other = (PayuslSotrud) object;
        if ((this.payuslsotrId == null && other.payuslsotrId != null) || (this.payuslsotrId != null && !this.payuslsotrId.equals(other.payuslsotrId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.PayuslSotrud[ payuslsotrId=" + payuslsotrId + " ]";
    }

    public SotrudDolgn getSotruddolgnId() {
        return sotruddolgnId;
    }

    public void setSotruddolgnId(SotrudDolgn sotruddolgnId) {
        this.sotruddolgnId = sotruddolgnId;
    }
    
}
