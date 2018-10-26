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
@Table(name = "PED_REG")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PedReg.findAll", query = "SELECT p FROM PedReg p"),
    @NamedQuery(name = "PedReg.findByPedregId", query = "SELECT p FROM PedReg p WHERE p.pedregId = :pedregId"),
    @NamedQuery(name = "PedReg.findByPedId", query = "SELECT p FROM PedReg p WHERE p.pedId = :pedId"),
    @NamedQuery(name = "PedReg.findBySprregId", query = "SELECT p FROM PedReg p WHERE p.sprregId = :sprregId"),
    @NamedQuery(name = "PedReg.findByPedregDate", query = "SELECT p FROM PedReg p WHERE p.pedregDate = :pedregDate")})
public class PedReg implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "PEDREG_ID")
    @SequenceGenerator(name = "SEQ_PED_REG", sequenceName = "SEQ_PED_REG", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.IDENTITY, generator="SEQ_PED_REG")  
    private Integer pedregId;
    @Column(name = "PEDREG_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date pedregDate;
    @JoinColumn(name = "PED_ID", referencedColumnName = "PED_ID")
    @ManyToOne
    private Ped pedId;
    @JoinColumn(name = "SPRREG_ID", referencedColumnName = "SPRREG_ID")
    @ManyToOne
    private SprRegion sprregId;

    public PedReg() {
    }

    public PedReg(Integer pedregId) {
        this.pedregId = pedregId;
    }

    public Integer getPedregId() {
        return pedregId;
    }

    public void setPedregId(Integer pedregId) {
        this.pedregId = pedregId;
    }

    public Date getPedregDate() {
        return pedregDate;
    }

    public void setPedregDate(Date pedregDate) {
        this.pedregDate = pedregDate;
    }

    public Ped getPedId() {
        return pedId;
    }

    public void setPedId(Ped pedId) {
        this.pedId = pedId;
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
        hash += (pedregId != null ? pedregId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PedReg)) {
            return false;
        }
        PedReg other = (PedReg) object;
        if ((this.pedregId == null && other.pedregId != null) || (this.pedregId != null && !this.pedregId.equals(other.pedregId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.PedReg[ pedregId=" + pedregId + " ]";
    }
    
    public String getFormatDate(){
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("dd.MM.yyyy");
        Date date = this.getPedregDate();
        String strD = "";
        try{
            strD  = format.format(date);
        }
        catch(Exception ex){            
        }
        return strD;
    }
    
}
