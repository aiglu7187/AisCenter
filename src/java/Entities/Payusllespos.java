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
@Table(name = "PAYUSLLESPOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Payusllespos.findAll", query = "SELECT p FROM Payusllespos p"),
    @NamedQuery(name = "Payusllespos.findByPayusllesposId", query = "SELECT p FROM Payusllespos p WHERE p.payusllesposId = :payusllesposId"),
    @NamedQuery(name = "Payusllespos.findByPayusllessonId", query = "SELECT p FROM Payusllespos p WHERE p.payusllessonId = :payusllessonId"),
    @NamedQuery(name = "Payusllespos.findByPayuslclientId", query = "SELECT p FROM Payusllespos p WHERE p.payuslclientId = :payuslclientId"),
    @NamedQuery(name = "Payusllespos.findByDateUpd", query = "SELECT p FROM Payusllespos p WHERE p.dateUpd = :dateUpd")})
public class Payusllespos implements Serializable {

    @JoinColumn(name = "PAYUSLCLIENT_ID", referencedColumnName = "PAYUSLCLIENT_ID")
    @ManyToOne
    private PayuslClient payuslclientId;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "PAYUSLLESPOS_ID")
    @SequenceGenerator(name = "SEQ_PAYUSLLESPOS", sequenceName = "SEQ_PAYUSLLESPOS", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.IDENTITY, generator="SEQ_PAYUSLLESPOS")
    private Integer payusllesposId;
    @Column(name = "DATE_UPD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateUpd;    
    @JoinColumn(name = "PAYUSLLESSON_ID", referencedColumnName = "PAYUSLLESSON_ID")
    @ManyToOne
    private PayuslLesson payusllessonId;
    @JoinColumn(name = "SPRPOS_ID", referencedColumnName = "SPRPOS_ID")
    @ManyToOne
    private SprPos sprposId;
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
    @ManyToOne
    private Users userId;

    public Payusllespos() {
    }

    public Payusllespos(Integer payusllesposId) {
        this.payusllesposId = payusllesposId;
    }

    public Integer getPayusllesposId() {
        return payusllesposId;
    }

    public void setPayusllesposId(Integer payusllesposId) {
        this.payusllesposId = payusllesposId;
    }

    public Date getDateUpd() {
        return dateUpd;
    }

    public void setDateUpd(Date dateUpd) {
        this.dateUpd = dateUpd;
    }
    public PayuslLesson getPayusllessonId() {
        return payusllessonId;
    }

    public void setPayusllessonId(PayuslLesson payusllessonId) {
        this.payusllessonId = payusllessonId;
    }

    public SprPos getSprposId() {
        return sprposId;
    }

    public void setSprposId(SprPos sprposId) {
        this.sprposId = sprposId;
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
        hash += (payusllesposId != null ? payusllesposId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Payusllespos)) {
            return false;
        }
        Payusllespos other = (Payusllespos) object;
        if ((this.payusllesposId == null && other.payusllesposId != null) || (this.payusllesposId != null && !this.payusllesposId.equals(other.payusllesposId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Payusllespos[ payusllesposId=" + payusllesposId + " ]";
    }

    public PayuslClient getPayuslclientId() {
        return payuslclientId;
    }

    public void setPayuslclientId(PayuslClient payuslclientId) {
        this.payuslclientId = payuslclientId;
    }
    
}
