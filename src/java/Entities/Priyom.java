/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;
import java.text.SimpleDateFormat;
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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author admin_ai
 */
@Entity
@Table(name = "PRIYOM")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Priyom.findAll", query = "SELECT p FROM Priyom p ORDER BY p.priyomDate"),
    @NamedQuery(name = "Priyom.findByPriyomId", query = "SELECT p FROM Priyom p WHERE p.priyomId = :priyomId ORDER BY p.priyomDate"),
    @NamedQuery(name = "Priyom.findBySpruslId", query = "SELECT p FROM Priyom p WHERE p.spruslId = :spruslId ORDER BY p.priyomDate"),
    @NamedQuery(name = "Priyom.findBySprregId", query = "SELECT p FROM Priyom p WHERE p.sprregId = :sprregId ORDER BY p.priyomDate"),
    @NamedQuery(name = "Priyom.findByPriyomDate", query = "SELECT p FROM Priyom p WHERE p.priyomDate = :priyomDate ORDER BY p.priyomDate"),
    @NamedQuery(name = "Priyom.findByPriyomDatePeriod", query = "SELECT p FROM Priyom p WHERE p.priyomDate >= :priyomDate1 AND p.priyomDate <= :priyomDate2 ORDER BY p.priyomDate"),
    @NamedQuery(name = "Priyom.findBySprregAndSprUsl", query = "SELECT p FROM Priyom p WHERE p.spruslId = :spruslId AND p.sprregId = :sprregId ORDER BY p.priyomDate"),
    @NamedQuery(name = "Priyom.findBySprregAndDate", query = "SELECT p FROM Priyom p WHERE p.priyomDate >= :priyomDate1 AND p.priyomDate <= :priyomDate2 AND p.sprregId = :sprregId ORDER BY p.priyomDate"),
    @NamedQuery(name = "Priyom.findBySpruslAndDate", query = "SELECT p FROM Priyom p WHERE p.priyomDate >= :priyomDate1 AND p.priyomDate <= :priyomDate2 AND p.spruslId = :spruslId ORDER BY p.priyomDate"),
    @NamedQuery(name = "Priyom.findPriyom", query = "SELECT p FROM Priyom p WHERE p.priyomDate >= :priyomDate1 AND p.priyomDate <= :priyomDate2 AND "
            + "p.spruslId = :spruslId AND p.sprregId = :sprregId ORDER BY p.priyomDate")})
public class Priyom implements Serializable {

    @OneToMany(mappedBy = "priyomId")
    private Collection<PriyomSotrud> priyomSotrudCollection;

    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
    @ManyToOne
    private Users userId;
    @OneToMany(mappedBy = "priyomId")
    private Collection<PriyomSubject> priyomSubjectCollection;
    @OneToMany(mappedBy = "priyomId")
    private Collection<PriyomClient> priyomClientCollection;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "PRIYOM_ID")
    @SequenceGenerator(name = "SEQ_PRIYOM", sequenceName = "SEQ_PRIYOM", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.IDENTITY, generator="SEQ_PRIYOM")
    private Integer priyomId;
    @JoinColumn(name = "SPRUSL_ID", referencedColumnName = "SPRUSL_ID")
    @ManyToOne
    private SprUsl spruslId;
    @Column(name = "PRIYOM_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date priyomDate;
    @Column(name = "DATE_UPD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateUpd;
    @JoinColumn(name = "SPRREG_ID", referencedColumnName = "SPRREG_ID")
    @ManyToOne
    private SprRegion sprregId;
    @JoinColumn(name = "SPRPLACE_ID", referencedColumnName = "SPRPLACE_ID")
    @ManyToOne
    private SprPlaces sprplaceId;

    public Priyom() {
    }

    public Priyom(Integer priyomId) {
        this.priyomId = priyomId;
    }

    public Integer getPriyomId() {
        return priyomId;
    }

    public void setPriyomId(Integer priyomId) {
        this.priyomId = priyomId;
    }

    public SprUsl getSpruslId() {
        return spruslId;
    }

    public void setSpruslId(SprUsl spruslId) {
        this.spruslId = spruslId;
    }

    public Date getPriyomDate() {
        return priyomDate;
    }

    public void setPriyomDate(Date priyomDate) {
        this.priyomDate = priyomDate;
    }

    public SprRegion getSprregId() {
        return sprregId;
    }

    public void setSprregId(SprRegion sprregId) {
        this.sprregId = sprregId;
    }
    
    public SprPlaces getSprplaceId() {
        return sprplaceId;
    }

    public void setSprplaceId(SprPlaces sprplaceId) {
        this.sprplaceId = sprplaceId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (priyomId != null ? priyomId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Priyom)) {
            return false;
        }
        Priyom other = (Priyom) object;
        if ((this.priyomId == null && other.priyomId != null) || (this.priyomId != null && !this.priyomId.equals(other.priyomId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Priyom[ priyomId=" + priyomId + " ]";
    }
 
     public Users getUserId() {
        return userId;
    }

    public void setUserId(Users userId) {
        this.userId = userId;
    }
    
    public Date getDateUpd() {
        return dateUpd;
    }

    public void setDateUpd(Date dateUpd) {
        this.dateUpd = dateUpd;
    }
    
    public String getFormatDate(){
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("dd.MM.yyyy");
        Date datePr = this.getPriyomDate();
        String strDate = "";
        try{
            strDate  = format.format(datePr);
        }
        catch(Exception ex){            
        }
        return strDate;
    }

    @XmlTransient
    public Collection<PriyomClient> getPriyomClientCollection() {
        return priyomClientCollection;
    }

    public void setPriyomClientCollection(Collection<PriyomClient> priyomClientCollection) {
        this.priyomClientCollection = priyomClientCollection;
    }

    @XmlTransient
    public Collection<PriyomSubject> getPriyomSubjectCollection() {
        return priyomSubjectCollection;
    }

    public void setPriyomSubjectCollection(Collection<PriyomSubject> priyomSubjectCollection) {
        this.priyomSubjectCollection = priyomSubjectCollection;
    }

    @XmlTransient
    public Collection<PriyomSotrud> getPriyomSotrudCollection() {
        return priyomSotrudCollection;
    }

    public void setPriyomSotrudCollection(Collection<PriyomSotrud> priyomSotrudCollection) {
        this.priyomSotrudCollection = priyomSotrudCollection;
    }
}
