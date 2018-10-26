/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Aiglu
 */
@Entity
@Table(name = "CHILDREN_OLDFIO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ChildrenOldfio.findAll", query = "SELECT c FROM ChildrenOldfio c"),
    @NamedQuery(name = "ChildrenOldfio.findByChildoldfioId", query = "SELECT c FROM ChildrenOldfio c WHERE c.childoldfioId = :childoldfioId"),
    @NamedQuery(name = "ChildrenOldfio.findByChildoldfioFam", query = "SELECT c FROM ChildrenOldfio c WHERE c.childoldfioFam = :childoldfioFam"),
    @NamedQuery(name = "ChildrenOldfio.findByChildoldfioName", query = "SELECT c FROM ChildrenOldfio c WHERE c.childoldfioName = :childoldfioName"),
    @NamedQuery(name = "ChildrenOldfio.findByChildoldfioPatr", query = "SELECT c FROM ChildrenOldfio c WHERE c.childoldfioPatr = :childoldfioPatr"),
    @NamedQuery(name = "ChildrenOldfio.findByChildId", query = "SELECT c FROM ChildrenOldfio c WHERE c.childId = :childId")})
public class ChildrenOldfio implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "CHILDOLDFIO_ID")
    @SequenceGenerator(name = "SEQ_CHILDREN_OLDFIO", sequenceName = "SEQ_CHILDREN_OLDFIO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SEQ_CHILDREN_OLDFIO")
    private Integer childoldfioId;
    @Size(max = 100)
    @Column(name = "CHILDOLDFIO_FAM")
    private String childoldfioFam;
    @Size(max = 100)
    @Column(name = "CHILDOLDFIO_NAME")
    private String childoldfioName;
    @Size(max = 100)
    @Column(name = "CHILDOLDFIO_PATR")
    private String childoldfioPatr;
    @JoinColumn(name = "CHILD_ID", referencedColumnName = "CHILD_ID")
    @ManyToOne
    private Children childId;
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
    @ManyToOne
    private Users userId;
    @Column(name = "DATE_UPD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateUpd;

    public ChildrenOldfio() {
    }

    public ChildrenOldfio(Integer childoldfioId) {
        this.childoldfioId = childoldfioId;
    }

    public Integer getChildoldfioId() {
        return childoldfioId;
    }

    public void setChildoldfioId(Integer childoldfioId) {
        this.childoldfioId = childoldfioId;
    }

    public String getChildoldfioFam() {
        return childoldfioFam;
    }

    public void setChildoldfioFam(String childoldfioFam) {
        this.childoldfioFam = childoldfioFam;
    }

    public String getChildoldfioName() {
        return childoldfioName;
    }

    public void setChildoldfioName(String childoldfioName) {
        this.childoldfioName = childoldfioName;
    }

    public String getChildoldfioPatr() {
        return childoldfioPatr;
    }

    public void setChildoldfioPatr(String childoldfioPatr) {
        this.childoldfioPatr = childoldfioPatr;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (childoldfioId != null ? childoldfioId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ChildrenOldfio)) {
            return false;
        }
        ChildrenOldfio other = (ChildrenOldfio) object;
        if ((this.childoldfioId == null && other.childoldfioId != null) || (this.childoldfioId != null && !this.childoldfioId.equals(other.childoldfioId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.ChildrenOldfio[ childoldfioId=" + childoldfioId + " ]";
    }

    public Children getChildId() {
        return childId;
    }

    public void setChildId(Children childId) {
        this.childId = childId;
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
    
    public String getFormatDateUpd() {
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("dd.MM.yyyy");
        Date date = this.getDateUpd();
        String strDate = "";
        try{
            strDate  = format.format(date);
        }
        catch(Exception ex){            
        }
        return strDate;
    }

    public void setDateUpd(Date dateUpd) {
        this.dateUpd = dateUpd;
    }

    public String getFIO() {
        if (childoldfioPatr != null) {
            return childoldfioFam + " " + childoldfioName + " " + childoldfioPatr;
        }
        else {
            return childoldfioFam + " " + childoldfioName;
        }
    }
}
