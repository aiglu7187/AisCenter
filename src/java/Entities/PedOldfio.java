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
@Table(name = "PED_OLDFIO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PedOldfio.findAll", query = "SELECT p FROM PedOldfio p"),
    @NamedQuery(name = "PedOldfio.findByPedoldfioId", query = "SELECT p FROM PedOldfio p WHERE p.pedoldfioId = :pedoldfioId"),
    @NamedQuery(name = "PedOldfio.findByPedoldfioFam", query = "SELECT p FROM PedOldfio p WHERE p.pedoldfioFam = :pedoldfioFam"),
    @NamedQuery(name = "PedOldfio.findByPedoldfioName", query = "SELECT p FROM PedOldfio p WHERE p.pedoldfioName = :pedoldfioName"),
    @NamedQuery(name = "PedOldfio.findByPedoldfioPatr", query = "SELECT p FROM PedOldfio p WHERE p.pedoldfioPatr = :pedoldfioPatr"),
    @NamedQuery(name = "PedOldfio.findByPedId", query = "SELECT p FROM PedOldfio p WHERE p.pedId = :pedId")})
public class PedOldfio implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "PEDOLDFIO_ID")
    @SequenceGenerator(name = "SEQ_PED_OLDFIO", sequenceName = "SEQ_PED_OLDFIO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SEQ_PED_OLDFIO")
    private Integer pedoldfioId;
    @Size(max = 100)
    @Column(name = "PEDOLDFIO_FAM")
    private String pedoldfioFam;
    @Size(max = 100)
    @Column(name = "PEDOLDFIO_NAME")
    private String pedoldfioName;
    @Size(max = 100)
    @Column(name = "PEDOLDFIO_PATR")
    private String pedoldfioPatr;
    @JoinColumn(name = "PED_ID", referencedColumnName = "PED_ID")
    @ManyToOne
    private Ped pedId;
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
    @ManyToOne
    private Users userId;
    @Column(name = "DATE_UPD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateUpd;

    public PedOldfio() {
    }

    public PedOldfio(Integer pedoldfioId) {
        this.pedoldfioId = pedoldfioId;
    }

    public Integer getPedoldfioId() {
        return pedoldfioId;
    }

    public void setPedoldfioId(Integer pedoldfioId) {
        this.pedoldfioId = pedoldfioId;
    }

    public String getPedoldfioFam() {
        return pedoldfioFam;
    }

    public void setPedoldfioFam(String pedoldfioFam) {
        this.pedoldfioFam = pedoldfioFam;
    }

    public String getPedoldfioName() {
        return pedoldfioName;
    }

    public void setPedoldfioName(String pedoldfioName) {
        this.pedoldfioName = pedoldfioName;
    }

    public String getPedoldfioPatr() {
        return pedoldfioPatr;
    }

    public void setPedoldfioPatr(String pedoldfioPatr) {
        this.pedoldfioPatr = pedoldfioPatr;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pedoldfioId != null ? pedoldfioId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PedOldfio)) {
            return false;
        }
        PedOldfio other = (PedOldfio) object;
        if ((this.pedoldfioId == null && other.pedoldfioId != null) || (this.pedoldfioId != null && !this.pedoldfioId.equals(other.pedoldfioId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.PedOldfio[ pedoldfioId=" + pedoldfioId + " ]";
    }

    public Ped getPedId() {
        return pedId;
    }

    public void setPedId(Ped pedId) {
        this.pedId = pedId;
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
        if (pedoldfioPatr != null) {
            return pedoldfioFam + " " + pedoldfioName + " " + pedoldfioPatr;
        } else {
            return pedoldfioFam + " " + pedoldfioName;
        }
    }
}
