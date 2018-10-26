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
@Table(name = "IPRA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ipra.findAll", query = "SELECT i FROM Ipra i"),
    @NamedQuery(name = "Ipra.findByIpraId", query = "SELECT i FROM Ipra i WHERE i.ipraId = :ipraId"),
    @NamedQuery(name = "Ipra.findByIpraN", query = "SELECT i FROM Ipra i WHERE i.ipraN = :ipraN"),
    @NamedQuery(name = "Ipra.findByIpraDateexp", query = "SELECT i FROM Ipra i WHERE i.ipraDateexp = :ipraDateexp"),
    @NamedQuery(name = "Ipra.findByIpraDateok", query = "SELECT i FROM Ipra i WHERE i.ipraDateok = :ipraDateok"),
    @NamedQuery(name = "Ipra.findByIpraIshmseN", query = "SELECT i FROM Ipra i WHERE i.ipraIshmseN = :ipraIshmseN"),
    @NamedQuery(name = "Ipra.findByIpraIshmseD", query = "SELECT i FROM Ipra i WHERE i.ipraIshmseD = :ipraIshmseD"),
    @NamedQuery(name = "Ipra.findByIpraVhdoN", query = "SELECT i FROM Ipra i WHERE i.ipraVhdoN = :ipraVhdoN"),
    @NamedQuery(name = "Ipra.findByIpraVhdoD", query = "SELECT i FROM Ipra i WHERE i.ipraVhdoD = :ipraVhdoD"),
    @NamedQuery(name = "Ipra.findByIpraPrikazN", query = "SELECT i FROM Ipra i WHERE i.ipraPrikazN = :ipraPrikazN"),
    @NamedQuery(name = "Ipra.findByIpraPrikazD", query = "SELECT i FROM Ipra i WHERE i.ipraPrikazD = :ipraPrikazD"),
    @NamedQuery(name = "Ipra.findByIpraPerechD", query = "SELECT i FROM Ipra i WHERE i.ipraPerechD = :ipraPerechD"),
    @NamedQuery(name = "Ipra.findByIpraTpmpkD", query = "SELECT i FROM Ipra i WHERE i.ipraTpmpkD = :ipraTpmpkD"),
    @NamedQuery(name = "Ipra.findByIpraOtchdo", query = "SELECT i FROM Ipra i WHERE i.ipraOtchdo = :ipraOtchdo"),
    @NamedQuery(name = "Ipra.findByIpraOtchcenter", query = "SELECT i FROM Ipra i WHERE i.ipraOtchcenter = :ipraOtchcenter"),
    @NamedQuery(name = "Ipra.findByIpraOtchomsu", query = "SELECT i FROM Ipra i WHERE i.ipraOtchomsu = :ipraOtchomsu"),
    @NamedQuery(name = "Ipra.findByIpraStatus", query = "SELECT i FROM Ipra i WHERE i.ipraStatus = :ipraStatus")})
public class Ipra implements Serializable {

