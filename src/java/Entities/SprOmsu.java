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
@Table(name = "SPR_OMSU")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SprOmsu.findAll", query = "SELECT s FROM SprOmsu s"),
    @NamedQuery(name = "SprOmsu.findBySpromsuId", query = "SELECT s FROM SprOmsu s WHERE s.spromsuId = :spromsuId"),
    @NamedQuery(name = "SprOmsu.findBySpromsuName", query = "SELECT s FROM SprOmsu s WHERE s.spromsuName = :spromsuName"),
    @NamedQuery(name = "SprOmsu.findBySpromsuChief", query = "SELECT s FROM SprOmsu s WHERE s.spromsuChief = :spromsuChief"),
    @NamedQuery(name = "SprOmsu.findBySpromsuAdr", query = "SELECT s FROM SprOmsu s WHERE s.spromsuAdr = :spromsuAdr"),
    @NamedQuery(name = "SprOmsu.findByDateUpd", query = "SELECT s FROM SprOmsu s WHERE s.dateUpd = :dateUpd"),
    @NamedQuery(name = "SprOmsu.findBySprregId", query = "SELECT s FROM SprOmsu s WHERE s.sprregId = :sprregId")})
public class SprOmsu implements Serializable {

    @Size(max = 255)
    @Column(name = "SPROMSU_NAME_ROD")
    private String spromsuNameRod;
    @Size(max = 100)
    @Column(name = "SPROMSU_CHIEF_FAM")
    private String spromsuChiefFam;
    @Size(max = 100)
    @Column(name = "SPROMSU_CHIEF_NAME")
    private String spromsuChiefName;
    @Size(max = 100)
    @Column(name = "SPROMSU_CHIEF_PATR")
    private String spromsuChiefPatr;
    @Size(max = 100)
    @Column(name = "SPROMSU_CHIEF_DOLGN")
    private String spromsuChiefDolgn;
    @Size(max = 100)
    @Column(name = "SPROMSU_CHIEF_DOLGN_DAT")
    private String spromsuChiefDolgnDat;

    @OneToMany(mappedBy = "spromsuId")
    private Collection<Ipra18Prikaz> ipra18PrikazCollection;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SPROMSU_ID")
    @SequenceGenerator(name = "SEQ_SPR_OMSU", sequenceName = "SEQ_SPR_OMSU", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SEQ_SPR_OMSU")
    private Integer spromsuId;
    @Size(max = 255)
    @Column(name = "SPROMSU_NAME")
    private String spromsuName;
    @Size(max = 255)
    @Column(name = "SPROMSU_CHIEF")
    private String spromsuChief;
    @Size(max = 255)
    @Column(name = "SPROMSU_ADR")
    private String spromsuAdr;
    @Column(name = "DATE_UPD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateUpd;
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
    @ManyToOne
    private Users userId;
    @JoinColumn(name = "SPRREG_ID", referencedColumnName = "SPRREG_ID")
    @ManyToOne
    private SprRegion sprregId;
    @Size(max = 255)
    @Column(name = "SPROMSU_NAME_DAT")
    private String spromsuNameDat;

    public SprOmsu() {
    }

    public SprOmsu(Integer spromsuId) {
        this.spromsuId = spromsuId;
    }

    public Integer getSpromsuId() {
        return spromsuId;
    }

    public void setSpromsuId(Integer spromsuId) {
        this.spromsuId = spromsuId;
    }

    public String getSpromsuName() {
        return spromsuName;
    }

    public void setSpromsuName(String spromsuName) {
        this.spromsuName = spromsuName;
    }

    public String getSpromsuChief() {
        return spromsuChief;
    }

    public void setSpromsuChief(String spromsuChief) {
        this.spromsuChief = spromsuChief;
    }

    public String getSpromsuAdr() {
        return spromsuAdr;
    }

    public void setSpromsuAdr(String spromsuAdr) {
        this.spromsuAdr = spromsuAdr;
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

    public SprRegion getSprregId() {
        return sprregId;
    }

    public void setSprregId(SprRegion sprregId) {
        this.sprregId = sprregId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (spromsuId != null ? spromsuId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SprOmsu)) {
            return false;
        }
        SprOmsu other = (SprOmsu) object;
        if ((this.spromsuId == null && other.spromsuId != null) || (this.spromsuId != null && !this.spromsuId.equals(other.spromsuId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.SprOmsu[ spromsuId=" + spromsuId + " ]";
    }

    @XmlTransient
    public Collection<Ipra18Prikaz> getIpra18PrikazCollection() {
        return ipra18PrikazCollection;
    }

    public void setIpra18PrikazCollection(Collection<Ipra18Prikaz> ipra18PrikazCollection) {
        this.ipra18PrikazCollection = ipra18PrikazCollection;
    }

    public String getSpromsuNameDat() {
        return spromsuNameDat;
    }

    public void setSpromsuNameDat(String spromsuNameDat) {
        this.spromsuNameDat = spromsuNameDat;
    }

    public String getSpromsuNameRod() {
        return spromsuNameRod;
    }

    public void setSpromsuNameRod(String spromsuNameRod) {
        this.spromsuNameRod = spromsuNameRod;
    }

    public String getSpromsuChiefFam() {
        return spromsuChiefFam;
    }

    public void setSpromsuChiefFam(String spromsuChiefFam) {
        this.spromsuChiefFam = spromsuChiefFam;
    }

    public String getSpromsuChiefName() {
        return spromsuChiefName;
    }

    public void setSpromsuChiefName(String spromsuChiefName) {
        this.spromsuChiefName = spromsuChiefName;
    }

    public String getSpromsuChiefPatr() {
        return spromsuChiefPatr;
    }

    public void setSpromsuChiefPatr(String spromsuChiefPatr) {
        this.spromsuChiefPatr = spromsuChiefPatr;
    }

    public String getSpromsuChiefDolgn() {
        return spromsuChiefDolgn;
    }

    public void setSpromsuChiefDolgn(String spromsuChiefDolgn) {
        this.spromsuChiefDolgn = spromsuChiefDolgn;
    }

    public String getSpromsuChiefDolgnDat() {
        return spromsuChiefDolgnDat;
    }

    public void setSpromsuChiefDolgnDat(String spromsuChiefDolgnDat) {
        this.spromsuChiefDolgnDat = spromsuChiefDolgnDat;
    }
}
