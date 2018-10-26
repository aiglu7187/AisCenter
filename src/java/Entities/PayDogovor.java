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
@Table(name = "PAY_DOGOVOR")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PayDogovor.findAll", query = "SELECT p FROM PayDogovor p"),
    @NamedQuery(name = "PayDogovor.findByPaydogId", query = "SELECT p FROM PayDogovor p WHERE p.paydogId = :paydogId"),
    @NamedQuery(name = "PayDogovor.findByDateUpd", query = "SELECT p FROM PayDogovor p WHERE p.dateUpd = :dateUpd")})
public class PayDogovor implements Serializable {

    @Size(max = 10)
    @Column(name = "PAYDOG_N")
    private String paydogN;
    @Column(name = "PAYDOG_D")
    @Temporal(TemporalType.TIMESTAMP)
    private Date paydogD;

    @OneToMany(mappedBy = "paydogId")
    private Collection<Payment> paymentCollection;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "PAYDOG_ID")
    @SequenceGenerator(name = "SEQ_PAY_DOGOVOR", sequenceName = "SEQ_PAY_DOGOVOR", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.IDENTITY, generator="SEQ_PAY_DOGOVOR")  
    private Integer paydogId;
    @Column(name = "DATE_UPD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateUpd;
    @JoinColumn(name = "CHILD_ID", referencedColumnName = "CHILD_ID")
    @ManyToOne
    private Children childId;
    @JoinColumn(name = "PARENT_ID", referencedColumnName = "PARENT_ID")
    @ManyToOne
    private Parents parentId;
    @JoinColumn(name = "PAYUSL_ID", referencedColumnName = "PAYUSL_ID")
    @ManyToOne
    private PayUsl payuslId;
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
    @ManyToOne
    private Users userId;

    public PayDogovor() {
    }

    public PayDogovor(Integer paydogId) {
        this.paydogId = paydogId;
    }

    public Integer getPaydogId() {
        return paydogId;
    }

    public void setPaydogId(Integer paydogId) {
        this.paydogId = paydogId;
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

    public Parents getParentId() {
        return parentId;
    }

    public void setParentId(Parents parentId) {
        this.parentId = parentId;
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
    
    public String getFormatDateDog(){
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("yyyy-MM-dd");
        Date date = this.getPaydogD();
        String strD = "";
        try{
            strD  = format.format(date);
        }
        catch(Exception ex){            
        }
        return strD;
    }
    
    public String getRegularFormatDateDog(){
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("dd.MM.yyyy");
        Date date = this.getPaydogD();
        String strD = "";
        try{
            strD  = format.format(date);
        }
        catch(Exception ex){            
        }
        return strD;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (paydogId != null ? paydogId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PayDogovor)) {
            return false;
        }
        PayDogovor other = (PayDogovor) object;
        if ((this.paydogId == null && other.paydogId != null) || (this.paydogId != null && !this.paydogId.equals(other.paydogId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.PayDogovor[ paydogId=" + paydogId + " ]";
    }

    @XmlTransient
    public Collection<Payment> getPaymentCollection() {
        return paymentCollection;
    }

    public void setPaymentCollection(Collection<Payment> paymentCollection) {
        this.paymentCollection = paymentCollection;
    }

    public String getPaydogN() {
        return paydogN;
    }

    public void setPaydogN(String paydogN) {
        this.paydogN = paydogN;
    }

    public Date getPaydogD() {
        return paydogD;
    }

    public void setPaydogD(Date paydogD) {
        this.paydogD = paydogD;
    }
    
}