    @Column(name = "IPRA_STATUS")
    private Integer ipraStatus;
    @Column(name = "IPRA_PRIKAZ_OMSU_D")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ipraPrikazOmsuD;
    @Column(name = "IPRA_OZNAKOM")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ipraOznakom;
    @Size(max = 50)
    @Column(name = "IPRA_OMSU_N")
    private String ipraOmsuN;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IPRA_ID")
    @SequenceGenerator(name = "IPRA_SEQ", sequenceName = "IPRA_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "IPRA_SEQ")
    private Integer ipraId;
    @Size(max = 50)
    @Column(name = "IPRA_N")
    private String ipraN;
    @Column(name = "IPRA_DATEEXP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ipraDateexp;
    @Size(max = 50)
    @Column(name = "IPRA_NEXP")
    private String ipraNexp;
    @Column(name = "IPRA_DATEOK")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ipraDateok;
    @Size(max = 50)
    @Column(name = "IPRA_ISHMSE_N")
    private String ipraIshmseN;
    @Column(name = "IPRA_ISHMSE_D")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ipraIshmseD;
    @Size(max = 50)
    @Column(name = "IPRA_VHDO_N")
    private String ipraVhdoN;
    @Column(name = "IPRA_VHDO_D")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ipraVhdoD;
    @Size(max = 50)
    @Column(name = "IPRA_PRIKAZ_N")
    private String ipraPrikazN;
    @Column(name = "IPRA_PRIKAZ_D")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ipraPrikazD;
    @Column(name = "IPRA_PERECH_D")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ipraPerechD;
    @Column(name = "IPRA_TPMPK_D")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ipraTpmpkD;
    @Column(name = "IPRA_OTCHDO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ipraOtchdo;
    @Column(name = "IPRA_OTCHCENTER")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ipraOtchcenter;
    @Column(name = "IPRA_OTCHOMSU")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ipraOtchomsu;
    @JoinColumn(name = "CHILD_ID", referencedColumnName = "CHILD_ID")
    @ManyToOne
    private Children childId;
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
    @ManyToOne
    private Users userId;
    @Column(name = "DATE_UPD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateUpd;
    @Size(max = 50)
    @Column(name = "IPRA_OTCHCENTER_N")
    private String ipraOtchcenterN;   

    public Ipra() {
    }

    public Ipra(Integer ipraId) {
        this.ipraId = ipraId;
    }

    public Integer getIpraId() {
        return ipraId;
    }

    public void setIpraId(Integer ipraId) {
        this.ipraId = ipraId;
    }

    public String getIpraN() {
        return ipraN;
    }

    public void setIpraN(String ipraN) {
        this.ipraN = ipraN;
    }

    public Date getIpraDateexp() {
        return ipraDateexp;
    }

    public void setIpraDateexp(Date ipraDateexp) {
        this.ipraDateexp = ipraDateexp;
    }

    public String getIpraNexp() {
        return ipraNexp;
    }

    public void setIpraNexp(String ipraNexp) {
        this.ipraNexp = ipraNexp;
    }

    public Date getIpraDateok() {
        return ipraDateok;
    }

    public void setIpraDateok(Date ipraDateok) {
        this.ipraDateok = ipraDateok;
    }

    public String getIpraIshmseN() {
        return ipraIshmseN;
    }

    public void setIpraIshmseN(String ipraIshmseN) {
        this.ipraIshmseN = ipraIshmseN;
    }

    public Date getIpraIshmseD() {
        return ipraIshmseD;
    }

    public void setIpraIshmseD(Date ipraIshmseD) {
        this.ipraIshmseD = ipraIshmseD;
    }

    public String getIpraVhdoN() {
        return ipraVhdoN;
    }

    public void setIpraVhdoN(String ipraVhdoN) {
        this.ipraVhdoN = ipraVhdoN;
    }

    public Date getIpraVhdoD() {
        return ipraVhdoD;
    }

    public void setIpraVhdoD(Date ipraVhdoD) {
        this.ipraVhdoD = ipraVhdoD;
    }

    public String getIpraPrikazN() {
        return ipraPrikazN;
    }

    public void setIpraPrikazN(String ipraPrikazN) {
        this.ipraPrikazN = ipraPrikazN;
    }

    public Date getIpraPrikazD() {
        return ipraPrikazD;
    }

    public void setIpraPrikazD(Date ipraPrikazD) {
        this.ipraPrikazD = ipraPrikazD;
    }

    public Date getIpraPerechD() {
        return ipraPerechD;
    }

    public void setIpraPerechD(Date ipraPerechD) {
        this.ipraPerechD = ipraPerechD;
    }

    public Date getIpraTpmpkD() {
        return ipraTpmpkD;
    }

    public void setIpraTpmpkD(Date ipraTpmpkD) {
        this.ipraTpmpkD = ipraTpmpkD;
    }

    public Date getIpraOtchdo() {
        return ipraOtchdo;
    }

    public void setIpraOtchdo(Date ipraOtchdo) {
        this.ipraOtchdo = ipraOtchdo;
    }

    public Date getIpraOtchcenter() {
        return ipraOtchcenter;
    }

    public void setIpraOtchcenter(Date ipraOtchcenter) {
        this.ipraOtchcenter = ipraOtchcenter;
    }

    public Date getIpraOtchomsu() {
        return ipraOtchomsu;
    }

    public void setIpraOtchomsu(Date ipraOtchomsu) {
        this.ipraOtchomsu = ipraOtchomsu;
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

    public void setDateUpd(Date dateUpd) {
        this.dateUpd = dateUpd;
    }

    public String getFormatDate(Date d) {
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("yyyy-MM-dd");
        String strD = "";
        try {
            strD = format.format(d);
        } catch (Exception ex) {
        }
        return strD;
    }

    public String getFormat2Date(Date d) {
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("dd.MM.yyyy");
        String strD = "";
        try {
            strD = format.format(d);
        } catch (Exception ex) {
        }
        return strD;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ipraId != null ? ipraId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ipra)) {
            return false;
        }
        Ipra other = (Ipra) object;
        if ((this.ipraId == null && other.ipraId != null) || (this.ipraId != null && !this.ipraId.equals(other.ipraId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Ipra[ ipraId=" + ipraId + " ]";
    }

    public Integer getIpraStatus() {
        return ipraStatus;
    }

    public void setIpraStatus(Integer ipraStatus) {
        this.ipraStatus = ipraStatus;
    }

    public Date getIpraPrikazOmsuD() {
        return ipraPrikazOmsuD;
    }

    public void setIpraPrikazOmsuD(Date ipraPrikazOmsuD) {
        this.ipraPrikazOmsuD = ipraPrikazOmsuD;
    }

    public Date getIpraOznakom() {
        return ipraOznakom;
    }

    public void setIpraOznakom(Date ipraOznakom) {
        this.ipraOznakom = ipraOznakom;
    }

    public String getIpraOmsuN() {
        return ipraOmsuN;
    }

    public void setIpraOmsuN(String ipraOmsuN) {
        this.ipraOmsuN = ipraOmsuN;
    }

    public String getIpraOtchcenterN() {
        return ipraOtchcenterN;
    }

    public void setIpraOtchcenterN(String ipraOtchcenterN) {
        this.ipraOtchcenterN = ipraOtchcenterN;
    }

}
