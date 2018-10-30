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
@Table(name = "ISH_CORR")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IshCorr.findAll", query = "SELECT i FROM IshCorr i"),
    @NamedQuery(name = "IshCorr.findByIshcorrId", query = "SELECT i FROM IshCorr i WHERE i.ishcorrId = :ishcorrId")})
public class IshCorr implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ISHCORR_ID")
    @SequenceGenerator(name = "SEQ_ISH_CORR", sequenceName = "SEQ_ISH_CORR", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SEQ_ISH_CORR")
    private Integer ishcorrId;
    @Column(name = "ISHCORR_D")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ishcorrD;
    @Size(max = 50)
    @Column(name = "ISHCORR_N")
    private String ishcorrN;
    @JoinColumn(name = "SPRCORR_ID", referencedColumnName = "SPRCORR_ID")
    @ManyToOne
    private SprCorr sprcorrId;
    @JoinColumn(name = "SPRISHTYPE_ID", referencedColumnName = "SPRISHTYPE_ID")
    @ManyToOne
    private SprIshType sprishtypeId;
    @Column(name = "DATE_UPD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateUpd;
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
    @ManyToOne
    private Users userId;

    public IshCorr() {
    }

    public IshCorr(Integer ishcorrId) {
        this.ishcorrId = ishcorrId;
    }
    
    public Integer getIshcorrId() {
        return ishcorrId;
    }

    public void setIshcorrId(Integer ishcorrId) {
        this.ishcorrId = ishcorrId;
    }
    
    public Date getIshcorrD() {
        return ishcorrD;
    }

    public void setIshcorrD(Date ishcorrD) {
        this.ishcorrD = ishcorrD;
    }
    
    public String getIshcorrN() {
        return ishcorrN;
    }

    public void setIshcorrN(String ishcorrN) {
        this.ishcorrN = ishcorrN;
    }
        
    public SprCorr getSprcorrId() {
        return sprcorrId;
    }

    public void setSprcorrId(SprCorr sprcorrId) {
        this.sprcorrId = sprcorrId;
    }
    
    public SprIshType getSprishtypeId() {
        return sprishtypeId;
    }

    public void setSprishtypeId(SprIshType sprishtypeId) {
        this.sprishtypeId = sprishtypeId;
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
        hash += (ishcorrId != null ? ishcorrId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IshCorr)) {
            return false;
        }
        IshCorr other = (IshCorr) object;
        if ((this.ishcorrId == null && other.ishcorrId != null) || (this.ishcorrId != null && !this.ishcorrId.equals(other.ishcorrId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.IshCorr[ ishcorrId=" + ishcorrId + " ]";
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
