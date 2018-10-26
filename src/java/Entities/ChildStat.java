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
@Table(name = "CHILD_STAT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ChildStat.findAll", query = "SELECT c FROM ChildStat c"),
    @NamedQuery(name = "ChildStat.findByChildstatId", query = "SELECT c FROM ChildStat c WHERE c.childstatId = :childstatId"),
    @NamedQuery(name = "ChildStat.findByChildId", query = "SELECT c FROM ChildStat c WHERE c.childId = :childId")})
public class ChildStat implements Serializable {

    @Column(name = "CHILDSTAT_DATE_K")
    @Temporal(TemporalType.TIMESTAMP)
    private Date childstatDateK;
    @Column(name = "CHILDSTAT_DATE_N")
    @Temporal(TemporalType.TIMESTAMP)
    private Date childstatDateN;
    @Column(name = "CHILDSTAT_ACTIVE")
    private Integer childstatActive;
    @Column(name = "DATE_UPD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateUpd;
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
    @ManyToOne
    private Users userId;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "CHILDSTAT_ID")
    @SequenceGenerator(name = "SEQ_CHILDSTAT", sequenceName = "SEQ_CHILDSTAT", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.IDENTITY, generator="SEQ_CHILDSTAT")
    private Integer childstatId;
    @JoinColumn(name = "SPRSTAT_ID", referencedColumnName = "SPRSTAT_ID")
    @ManyToOne
    private SprStat sprstatId;
    @JoinColumn(name = "CHILD_ID", referencedColumnName = "CHILD_ID")
    @ManyToOne
    private Children childId;
    @JoinColumn(name = "PRIYOM_ID", referencedColumnName = "PRIYOM_ID")
    @ManyToOne
    private Priyom priyomId;
    

    public ChildStat() {
    }

    public ChildStat(Integer childstatId) {
        this.childstatId = childstatId;
    }

    public Integer getChildstatId() {
        return childstatId;
    }

    public void setChildstatId(Integer childstatId) {
        this.childstatId = childstatId;
    }

    public SprStat getSprstatId() {
        return sprstatId;
    }

    public void setSprstatId(SprStat sprstatId) {
        this.sprstatId = sprstatId;
    }

    public Children getChildId() {
        return childId;
    }

    public void setChildId(Children childId) {
        this.childId = childId;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (childstatId != null ? childstatId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ChildStat)) {
            return false;
        }
        ChildStat other = (ChildStat) object;
        if ((this.childstatId == null && other.childstatId != null) || (this.childstatId != null && !this.childstatId.equals(other.childstatId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.ChildStat[ childstatId=" + childstatId + " ]";
    }
    
    public Date getChildstatDateK() {
        return childstatDateK;
    }

    public void setChildstatDateK(Date childstatDateK) {
        this.childstatDateK = childstatDateK;
    }
    
    public Date getChildstatDateN() {
        return childstatDateN;
    }

    public void setChildstatDateN(Date childstatDateN) {
        this.childstatDateN = childstatDateN;
    }

    public Integer getChildstatActive() {
        return childstatActive;
    }

    public void setChildstatActive(Integer childstatActive) {
        this.childstatActive = childstatActive;
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
    
     public Priyom getPriyomId() {
        return priyomId;
    }

    public void setPriyomId(Priyom priyomId) {
        this.priyomId = priyomId;
    }
}
