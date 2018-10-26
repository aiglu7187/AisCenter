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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Aiglu
 */
@Entity
@Table(name = "PARENTS_OLDFIO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ParentsOldfio.findAll", query = "SELECT p FROM ParentsOldfio p"),
    @NamedQuery(name = "ParentsOldfio.findByParentoldfioId", query = "SELECT p FROM ParentsOldfio p WHERE p.parentoldfioId = :parentoldfioId"),
    @NamedQuery(name = "ParentsOldfio.findByParentoldfioFam", query = "SELECT p FROM ParentsOldfio p WHERE p.parentoldfioFam = :parentoldfioFam"),
    @NamedQuery(name = "ParentsOldfio.findByParentoldfioName", query = "SELECT p FROM ParentsOldfio p WHERE p.parentoldfioName = :parentoldfioName"),
    @NamedQuery(name = "ParentsOldfio.findByParentoldfioPatr", query = "SELECT p FROM ParentsOldfio p WHERE p.parentoldfioPatr = :parentoldfioPatr"),
    @NamedQuery(name = "ParentsOldfio.findByParentId", query = "SELECT p FROM ParentsOldfio p WHERE p.parentId = :parentId")})
public class ParentsOldfio implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "PARENTOLDFIO_ID")
    @SequenceGenerator(name = "SEQ_PARENTS_OLDFIO", sequenceName = "SEQ_PARENTS_OLDFIO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SEQ_PARENTS_OLDFIO")
    private Integer parentoldfioId;
    @Size(max = 100)
    @Column(name = "PARENTOLDFIO_FAM")
    private String parentoldfioFam;
    @Size(max = 100)
    @Column(name = "PARENTOLDFIO_NAME")
    private String parentoldfioName;
    @Size(max = 100)
    @Column(name = "PARENTOLDFIO_PATR")
    private String parentoldfioPatr;
    @JoinColumn(name = "PARENT_ID", referencedColumnName = "PARENT_ID")
    @ManyToOne
    private Parents parentId;
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
    @ManyToOne
    private Users userId;
    @Column(name = "DATE_UPD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateUpd;

    public ParentsOldfio() {
    }

    public ParentsOldfio(Integer parentoldfioId) {
        this.parentoldfioId = parentoldfioId;
    }

    public Integer getParentoldfioId() {
        return parentoldfioId;
    }

    public void setParentoldfioId(Integer parentoldfioId) {
        this.parentoldfioId = parentoldfioId;
    }

    public String getParentoldfioFam() {
        return parentoldfioFam;
    }

    public void setParentoldfioFam(String parentoldfioFam) {
        this.parentoldfioFam = parentoldfioFam;
    }

    public String getParentoldfioName() {
        return parentoldfioName;
    }

    public void setParentoldfioName(String parentoldfioName) {
        this.parentoldfioName = parentoldfioName;
    }

    public String getParentoldfioPatr() {
        return parentoldfioPatr;
    }

    public void setParentoldfioPatr(String parentoldfioPatr) {
        this.parentoldfioPatr = parentoldfioPatr;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (parentoldfioId != null ? parentoldfioId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ParentsOldfio)) {
            return false;
        }
        ParentsOldfio other = (ParentsOldfio) object;
        if ((this.parentoldfioId == null && other.parentoldfioId != null) || (this.parentoldfioId != null && !this.parentoldfioId.equals(other.parentoldfioId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.ParentsOldfio[ parentoldfioId=" + parentoldfioId + " ]";
    }

    public Parents getParentId() {
        return parentId;
    }

    public void setParentId(Parents parentId) {
        this.parentId = parentId;
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
        if (parentoldfioPatr != null) {
            return parentoldfioFam + " " + parentoldfioName + " " + parentoldfioPatr;
        } else {
            return parentoldfioFam + " " + parentoldfioName;
        }
    }
}
