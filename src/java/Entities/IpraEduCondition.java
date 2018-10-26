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
@Table(name = "IPRA_EDU_CONDITION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IpraEduCondition.findAll", query = "SELECT i FROM IpraEduCondition i"),
    @NamedQuery(name = "IpraEduCondition.findByIpraeducondId", query = "SELECT i FROM IpraEduCondition i WHERE i.ipraeducondId = :ipraeducondId"),
    @NamedQuery(name = "IpraEduCondition.findByIpraeducondContext", query = "SELECT i FROM IpraEduCondition i WHERE i.ipraeducondContext = :ipraeducondContext"),
    @NamedQuery(name = "IpraEduCondition.findByIpraperechenId", query = "SELECT i FROM IpraEduCondition i WHERE i.ipraperechenId = :ipraperechenId ORDER BY i.ipraeducondtypeId")})
public class IpraEduCondition implements Serializable {

    @Column(name = "IPRAEDUCOND_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ipraeducondDate;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IPRAEDUCOND_ID")
    @SequenceGenerator(name = "SEQ_IPRA_EDU_CONDITION", sequenceName = "SEQ_IPRA_EDU_CONDITION", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.IDENTITY, generator="SEQ_IPRA_EDU_CONDITION")
    private Integer ipraeducondId;
    @Size(max = 1000)
    @Column(name = "IPRAEDUCOND_CONTEXT")
    private String ipraeducondContext;
    @JoinColumn(name = "IPRAEDUCONDTYPE_ID", referencedColumnName = "IPRAEDUCONDTYPE_ID")
    @ManyToOne
    private IpraeducondType ipraeducondtypeId;
    @JoinColumn(name = "IPRAPERECHEN_ID", referencedColumnName = "IPRAPERECHEN_ID")
    @ManyToOne
    private IpraPerechen ipraperechenId;
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
    @ManyToOne
    private Users userId;
    @Column(name = "DATE_UPD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateUpd;

    public IpraEduCondition() {
    }

    public IpraEduCondition(Integer ipraeducondId) {
        this.ipraeducondId = ipraeducondId;
    }

    public Integer getIpraeducondId() {
        return ipraeducondId;
    }

    public void setIpraeducondId(Integer ipraeducondId) {
        this.ipraeducondId = ipraeducondId;
    }

    public String getIpraeducondContext() {
        return ipraeducondContext;
    }

    public void setIpraeducondContext(String ipraeducondContext) {
        this.ipraeducondContext = ipraeducondContext;
    }

    public IpraeducondType getIpraeducondtypeId() {
        return ipraeducondtypeId;
    }

    public void setIpraeducondtypeId(IpraeducondType ipraeducondtypeId) {
        this.ipraeducondtypeId = ipraeducondtypeId;
    }

    public IpraPerechen getIpraperechenId() {
        return ipraperechenId;
    }

    public void setIpraperechenId(IpraPerechen ipraperechenId) {
        this.ipraperechenId = ipraperechenId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ipraeducondId != null ? ipraeducondId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IpraEduCondition)) {
            return false;
        }
        IpraEduCondition other = (IpraEduCondition) object;
        if ((this.ipraeducondId == null && other.ipraeducondId != null) || (this.ipraeducondId != null && !this.ipraeducondId.equals(other.ipraeducondId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.IpraEduCondition[ ipraeducondId=" + ipraeducondId + " ]";
    }

    public Date getIpraeducondDate() {
        return ipraeducondDate;
    }

    public void setIpraeducondDate(Date ipraeducondDate) {
        this.ipraeducondDate = ipraeducondDate;
    }
    
    public String getFormatDate(){
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("yyyy-MM-dd");
        String strD = "";
        try{
            strD  = format.format(this.ipraeducondDate);
        }
        catch(Exception ex){            
        }
        return strD;
    }
    
    public String getFormat2Date(){
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("dd.MM.yyyy");
        String strD = "";
        try{
            strD  = format.format(this.ipraeducondDate);
        }
        catch(Exception ex){            
        }
        return strD;
    }
    
    public void setUserId(Users userId){
        this.userId = userId;
    }
    
    public Users getUserId(){
        return userId;
    }
    
    public void setDateUpd(Date dateUpd){
        this.dateUpd = dateUpd;
    }
    
    public Date getDateUpd(){
        return dateUpd;
    }
}
