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
 * @author Aiglu
 */
@Entity
@Table(name = "PAYUSL_LESSON")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PayuslLesson.findAll", query = "SELECT p FROM PayuslLesson p"),
    @NamedQuery(name = "PayuslLesson.findByPayusllessonId", query = "SELECT p FROM PayuslLesson p WHERE p.payusllessonId = :payusllessonId"),
    @NamedQuery(name = "PayuslLesson.findByPayuslId", query = "SELECT p FROM PayuslLesson p WHERE p.payuslId = :payuslId"),
    @NamedQuery(name = "PayuslLesson.findByPayusllessonDate", query = "SELECT p FROM PayuslLesson p WHERE p.payusllessonDate = :payusllessonDate"),
    @NamedQuery(name = "PayuslLesson.findByDateUpd", query = "SELECT p FROM PayuslLesson p WHERE p.dateUpd = :dateUpd")})
public class PayuslLesson implements Serializable {

    @OneToMany(mappedBy = "payusllessonId")
    private Collection<Payusllespos> payusllesposCollection;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "PAYUSLLESSON_ID")
    @SequenceGenerator(name = "SEQ_PAYUSL_LESSON", sequenceName = "SEQ_PAYUSL_LESSON", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SEQ_PAYUSL_LESSON")
    private Integer payusllessonId;
    @Column(name = "PAYUSLLESSON_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date payusllessonDate;
    @Column(name = "DATE_UPD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateUpd;
    @JoinColumn(name = "PAYUSL_ID", referencedColumnName = "PAYUSL_ID")
    @ManyToOne
    private PayUsl payuslId;
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
    @ManyToOne
    private Users userId;

    public PayuslLesson() {
    }

    public PayuslLesson(Integer payusllessonId) {
        this.payusllessonId = payusllessonId;
    }

    public Integer getPayusllessonId() {
        return payusllessonId;
    }

    public void setPayusllessonId(Integer payusllessonId) {
        this.payusllessonId = payusllessonId;
    }

    public Date getPayusllessonDate() {
        return payusllessonDate;
    }

    public void setPayusllessonDate(Date payusllessonDate) {
        this.payusllessonDate = payusllessonDate;
    }

    public Date getDateUpd() {
        return dateUpd;
    }

    public void setDateUpd(Date dateUpd) {
        this.dateUpd = dateUpd;
    }

    public PayUsl getPayuslId() {
        return payuslId;
    }

    public void setPayuslId(PayUsl payuslId) {
        this.payuslId = payuslId;
    }

    public Users getUserId() {
        return userId;
    }

    public void setUserId(Users userId) {
        this.userId = userId;
    }

    public String getFormatLessonDate() {
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("yyyy-MM-dd");
        Date date = this.getPayusllessonDate();
        String strDate = "";
        try {
            strDate = format.format(date);
        } catch (Exception ex) {
        }
        return strDate;
    }
    
    public String getRegularFormatLessonDate() {
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("dd.MM.yyyy");
        Date date = this.getPayusllessonDate();
        String strDate = "";
        try {
            strDate = format.format(date);
        } catch (Exception ex) {
        }
        return strDate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (payusllessonId != null ? payusllessonId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PayuslLesson)) {
            return false;
        }
        PayuslLesson other = (PayuslLesson) object;
        if ((this.payusllessonId == null && other.payusllessonId != null) || (this.payusllessonId != null && !this.payusllessonId.equals(other.payusllessonId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.PayuslLesson[ payusllessonId=" + payusllessonId + " ]";
    }

    @XmlTransient
    public Collection<Payusllespos> getPayusllesposCollection() {
        return payusllesposCollection;
    }

    public void setPayusllesposCollection(Collection<Payusllespos> payusllesposCollection) {
        this.payusllesposCollection = payusllesposCollection;
    }

}
