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
 * @author admin_ai
 */
@Entity
@Table(name = "PARENTS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Parents.findAll", query = "SELECT p FROM Parents p ORDER BY p.parentFam, p.parentName, p.parentPatr"),
    @NamedQuery(name = "Parents.findByParentId", query = "SELECT p FROM Parents p WHERE p.parentId = :parentId"),
    @NamedQuery(name = "Parents.findByParentFam", query = "SELECT p FROM Parents p WHERE p.parentFam = :parentFam"),
    @NamedQuery(name = "Parents.findByParentName", query = "SELECT p FROM Parents p WHERE p.parentName = :parentName"),
    @NamedQuery(name = "Parents.findByParentPatr", query = "SELECT p FROM Parents p WHERE p.parentPatr = :parentPatr"),
    @NamedQuery(name = "Parents.findByParentNom", query = "SELECT p FROM Parents p WHERE p.parentNom = :parentNom")})

public class Parents implements Serializable {

    @Column(name = "PARENT_NOM")
    private Integer parentNom;  
    @OneToMany(mappedBy = "parentId")
    private Collection<PmpkParent> pmpkParentCollection;
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
    @ManyToOne
    private Users userId;    
    @OneToMany(mappedBy = "parentId")
    private Collection<PayDogovor> payDogovorCollection;
    @OneToMany(mappedBy = "parentId")
    private Collection<Dogovor> dogovorCollection;
    @OneToMany(mappedBy = "parentId")
    private Collection<Telephon> telephonCollection;
    @OneToMany(mappedBy = "parentId")
    private Collection<ParentsReg> parentsRegCollection;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "PARENT_ID")
    @SequenceGenerator(name = "SEQ_PARENTS", sequenceName = "SEQ_PARENTS", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.IDENTITY, generator="SEQ_PARENTS")    
    private Integer parentId;
    @Size(max = 100)
    @Column(name = "PARENT_FAM")
    private String parentFam;
    @Size(max = 100)
    @Column(name = "PARENT_NAME")
    private String parentName;
    @Size(max = 100)
    @Column(name = "PARENT_PATR")
    private String parentPatr;
    @Column(name = "DATE_UPD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateUpd;
    @JoinColumn(name = "SPRREG_ID", referencedColumnName = "SPRREG_ID")
    @ManyToOne
    private SprRegion sprregId;

    public Parents() {
    }

    public Parents(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getParentFam() {
        return parentFam;
    }

    public void setParentFam(String parentFam) {
        this.parentFam = parentFam;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getParentPatr() {
        return parentPatr;
    }

    public void setParentPatr(String parentPatr) {
        this.parentPatr = parentPatr;
    }

    public Integer getParentNom() {
        return parentNom;
    }

    public void setParentNom(Integer parentNom) {
        this.parentNom = parentNom;
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
        hash += (parentId != null ? parentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Parents)) {
            return false;
        }
        Parents other = (Parents) object;
        if ((this.parentId == null && other.parentId != null) || (this.parentId != null && !this.parentId.equals(other.parentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Parents[ parentId=" + parentId + " ]";
    }
    
    public String getFIO(){
        if (parentPatr != null){
            return parentFam + " " + parentName + " " + parentPatr;
        }
        else {
            return parentFam + " " + parentName;
        }
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

    @XmlTransient
    public Collection<ParentsReg> getParentsRegCollection() {
        return parentsRegCollection;
    }

    public void setParentsRegCollection(Collection<ParentsReg> parentsRegCollection) {
        this.parentsRegCollection = parentsRegCollection;
    }

    @XmlTransient
    public Collection<Dogovor> getDogovorCollection() {
        return dogovorCollection;
    }

    public void setDogovorCollection(Collection<Dogovor> dogovorCollection) {
        this.dogovorCollection = dogovorCollection;
    }

    @XmlTransient
    public Collection<Telephon> getTelephonCollection() {
        return telephonCollection;
    }

    public void setTelephonCollection(Collection<Telephon> telephonCollection) {
        this.telephonCollection = telephonCollection;
    }

    @XmlTransient
    public Collection<PayDogovor> getPayDogovorCollection() {
        return payDogovorCollection;
    }

    public void setPayDogovorCollection(Collection<PayDogovor> payDogovorCollection) {
        this.payDogovorCollection = payDogovorCollection;
    }

    @XmlTransient
    public Collection<PmpkParent> getPmpkParentCollection() {
        return pmpkParentCollection;
    }

    public void setPmpkParentCollection(Collection<PmpkParent> pmpkParentCollection) {
        this.pmpkParentCollection = pmpkParentCollection;
    }

}
