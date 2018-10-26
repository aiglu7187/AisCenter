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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Aiglu
 */
@Entity
@Table(name = "CHILD_STATUS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ChildStatus.findAll", query = "SELECT c FROM ChildStatus c"),
    @NamedQuery(name = "ChildStatus.findByChildstatusId", query = "SELECT c FROM ChildStatus c WHERE c.childstatusId = :childstatusId"),
    @NamedQuery(name = "ChildStatus.findByChildId", query = "SELECT c FROM ChildStatus c WHERE c.childId = :childId"),
    @NamedQuery(name = "ChildStatus.findByChildstatusDateN", query = "SELECT c FROM ChildStatus c WHERE c.childstatusDateN = :childstatusDateN"),
    @NamedQuery(name = "ChildStatus.findByChildstatusDateK", query = "SELECT c FROM ChildStatus c WHERE c.childstatusDateK = :childstatusDateK"),
    @NamedQuery(name = "ChildStatus.findByDateUpd", query = "SELECT c FROM ChildStatus c WHERE c.dateUpd = :dateUpd")})
public class ChildStatus implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "CHILDSTATUS_ID")
    @SequenceGenerator(name = "SEQ_CHILD_STATUS", sequenceName = "SEQ_CHILD_STATUS", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SEQ_CHILD_STATUS")
    private Integer childstatusId;
    @Column(name = "CHILDSTATUS_DATE_N")
    @Temporal(TemporalType.TIMESTAMP)
    private Date childstatusDateN;
    @Column(name = "CHILDSTATUS_DATE_K")
    @Temporal(TemporalType.TIMESTAMP)
    private Date childstatusDateK;
    @Column(name = "DATE_UPD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateUpd;
    @JoinColumn(name = "CHILD_ID", referencedColumnName = "CHILD_ID")
    @ManyToOne
    private Children childId;
    @JoinColumn(name = "SPRSTAT_ID", referencedColumnName = "SPRSTAT_ID")
    @ManyToOne
    private SprStat sprstatId;
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
    @ManyToOne
    private Users userId;

    public ChildStatus() {
    }

    public ChildStatus(Integer childstatusId) {
        this.childstatusId = childstatusId;
    }

    public Integer getChildstatusId() {
        return childstatusId;
    }

    public void setChildstatusId(Integer childstatusId) {
        this.childstatusId = childstatusId;
    }

    public Date getChildstatusDateN() {
        return childstatusDateN;
    }

    public void setChildstatusDateN(Date childstatusDateN) {
        this.childstatusDateN = childstatusDateN;
    }

    public Date getChildstatusDateK() {
        return childstatusDateK;
    }

    public void setChildstatusDateK(Date childstatusDateK) {
        this.childstatusDateK = childstatusDateK;
    }

    public Date getDateUpd() {
        return dateUpd;
    }

    public void setDateUpd(Date dateUpd) {
        this.dateUpd = dateUpd;
    }

    public Children getChildId() {
        return childId;
    }

    public void setChildId(Children childId) {
        this.childId = childId;
    }

    public SprStat getSprstatId() {
        return sprstatId;
    }

    public void setSprstatId(SprStat sprstatId) {
        this.sprstatId = sprstatId;
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
        hash += (childstatusId != null ? childstatusId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ChildStatus)) {
            return false;
        }
        ChildStatus other = (ChildStatus) object;
        if ((this.childstatusId == null && other.childstatusId != null) || (this.childstatusId != null && !this.childstatusId.equals(other.childstatusId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.ChildStatus[ childstatusId=" + childstatusId + " ]";
    }

    public String getChildstatusFormatDateK() {
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("dd.MM.yyyy");
        Date date = this.getChildstatusDateK();
        String strD = "";
        try {
            strD = format.format(date);
        } catch (Exception ex) {
        }
        return strD;
    }
    
    public String getChildstatusFormatDateN() {
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("dd.MM.yyyy");
        Date date = this.getChildstatusDateN();
        String strD = "";
        try {
            strD = format.format(date);
        } catch (Exception ex) {
        }
        return strD;
    }

}
