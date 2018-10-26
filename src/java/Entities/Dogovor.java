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
@Table(name = "DOGOVOR")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Dogovor.findAll", query = "SELECT d FROM Dogovor d"),
    @NamedQuery(name = "Dogovor.findByDogId", query = "SELECT d FROM Dogovor d WHERE d.dogId = :dogId"),
    @NamedQuery(name = "Dogovor.findByDogN", query = "SELECT d FROM Dogovor d WHERE d.dogN = :dogN"),
    @NamedQuery(name = "Dogovor.findByDogDate", query = "SELECT d FROM Dogovor d WHERE d.dogDate = :dogDate"),
    @NamedQuery(name = "Dogovor.findByDogSum", query = "SELECT d FROM Dogovor d WHERE d.dogSum = :dogSum")})
public class Dogovor implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "DOG_ID")
    @SequenceGenerator(name = "SEQ_DOGOVOR", sequenceName = "SEQ_DOGOVOR", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.IDENTITY, generator="SEQ_DOGOVOR")
    private Integer dogId;
    @Size(max = 1)
    @Column(name = "DOG_N")
    private String dogN;
    @Column(name = "DOG_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dogDate;
    @Column(name = "DOG_SUM")
    private BigDecimal dogSum;
    @JoinColumn(name = "CHILD_ID", referencedColumnName = "CHILD_ID")
    @ManyToOne
    private Children childId;
    @JoinColumn(name = "GROUP_ID", referencedColumnName = "GROUP_ID")
    @ManyToOne
    private Groups groupId;
    @JoinColumn(name = "PARENT_ID", referencedColumnName = "PARENT_ID")
    @ManyToOne
    private Parents parentId;
    

    public Dogovor() {
    }

    public Dogovor(Integer dogId) {
        this.dogId = dogId;
    }

    public Integer getDogId() {
        return dogId;
    }

    public void setDogId(Integer dogId) {
        this.dogId = dogId;
    }

    public String getDogN() {
        return dogN;
    }

    public void setDogN(String dogN) {
        this.dogN = dogN;
    }

    public Date getDogDate() {
        return dogDate;
    }

    public void setDogDate(Date dogDate) {
        this.dogDate = dogDate;
    }

    public BigDecimal getDogSum() {
        return dogSum;
    }

    public void setDogSum(BigDecimal dogSum) {
        this.dogSum = dogSum;
    }

    public Children getChildId() {
        return childId;
    }

    public void setChildId(Children childId) {
        this.childId = childId;
    }

    public Groups getGroupId() {
        return groupId;
    }

    public void setGroupId(Groups groupId) {
        this.groupId = groupId;
    }

    public Parents getParentId() {
        return parentId;
    }

    public void setParentId(Parents parentId) {
        this.parentId = parentId;
    }

     @Override
    public int hashCode() {
        int hash = 0;
        hash += (dogId != null ? dogId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Dogovor)) {
            return false;
        }
        Dogovor other = (Dogovor) object;
        if ((this.dogId == null && other.dogId != null) || (this.dogId != null && !this.dogId.equals(other.dogId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Dogovor[ dogId=" + dogId + " ]";
    }
    
}
