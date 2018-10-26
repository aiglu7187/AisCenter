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
 * @author admin_ai
 */
@Entity
@Table(name = "PRIYOM_SOTRUD")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PriyomSotrud.findAll", query = "SELECT p FROM PriyomSotrud p"),
    @NamedQuery(name = "PriyomSotrud.findByPrsotrId", query = "SELECT p FROM PriyomSotrud p WHERE p.prsotrId = :prsotrId"),
    @NamedQuery(name = "PriyomSotrud.findByPriyomId", query = "SELECT p FROM PriyomSotrud p WHERE p.priyomId = :priyomId")})
public class PriyomSotrud implements Serializable {

    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
    @ManyToOne
    private Users userId;    
    @JoinColumn(name = "SOTRUDDOLGN_ID", referencedColumnName = "SOTRUDDOLGN_ID")
    @ManyToOne
    private SotrudDolgn sotruddolgnId;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "PRSOTR_ID")
    @SequenceGenerator(name = "SEQ_PRIYOMSOTRUD", sequenceName = "SEQ_PRIYOMSOTRUD", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.IDENTITY, generator="SEQ_PRIYOMSOTRUD")  
    private Integer prsotrId;
    @JoinColumn(name = "PRIYOM_ID", referencedColumnName = "PRIYOM_ID")
    @ManyToOne
    private Priyom priyomId;
    @JoinColumn(name = "SOTRUD_ID", referencedColumnName = "SOTRUD_ID")
    @ManyToOne
    private Sotrud sotrudId;
    @Column(name = "DATE_UPD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateUpd;

    public PriyomSotrud() {
    }

    public PriyomSotrud(Integer prsotrId) {
        this.prsotrId = prsotrId;
    }

    public Integer getPrsotrId() {
        return prsotrId;
    }

    public void setPrsotrId(Integer prsotrId) {
        this.prsotrId = prsotrId;
    }

    public Priyom getPriyomId() {
        return priyomId;
    }

    public void setPriyomId(Priyom priyomId) {
        this.priyomId = priyomId;
    }

    public Sotrud getSotrudId() {
        return sotrudId;
    }

    public void setSotrudId(Sotrud sotrudId) {
        this.sotrudId = sotrudId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (prsotrId != null ? prsotrId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PriyomSotrud)) {
            return false;
        }
        PriyomSotrud other = (PriyomSotrud) object;
        if ((this.prsotrId == null && other.prsotrId != null) || (this.prsotrId != null && !this.prsotrId.equals(other.prsotrId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.PriyomSotrud[ prsotrId=" + prsotrId + " ]";
    }
    
    public Date getDateUpd() {
        return dateUpd;
    }

    public void setDateUpd(Date dateUpd) {
        this.dateUpd = dateUpd;
    }

    public SotrudDolgn getSotruddolgnId() {
        return sotruddolgnId;
    }

    public void setSotruddolgnId(SotrudDolgn sotruddolgnId) {
        this.sotruddolgnId = sotruddolgnId;
    }

    public Users getUserId() {
        return userId;
    }

    public void setUserId(Users userId) {
        this.userId = userId;
    }
}
