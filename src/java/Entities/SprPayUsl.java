/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
@Table(name = "SPR_PAY_USL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SprPayUsl.findAll", query = "SELECT s FROM SprPayUsl s"),
    @NamedQuery(name = "SprPayUsl.findBySprpayuslId", query = "SELECT s FROM SprPayUsl s WHERE s.sprpayuslId = :sprpayuslId"),
    @NamedQuery(name = "SprPayUsl.findBySprpayuslName", query = "SELECT s FROM SprPayUsl s WHERE s.sprpayuslName = :sprpayuslName"),
    @NamedQuery(name = "SprPayUsl.findBySprpayuslStat", query = "SELECT s FROM SprPayUsl s WHERE s.sprpayuslStat = :sprpayuslStat"),
    @NamedQuery(name = "SprPayUsl.findBySprpayuslLesson", query = "SELECT s FROM SprPayUsl s WHERE s.sprpayuslLesson = :sprpayuslLesson"),
    @NamedQuery(name = "SprPayUsl.findBySprpayuslGroup", query = "SELECT s FROM SprPayUsl s WHERE s.sprpayuslGroup = :sprpayuslGroup")})
public class SprPayUsl implements Serializable {

    @Column(name = "SPRPAYUSL_STAT")
    private Integer sprpayuslStat;
    @Column(name = "SPRPAYUSL_LESSON")
    private Integer sprpayuslLesson;
    @Column(name = "SPRPAYUSL_GROUP")
    private Integer sprpayuslGroup;    
    @Column(name = "SPRPAYUSL_ACT")
    private Integer sprpayuslAct;
    @Column(name = "SPRPAYUSL_TIME")
    private Integer sprpayuslTime;
    /*@Column(name = "SPRPAYUSL_STAT")
    private Integer sprpayuslStat;
    @Column(name = "SPRPAYUSL_LESSON")
    private Integer sprpayuslLesson;
    @Column(name = "SPRPAYUSL_GROUP")
    private Integer sprpayuslGroup;    
    @Column(name = "SPRPAYUSL_ACT")
    private Integer sprpayuslAct;
    @Column(name = "SPRPAYUSL_TIME")
    private Integer sprpayuslTime;*/
    
    @OneToMany(mappedBy = "sprpayuslId")
    private Collection<PayUsl> payUslCollection;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "SPRPAYUSL_PRICE")
    private BigDecimal sprpayuslPrice;
    @Column(name = "SPRPAYUSL_DN")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sprpayuslDn;
    @Column(name = "SPRPAYUSL_DK")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sprpayuslDk;
    @OneToMany(mappedBy = "sprpayuslId")
    private Collection<PayuslDolgntype> payuslDolgntypeCollection;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SPRPAYUSL_ID")
    private Integer sprpayuslId;
    @Size(max = 100)
    @Column(name = "SPRPAYUSL_NAME")
    private String sprpayuslName;

    public SprPayUsl() {
    }

    public SprPayUsl(Integer sprpayuslId) {
        this.sprpayuslId = sprpayuslId;
    }

    public Integer getSprpayuslId() {
        return sprpayuslId;
    }

    public void setSprpayuslId(Integer sprpayuslId) {
        this.sprpayuslId = sprpayuslId;
    }

    public String getSprpayuslName() {
        return sprpayuslName;
    }

    public void setSprpayuslName(String sprpayuslName) {
        this.sprpayuslName = sprpayuslName;
    }

    public Integer getSprpayuslStat() {
        return sprpayuslStat;
    }

    public void setSprpayuslStat(Integer sprpayuslStat) {
        this.sprpayuslStat = sprpayuslStat;
    }

    public Integer getSprpayuslLesson() {
        return sprpayuslLesson;
    }

    public void setSprpayuslLesson(Integer sprpayuslLesson) {
        this.sprpayuslLesson = sprpayuslLesson;
    }

    public Integer getSprpayuslGroup() {
        return sprpayuslGroup;
    }

    public void setSprpayuslGroup(Integer sprpayuslGroup) {
        this.sprpayuslGroup = sprpayuslGroup;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sprpayuslId != null ? sprpayuslId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SprPayUsl)) {
            return false;
        }
        SprPayUsl other = (SprPayUsl) object;
        if ((this.sprpayuslId == null && other.sprpayuslId != null) || (this.sprpayuslId != null && !this.sprpayuslId.equals(other.sprpayuslId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.SprPayUsl[ sprpayuslId=" + sprpayuslId + " ]";
    }

    @XmlTransient
    public Collection<PayuslDolgntype> getPayuslDolgntypeCollection() {
        return payuslDolgntypeCollection;
    }

    public void setPayuslDolgntypeCollection(Collection<PayuslDolgntype> payuslDolgntypeCollection) {
        this.payuslDolgntypeCollection = payuslDolgntypeCollection;
    }

    public BigDecimal getSprpayuslPrice() {
        return sprpayuslPrice;
    }

    public void setSprpayuslPrice(BigDecimal sprpayuslPrice) {
        this.sprpayuslPrice = sprpayuslPrice;
    }

    public Integer getSprpayuslAct() {
        return sprpayuslAct;
    }

    public void setSprpayuslAct(Integer sprpayuslAct) {
        this.sprpayuslAct = sprpayuslAct;
    }

    public Date getSprpayuslDn() {
        return sprpayuslDn;
    }

    public void setSprpayuslDn(Date sprpayuslDn) {
        this.sprpayuslDn = sprpayuslDn;
    }

    public Date getSprpayuslDk() {
        return sprpayuslDk;
    }

    public void setSprpayuslDk(Date sprpayuslDk) {
        this.sprpayuslDk = sprpayuslDk;
    }

    public Integer getSprpayuslTime() {
        return sprpayuslTime;
    }

    public void setSprpayuslTime(Integer sprpayuslTime) {
        this.sprpayuslTime = sprpayuslTime;
    }

    @XmlTransient
    public Collection<PayUsl> getPayUslCollection() {
        return payUslCollection;
    }

    public void setPayUslCollection(Collection<PayUsl> payUslCollection) {
        this.payUslCollection = payUslCollection;
    }
    
}
