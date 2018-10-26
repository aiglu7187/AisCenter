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
@Table(name = "IPRA18_PRIKAZ_N")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ipra18PrikazN.findAll", query = "SELECT i FROM Ipra18PrikazN i"),
    @NamedQuery(name = "Ipra18PrikazN.findByIpra18prikazId", query = "SELECT i FROM Ipra18PrikazN i WHERE i.ipra18prikazId = :ipra18prikazId"),
    @NamedQuery(name = "Ipra18PrikazN.findByIpra18Id", query = "SELECT i FROM Ipra18PrikazN i WHERE i.ipra18Id = :ipra18Id")})
public class Ipra18PrikazN implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IPRA18PRIKAZ_ID")
    @SequenceGenerator(name = "SEQ_IPRA18_PRIKAZ_N", sequenceName = "SEQ_IPRA18_PRIKAZ_N", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SEQ_IPRA18_PRIKAZ_N")
    private Integer ipra18prikazId;
    @JoinColumn(name = "IPRA18_ID", referencedColumnName = "IPRA18_ID")
    @ManyToOne
    private Ipra18N ipra18Id;    
    @Size(max = 50)
    @Column(name = "IPRA18PRIKAZ_N")
    private String ipra18prikazN;
    @Column(name = "IPRA18PRIKAZ_D")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ipra18prikazD;    
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
    @ManyToOne
    private Users userId;
    @Column(name = "DATE_UPD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateUpd;
    @JoinColumn(name = "IPRAOMSU_ID", referencedColumnName = "IPRAOMSU_ID")
    @ManyToOne
    private IpraOmsu ipraomsuId;

    public Ipra18PrikazN() {
    }

    public Ipra18PrikazN(Integer ipra18prikazId) {
        this.ipra18prikazId = ipra18prikazId;
    }

    public Integer getIpra18prikazId() {
        return ipra18prikazId;
    }

    public void setIpra18prikazId(Integer ipra18prikazId) {
        this.ipra18prikazId = ipra18prikazId;
    }

    public Date getDateUpd() {
        return dateUpd;
    }

    public void setDateUpd(Date dateUpd) {
        this.dateUpd = dateUpd;
    }

    public Ipra18N getIpra18Id() {
        return ipra18Id;
    }

    public void setIpra18Id(Ipra18N ipra18Id) {
        this.ipra18Id = ipra18Id;
    }

    public Users getUserId() {
        return userId;
    }

    public void setUserId(Users userId) {
        this.userId = userId;
    }

    public String getIpra18prikazN(){
        return ipra18prikazN;
    }
    
    public void setIpra18prikazN(String ipra18prikazN){
        this.ipra18prikazN = ipra18prikazN;
    }
    
    public Date getIpra18prikazD() {
        return ipra18prikazD;
    }
    
    public void setIpra18prikazD(Date ipra18prikazD) {
        this.ipra18prikazD = ipra18prikazD;
    }
    
    public IpraOmsu getIpraomsuId() {
        return ipraomsuId;
    }
    
    public void setIpraomsuId(IpraOmsu ipraomsuId) {
        this.ipraomsuId = ipraomsuId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ipra18prikazId != null ? ipra18prikazId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ipra18Prikaz)) {
            return false;
        }
        Ipra18PrikazN other = (Ipra18PrikazN) object;
        if ((this.ipra18prikazId == null && other.ipra18prikazId != null) || (this.ipra18prikazId != null && !this.ipra18prikazId.equals(other.ipra18prikazId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Ipra18PrikazN[ ipra18prikazId=" + ipra18prikazId + " ]";
    }

    public String getFormatDate(Date d){
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("yyyy-MM-dd");
        String strD = "";
        try{
            strD  = format.format(d);
        }
        catch(Exception ex){            
        }
        return strD;
    }
    
    public String getFormat2Date(Date d){
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("dd.MM.yyyy");
        String strD = "";
        try{
            strD  = format.format(d);
        }
        catch(Exception ex){            
        }
        return strD;
    }
}