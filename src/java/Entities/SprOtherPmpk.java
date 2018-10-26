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
@Table(name = "SPR_OTHER_PMPK")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SprOtherPmpk.findAll", query = "SELECT s FROM SprOtherPmpk s ORDER BY s.sprotherpmpkShname "),
    @NamedQuery(name = "SprOtherPmpk.findBySprotherpmpkId", query = "SELECT s FROM SprOtherPmpk s WHERE s.sprotherpmpkId = :sprotherpmpkId"),
    @NamedQuery(name = "SprOtherPmpk.findBySprotherpmpkName", query = "SELECT s FROM SprOtherPmpk s WHERE s.sprotherpmpkName = :sprotherpmpkName"),
    @NamedQuery(name = "SprOtherPmpk.findBySprotherpmpkChief", query = "SELECT s FROM SprOtherPmpk s WHERE s.sprotherpmpkChief = :sprotherpmpkChief"),
    @NamedQuery(name = "SprOtherPmpk.findBySprotherpmpkAdres", query = "SELECT s FROM SprOtherPmpk s WHERE s.sprotherpmpkAdres = :sprotherpmpkAdres"),
    @NamedQuery(name = "SprOtherPmpk.findBySprotherpmpkShname", query = "SELECT s FROM SprOtherPmpk s WHERE s.sprotherpmpkShname = :sprotherpmpkShname"),
    @NamedQuery(name = "SprOtherPmpk.findByDateUpd", query = "SELECT s FROM SprOtherPmpk s WHERE s.dateUpd = :dateUpd")})
public class SprOtherPmpk implements Serializable {

    @Size(max = 100)
    @Column(name = "SPROTHERPMPK_CHIEF_FAM")
    private String sprotherpmpkChiefFam;
    @Size(max = 100)
    @Column(name = "SPROTHERPMPK_CHIEF_NAME")
    private String sprotherpmpkChiefName;
    @Size(max = 100)
    @Column(name = "SPROTHERPMPK_CHIEF_PATR")
    private String sprotherpmpkChiefPatr;
    @Size(max = 255)
    @Column(name = "SPROTHERPMPK_NAME_ROD")
    private String sprotherpmpkNameRod;

    @OneToMany(mappedBy = "sprotherpmpkId")
    private Collection<OtherpmpkRegion> otherpmpkRegionCollection;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SPROTHERPMPK_ID")
    @SequenceGenerator(name = "SEQ_SPR_OTHER_PMPK", sequenceName = "SEQ_SPR_OTHER_PMPK", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.IDENTITY, generator="SEQ_SPR_OTHER_PMPK")
    private Integer sprotherpmpkId;
    /*private Integer sprotherpmpkId;*/
    @Size(max = 255)
    @Column(name = "SPROTHERPMPK_NAME")
    private String sprotherpmpkName;
    @Size(max = 255)
    @Column(name = "SPROTHERPMPK_CHIEF")
    private String sprotherpmpkChief;
    @Size(max = 255)
    @Column(name = "SPROTHERPMPK_ADRES")
    private String sprotherpmpkAdres;
    @Size(max = 20)
    @Column(name = "SPROTHERPMPK_SHNAME")
    private String sprotherpmpkShname;
    @Column(name = "DATE_UPD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateUpd;
    @OneToMany(mappedBy = "sprotherpmpkId")
    private Collection<Ipra18Prikaz> ipra18PrikazCollection;
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
    @ManyToOne
    private Users userId;

    public SprOtherPmpk() {
    }

    public SprOtherPmpk(Integer sprotherpmpkId) {
        this.sprotherpmpkId = sprotherpmpkId;
    }

    public Integer getSprotherpmpkId() {
        return sprotherpmpkId;
    }

    public void setSprotherpmpkId(Integer sprotherpmpkId) {
        this.sprotherpmpkId = sprotherpmpkId;
    }

    public String getSprotherpmpkName() {
        return sprotherpmpkName;
    }

    public void setSprotherpmpkName(String sprotherpmpkName) {
        this.sprotherpmpkName = sprotherpmpkName;
    }

    public String getSprotherpmpkChief() {
        return sprotherpmpkChief;
    }

    public void setSprotherpmpkChief(String sprotherpmpkChief) {
        this.sprotherpmpkChief = sprotherpmpkChief;
    }

    public String getSprotherpmpkAdres() {
        return sprotherpmpkAdres;
    }

    public void setSprotherpmpkAdres(String sprotherpmpkAdres) {
        this.sprotherpmpkAdres = sprotherpmpkAdres;
    }

    public String getSprotherpmpkShname() {
        return sprotherpmpkShname;
    }

    public void setSprotherpmpkShname(String sprotherpmpkShname) {
        this.sprotherpmpkShname = sprotherpmpkShname;
    }

    public Date getDateUpd() {
        return dateUpd;
    }

    public void setDateUpd(Date dateUpd) {
        this.dateUpd = dateUpd;
    }

    @XmlTransient
    public Collection<Ipra18Prikaz> getIpra18PrikazCollection() {
        return ipra18PrikazCollection;
    }

    public void setIpra18PrikazCollection(Collection<Ipra18Prikaz> ipra18PrikazCollection) {
        this.ipra18PrikazCollection = ipra18PrikazCollection;
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
        hash += (sprotherpmpkId != null ? sprotherpmpkId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SprOtherPmpk)) {
            return false;
        }
        SprOtherPmpk other = (SprOtherPmpk) object;
        if ((this.sprotherpmpkId == null && other.sprotherpmpkId != null) || (this.sprotherpmpkId != null && !this.sprotherpmpkId.equals(other.sprotherpmpkId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.SprOtherPmpk[ sprotherpmpkId=" + sprotherpmpkId + " ]";
    }

    @XmlTransient
    public Collection<OtherpmpkRegion> getOtherpmpkRegionCollection() {
        return otherpmpkRegionCollection;
    }

    public void setOtherpmpkRegionCollection(Collection<OtherpmpkRegion> otherpmpkRegionCollection) {
        this.otherpmpkRegionCollection = otherpmpkRegionCollection;
    }

    public String getSprotherpmpkChiefFam() {
        return sprotherpmpkChiefFam;
    }

    public void setSprotherpmpkChiefFam(String sprotherpmpkChiefFam) {
        this.sprotherpmpkChiefFam = sprotherpmpkChiefFam;
    }

    public String getSprotherpmpkChiefName() {
        return sprotherpmpkChiefName;
    }

    public void setSprotherpmpkChiefName(String sprotherpmpkChiefName) {
        this.sprotherpmpkChiefName = sprotherpmpkChiefName;
    }

    public String getSprotherpmpkChiefPatr() {
        return sprotherpmpkChiefPatr;
    }

    public void setSprotherpmpkChiefPatr(String sprotherpmpkChiefPatr) {
        this.sprotherpmpkChiefPatr = sprotherpmpkChiefPatr;
    }

    public String getSprotherpmpkNameRod() {
        return sprotherpmpkNameRod;
    }

    public void setSprotherpmpkNameRod(String sprotherpmpkNameRod) {
        this.sprotherpmpkNameRod = sprotherpmpkNameRod;
    }
    
}
