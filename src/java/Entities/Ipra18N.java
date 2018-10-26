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
@Table(name = "IPRA18N")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ipra18N.findAll", query = "SELECT i FROM Ipra18N i"),
    @NamedQuery(name = "Ipra18N.findByIpra18Id", query = "SELECT i FROM Ipra18N i WHERE i.ipra18Id = :ipra18Id"),
    @NamedQuery(name = "Ipra18N.findByIpra18N", query = "SELECT i FROM Ipra18N i WHERE i.ipra18N = :ipra18N")})
public class Ipra18N implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IPRA18_ID")
    @SequenceGenerator(name = "SEQ_IPRA18N", sequenceName = "SEQ_IPRA18N", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SEQ_IPRA18N")
    private Integer ipra18Id;
    @JoinColumn(name = "CHILD_ID", referencedColumnName = "CHILD_ID")
    @ManyToOne
    private Children childId;
    @Size(max = 50)
    @Column(name = "IPRA18_N")
    private String ipra18N;
    @Size(max = 50)
    @Column(name = "IPRA18_NEXP")
    private String ipra18Nexp;
    @Column(name = "IPRA18_DATEEXP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ipra18Dateexp;
    @Column(name = "IPRA18_DATEOK")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ipra18Dateok;
    @Size(max = 50)
    @Column(name = "IPRA18_ISHMSE_N")
    private String ipra18IshmseN;
    @Column(name = "IPRA18_ISHMSE_D")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ipra18IshmseD;
    @Size(max = 50)
    @Column(name = "IPRA18_VHTODO_N")
    private String ipra18VhtodoN;
    @Column(name = "IPRA18_VHTODO_D")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ipra18VhtodoD;
    @JoinColumn(name = "SPRMSE_ID", referencedColumnName = "SPRMSE_ID")
    @ManyToOne
    private SprMse sprmseId;
    @Column(name = "IPRA18_STATUS")
    private Integer ipra18Status;
    @Column(name = "IPRA18_OTCHOMSU")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ipra18Otchomsu;
    @Column(name = "IPRA18_OTCHCENTER")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ipra18Otchcenter;    
    @Column(name = "IPRA18_OTCHDO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ipra18Otchdo;
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
    @ManyToOne
    private Users userId;
    @Column(name = "DATE_UPD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateUpd;

    public Ipra18N() {
    }

    public Ipra18N(Integer ipra18Id) {
        this.ipra18Id = ipra18Id;
    }

    public Integer getIpra18Id() {
        return ipra18Id;
    }

    public void setIpra18Id(Integer ipra18Id) {
        this.ipra18Id = ipra18Id;
    }

    public String getIpra18N() {
        return ipra18N;
    }

    public void setIpra18N(String ipra18N) {
        this.ipra18N = ipra18N;
    }

    public Date getIpra18Dateexp() {
        return ipra18Dateexp;
    }

    public void setIpra18Dateexp(Date ipra18Dateexp) {
        this.ipra18Dateexp = ipra18Dateexp;
    }

    public Date getIpra18Dateok() {
        return ipra18Dateok;
    }

    public void setIpra18Dateok(Date ipra18Dateok) {
        this.ipra18Dateok = ipra18Dateok;
    }

    public String getIpra18IshmseN() {
        return ipra18IshmseN;
    }

    public void setIpra18IshmseN(String ipra18IshmseN) {
        this.ipra18IshmseN = ipra18IshmseN;
    }

    public Date getIpra18IshmseD() {
        return ipra18IshmseD;
    }

    public void setIpra18IshmseD(Date ipra18IshmseD) {
        this.ipra18IshmseD = ipra18IshmseD;
    }

    public String getIpra18VhtodoN() {
        return ipra18VhtodoN;
    }

    public void setIpra18VhtodoN(String ipra18VhdoN) {
        this.ipra18VhtodoN = ipra18VhdoN;
    }

    public Date getIpra18VhtodoD() {
        return ipra18VhtodoD;
    }

    public void setIpra18VhtodoD(Date ipra18VhdoD) {
        this.ipra18VhtodoD = ipra18VhdoD;
    }

    public Date getIpra18Otchdo() {
        return ipra18Otchdo;
    }

    public void setIpra18Otchdo(Date ipra18Otchdo) {
        this.ipra18Otchdo = ipra18Otchdo;
    }

    public Date getDateUpd() {
        return dateUpd;
    }

    public void setDateUpd(Date dateUpd) {
        this.dateUpd = dateUpd;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ipra18Id != null ? ipra18Id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ipra18)) {
            return false;
        }
        Ipra18N other = (Ipra18N) object;
        if ((this.ipra18Id == null && other.ipra18Id != null) || (this.ipra18Id != null && !this.ipra18Id.equals(other.ipra18Id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Ipra18N[ ipra18Id=" + ipra18Id + " ]";
    }

    public SprMse getSprmseId() {
        return sprmseId;
    }

    public void setSprmseId(SprMse sprmseId) {
        this.sprmseId = sprmseId;
    }

    public Integer getIpra18Status() {
        return ipra18Status;
    }

    public void setIpra18Status(Integer ipra18Status) {
        this.ipra18Status = ipra18Status;
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

    public String getIpra18Nexp() {
        return ipra18Nexp;
    }

    public void setIpra18Nexp(String ipra18Nexp) {
        this.ipra18Nexp = ipra18Nexp;
    }

    public Date getIpra18Otchomsu() {
        return ipra18Otchomsu;
    }

    public void setIpra18Otchomsu(Date ipra18Otchomsu) {
        this.ipra18Otchomsu = ipra18Otchomsu;
    }

    public Date getIpra18Otchcenter() {
        return ipra18Otchcenter;
    }

    public void setIpra18Otchcenter(Date ipra18Otchcenter) {
        this.ipra18Otchcenter = ipra18Otchcenter;
    }
}
