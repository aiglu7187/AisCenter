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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Aiglu
 */
@Entity
@Table(name = "PAY_USL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PayUsl.findAll", query = "SELECT p FROM PayUsl p"),
    @NamedQuery(name = "PayUsl.findByPayuslId", query = "SELECT p FROM PayUsl p WHERE p.payuslId = :payuslId"),
    @NamedQuery(name = "PayUsl.findBySprpayuslId", query = "SELECT p FROM PayUsl p WHERE p.sprpayuslId = :sprpayuslId"),
    @NamedQuery(name = "PayUsl.findByPayuslStatus", query = "SELECT p FROM PayUsl p WHERE p.payuslStatus = :payuslStatus"),
    @NamedQuery(name = "PayUsl.findByPayuslName", query = "SELECT p FROM PayUsl p WHERE p.payuslName = :payuslName"),
    @NamedQuery(name = "PayUsl.findByPayuslDate", query = "SELECT p FROM PayUsl p WHERE p.payuslDate = :payuslDate"),
    @NamedQuery(name = "PayUsl.findByDateUpd", query = "SELECT p FROM PayUsl p WHERE p.dateUpd = :dateUpd")})
public class PayUsl implements Serializable {

    @OneToMany(mappedBy = "payuslId")
    private Collection<PayuslClient> payuslClientCollection;

    @OneToMany(mappedBy = "payuslId")
    private Collection<PayuslLesson> payuslLessonCollection;

    @OneToMany(mappedBy = "payuslId")
    private Collection<PayuslSotrud> payuslSotrudCollection;

    @OneToMany(mappedBy = "payuslId")
    private Collection<PayDogovor> payDogovorCollection;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "PAYUSL_ID")
    @SequenceGenerator(name = "SEQ_PAY_USL", sequenceName = "SEQ_PAY_USL", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SEQ_PAY_USL")
    private Integer payuslId;
    @JoinColumn(name = "SPRPAYUSL_ID", referencedColumnName = "SPRPAYUSL_ID")
    @ManyToOne
    private SprPayUsl sprpayuslId;
    @Size(max = 15)
    @Column(name = "PAYUSL_STATUS")
    private String payuslStatus;
    @Size(max = 50)
    @Column(name = "PAYUSL_NAME")
    private String payuslName;
    @Column(name = "PAYUSL_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date payuslDate;
    @Column(name = "DATE_UPD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateUpd;
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
    @ManyToOne
    private Users userId;

    public PayUsl() {
    }

    public PayUsl(Integer payuslId) {
        this.payuslId = payuslId;
    }

    public Integer getPayuslId() {
        return payuslId;
    }

    public void setPayuslId(Integer payuslId) {
        this.payuslId = payuslId;
    }

    public SprPayUsl getSprpayuslId() {
        return sprpayuslId;
    }

    public void setSprpayuslId(SprPayUsl sprpayuslId) {
        this.sprpayuslId = sprpayuslId;
    }

    public String getPayuslStatus() {
        return payuslStatus;
    }

    public void setPayuslStatus(String payuslStatus) {
        this.payuslStatus = payuslStatus;
    }

    public String getPayuslName() {
        return payuslName;
    }

    public void setPayuslName(String payuslName) {
        this.payuslName = payuslName;
    }

    public Date getPayuslDate() {
        return payuslDate;
    }

    public void setPayuslDate(Date payuslDate) {
        this.payuslDate = payuslDate;
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

    public String getRegularFormatDate() {
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("dd.MM.yyyy");
        Date date = this.getPayuslDate();
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
        hash += (payuslId != null ? payuslId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PayUsl)) {
            return false;
        }
        PayUsl other = (PayUsl) object;
        if ((this.payuslId == null && other.payuslId != null) || (this.payuslId != null && !this.payuslId.equals(other.payuslId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.PayUsl[ payuslId=" + payuslId + " ]";
    }

    @XmlTransient
    public Collection<PayDogovor> getPayDogovorCollection() {
        return payDogovorCollection;
    }

    public void setPayDogovorCollection(Collection<PayDogovor> payDogovorCollection) {
        this.payDogovorCollection = payDogovorCollection;
    }

    @XmlTransient
    public Collection<PayuslSotrud> getPayuslSotrudCollection() {
        return payuslSotrudCollection;
    }

    public void setPayuslSotrudCollection(Collection<PayuslSotrud> payuslSotrudCollection) {
        this.payuslSotrudCollection = payuslSotrudCollection;
    }

    @XmlTransient
    public Collection<PayuslLesson> getPayuslLessonCollection() {
        return payuslLessonCollection;
    }

    public void setPayuslLessonCollection(Collection<PayuslLesson> payuslLessonCollection) {
        this.payuslLessonCollection = payuslLessonCollection;
    }

    @XmlTransient
    public Collection<PayuslClient> getPayuslClientCollection() {
        return payuslClientCollection;
    }

    public void setPayuslClientCollection(Collection<PayuslClient> payuslClientCollection) {
        this.payuslClientCollection = payuslClientCollection;
    }

}
