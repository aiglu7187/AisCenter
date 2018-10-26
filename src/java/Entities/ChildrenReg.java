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
@Table(name = "CHILDREN_REG")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ChildrenReg.findAll", query = "SELECT c FROM ChildrenReg c"),
    @NamedQuery(name = "ChildrenReg.findByChildrenregId", query = "SELECT c FROM ChildrenReg c WHERE c.childrenregId = :childrenregId"),
    @NamedQuery(name = "ChildrenReg.findByChildId", query = "SELECT c FROM ChildrenReg c WHERE c.childId = :childId"),
    @NamedQuery(name = "ChildrenReg.findBySprregId", query = "SELECT c FROM ChildrenReg c WHERE c.sprregId = :sprregId"),
    @NamedQuery(name = "ChildrenReg.findByChildrenregDate", query = "SELECT c FROM ChildrenReg c WHERE c.childrenregDate = :childrenregDate")})
public class ChildrenReg implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "CHILDRENREG_ID")
    @SequenceGenerator(name = "SEQ_CHILDREN_REG", sequenceName = "SEQ_CHILDREN_REG", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.IDENTITY, generator="SEQ_CHILDREN_REG")
    private Integer childrenregId;
    @Column(name = "CHILDRENREG_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date childrenregDate;
    @JoinColumn(name = "CHILD_ID", referencedColumnName = "CHILD_ID")
    @ManyToOne
    private Children childId;
    @JoinColumn(name = "SPRREG_ID", referencedColumnName = "SPRREG_ID")
    @ManyToOne
    private SprRegion sprregId;

    public ChildrenReg() {
    }

    public ChildrenReg(Integer childrenregId) {
        this.childrenregId = childrenregId;
    }

    public Integer getChildrenregId() {
        return childrenregId;
    }

    public void setChildrenregId(Integer childrenregId) {
        this.childrenregId = childrenregId;
    }

    public Date getChildrenregDate() {
        return childrenregDate;
    }

    public void setChildrenregDate(Date childrenregDate) {
        this.childrenregDate = childrenregDate;
    }

    public Children getChildId() {
        return childId;
    }

    public void setChildId(Children childId) {
        this.childId = childId;
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
        hash += (childrenregId != null ? childrenregId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ChildrenReg)) {
            return false;
        }
        ChildrenReg other = (ChildrenReg) object;
        if ((this.childrenregId == null && other.childrenregId != null) || (this.childrenregId != null && !this.childrenregId.equals(other.childrenregId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.ChildrenReg[ childrenregId=" + childrenregId + " ]";
    }
 
    public String getFormatDate(){
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("dd.MM.yyyy");
        Date date = this.getChildrenregDate();
        String strD = "";
        try{
            strD  = format.format(date);
        }
        catch(Exception ex){            
        }
        return strD;
    }
}
