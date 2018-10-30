/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;
import java.text.SimpleDateFormat;
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
@Table(name = "VH_CORR")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VhCorr.findAll", query = "SELECT v FROM VhCorr v"),
    @NamedQuery(name = "VhCorr.findByIpravhnomId", query = "SELECT v FROM VhCorr v WHERE v.vhcorrId = :vhcorrId")})
public class VhCorr implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "VHCORR_ID")
    @SequenceGenerator(name = "SEQ_VH_CORR", sequenceName = "SEQ_VH_CORR", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SEQ_VH_CORR")
    private Integer vhcorrId;
    @Column(name = "VHCORR_D")
    @Temporal(TemporalType.TIMESTAMP)
    private Date vhcorrD;
    @Size(max = 50)
    @Column(name = "VHCORR_N")
    private String vhcorrN;
    @Column(name = "VHCORR_ISHD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date vhcorrIshd;
    @Size(max = 50)
    @Column(name = "VHCORR_ISHN")
    private String vhcorrIshn;
    @JoinColumn(name = "SPRCORR_ID", referencedColumnName = "SPRCORR_ID")
    @ManyToOne
    private SprCorr sprcorrId;
    @JoinColumn(name = "SPRVHTYPE_ID", referencedColumnName = "SPRVHTYPE_ID")
    @ManyToOne
    private SprVhType sprvhtypeId;
    @Column(name = "DATE_UPD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateUpd;
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
    @ManyToOne
    private Users userId;

    public Integer getVhcorrId() {
        return vhcorrId;
    }

    public void setVhcorrId(Integer vhcorrId) {
        this.vhcorrId = vhcorrId;
    }
    
    public Date getVhcorrD() {
        return vhcorrD;
    }

    public void setVhcorrD(Date vhcorrD) {
        this.vhcorrD = vhcorrD;
    }
    
    public String getVhcorrN() {
        return vhcorrN;
    }

    public void setVhcorrN(String vhcorrN) {
        this.vhcorrN = vhcorrN;
    }
    
    public Date getVhcorrIshd() {
        return vhcorrIshd;
    }

    public void setVhcorrIshd(Date vhcorrIshd) {
        this.vhcorrIshd = vhcorrIshd;
    }
    
    public String getVhcorrIshn() {
        return vhcorrIshn;
    }

    public void setVhcorrIshn(String vhcorrIshn) {
        this.vhcorrIshn = vhcorrIshn;
    }
    
    public SprCorr getSprcorrId() {
        return sprcorrId;
    }

    public void setSprcorrId(SprCorr sprcorrId) {
        this.sprcorrId = sprcorrId;
    }
    
    public SprVhType getSprvhtypeId() {
        return sprvhtypeId;
    }

    public void setSprvhtypeId(SprVhType sprvhtypeId) {
        this.sprvhtypeId = sprvhtypeId;
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
        hash += (vhcorrId != null ? vhcorrId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VhCorr)) {
            return false;
        }
        VhCorr other = (VhCorr) object;
        if ((this.vhcorrId == null && other.vhcorrId != null) || (this.vhcorrId != null && !this.vhcorrId.equals(other.vhcorrId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.VhCorr[ vhcorrId=" + vhcorrId + " ]";
    }
    
    public String getFormatDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("yyyy-MM-dd");
        String strDate = "";
        try {
            strDate = format.format(date);
        } catch (Exception ex) {
        }
        return strDate;
    }
    
    public String getFormat2Date(Date date) {
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("dd.MM.yyyy");
        String strDate = "";
        try {
            strDate = format.format(date);
        } catch (Exception ex) {
        }
        return strDate;
    }
    
}
