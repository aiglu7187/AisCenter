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
@Table(name = "PARENTS_REG")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ParentsReg.findAll", query = "SELECT p FROM ParentsReg p"),
    @NamedQuery(name = "ParentsReg.findByParentsregId", query = "SELECT p FROM ParentsReg p WHERE p.parentsregId = :parentsregId"),
    @NamedQuery(name = "ParentsReg.findByParentId", query = "SELECT p FROM ParentsReg p WHERE p.parentId = :parentId"),
    @NamedQuery(name = "ParentsReg.findBySprregId", query = "SELECT p FROM ParentsReg p WHERE p.sprregId = :sprregId"),
    @NamedQuery(name = "ParentsReg.findByParentsregDate", query = "SELECT p FROM ParentsReg p WHERE p.parentsregDate = :parentsregDate")})
public class ParentsReg implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "PARENTSREG_ID")
    @SequenceGenerator(name = "SEQ_PARENTS_REG", sequenceName = "SEQ_PARENTS_REG", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.IDENTITY, generator="SEQ_PARENTS_REG")
    private Integer parentsregId;
    @Column(name = "PARENTSREG_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date parentsregDate;
    @JoinColumn(name = "PARENT_ID", referencedColumnName = "PARENT_ID")
    @ManyToOne
    private Parents parentId;
    @JoinColumn(name = "SPRREG_ID", referencedColumnName = "SPRREG_ID")
    @ManyToOne
    private SprRegion sprregId;

    public ParentsReg() {
    }

    public ParentsReg(Integer parentsregId) {
        this.parentsregId = parentsregId;
    }

    public Integer getParentsregId() {
        return parentsregId;
    }

    public void setParentsregId(Integer parentsregId) {
        this.parentsregId = parentsregId;
    }

    public Date getParentsregDate() {
        return parentsregDate;
    }

    public void setParentsregDate(Date parentsregDate) {
        this.parentsregDate = parentsregDate;
    }

    public Parents getParentId() {
        return parentId;
    }

    public void setParentId(Parents parentId) {
        this.parentId = parentId;
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
        hash += (parentsregId != null ? parentsregId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ParentsReg)) {
            return false;
        }
        ParentsReg other = (ParentsReg) object;
        if ((this.parentsregId == null && other.parentsregId != null) || (this.parentsregId != null && !this.parentsregId.equals(other.parentsregId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.ParentsReg[ parentsregId=" + parentsregId + " ]";
    }
    
    public String getFormatDate(){
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("dd.MM.yyyy");
        Date date = this.getParentsregDate();
        String strD = "";
        try{
            strD  = format.format(date);
        }
        catch(Exception ex){            
        }
        return strD;
    }
}
