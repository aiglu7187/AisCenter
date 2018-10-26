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
@Table(name = "SPR_MSE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SprMse.findAll", query = "SELECT s FROM SprMse s"),
    @NamedQuery(name = "SprMse.findBySprmseId", query = "SELECT s FROM SprMse s WHERE s.sprmseId = :sprmseId"),
    @NamedQuery(name = "SprMse.findBySprmseName", query = "SELECT s FROM SprMse s WHERE s.sprmseName = :sprmseName"),
    @NamedQuery(name = "SprMse.findBySprmseChief", query = "SELECT s FROM SprMse s WHERE s.sprmseChief = :sprmseChief"),
    @NamedQuery(name = "SprMse.findBySprmseAdr", query = "SELECT s FROM SprMse s WHERE s.sprmseAdr = :sprmseAdr"),
    @NamedQuery(name = "SprMse.findByDateUpd", query = "SELECT s FROM SprMse s WHERE s.dateUpd = :dateUpd")})
public class SprMse implements Serializable {

    @Size(max = 100)
    @Column(name = "SPRMSE_SHNAME")
    private String sprmseShname;

    @OneToMany(mappedBy = "sprmseId")
    private Collection<Ipra18> ipra18Collection;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SPRMSE_ID")
    @SequenceGenerator(name = "SEQ_SPR_MSE", sequenceName = "SEQ_SPR_MSE", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.IDENTITY, generator="SEQ_SPR_MSE")
    private Integer sprmseId;
    @Size(max = 255)
    @Column(name = "SPRMSE_NAME")
    private String sprmseName;
    @Size(max = 255)
    @Column(name = "SPRMSE_CHIEF")
    private String sprmseChief;
    @Size(max = 255)
    @Column(name = "SPRMSE_ADR")
    private String sprmseAdr;
    @Column(name = "DATE_UPD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateUpd;
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
    @ManyToOne
    private Users userId;
    @Size(max = 255)
    @Column(name = "SPRMSE_NAME_ROD")
    private String sprmseNameRod;
    @Size(max = 255)
    @Column(name = "SPRMSE_NAME_TV")
    private String sprmseNameTv;

    public SprMse() {
    }

    public SprMse(Integer sprmseId) {
        this.sprmseId = sprmseId;
    }

    public Integer getSprmseId() {
        return sprmseId;
    }

    public void setSprmseId(Integer sprmseId) {
        this.sprmseId = sprmseId;
    }

    public String getSprmseName() {
        return sprmseName;
    }

    public void setSprmseName(String sprmseName) {
        this.sprmseName = sprmseName;
    }

    public String getSprmseChief() {
        return sprmseChief;
    }

    public void setSprmseChief(String sprmseChief) {
        this.sprmseChief = sprmseChief;
    }

    public String getSprmseAdr() {
        return sprmseAdr;
    }

    public void setSprmseAdr(String sprmseAdr) {
        this.sprmseAdr = sprmseAdr;
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
        hash += (sprmseId != null ? sprmseId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SprMse)) {
            return false;
        }
        SprMse other = (SprMse) object;
        if ((this.sprmseId == null && other.sprmseId != null) || (this.sprmseId != null && !this.sprmseId.equals(other.sprmseId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.SprMse[ sprmseId=" + sprmseId + " ]";
    }

    @XmlTransient
    public Collection<Ipra18> getIpra18Collection() {
        return ipra18Collection;
    }

    public void setIpra18Collection(Collection<Ipra18> ipra18Collection) {
        this.ipra18Collection = ipra18Collection;
    }

    public String getSprmseShname() {
        return sprmseShname;
    }

    public void setSprmseShname(String sprmseShname) {
        this.sprmseShname = sprmseShname;
    }
    
    public String getSprmseNameRod() {
        return sprmseNameRod;
    }

    public void setSprmseNameRod(String sprmseNameRod) {
        this.sprmseNameRod = sprmseNameRod;
    }
    
    public String getSprmseNameTv() {
        return sprmseNameTv;
    }

    public void setSprmseNameTv(String sprmseNameTv) {
        this.sprmseNameTv = sprmseNameTv;
    }
}
