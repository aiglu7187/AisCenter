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
@Table(name = "IPRA18_PRIKAZ")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ipra18Prikaz.findAll", query = "SELECT i FROM Ipra18Prikaz i"),
    @NamedQuery(name = "Ipra18Prikaz.findByIpra18prikazId", query = "SELECT i FROM Ipra18Prikaz i WHERE i.ipra18prikazId = :ipra18prikazId"),
    @NamedQuery(name = "Ipra18Prikaz.findByIpra18prikazReqN", query = "SELECT i FROM Ipra18Prikaz i WHERE i.ipra18prikazReqN = :ipra18prikazReqN"),
    @NamedQuery(name = "Ipra18Prikaz.findByIpra18prikazReqD", query = "SELECT i FROM Ipra18Prikaz i WHERE i.ipra18prikazReqD = :ipra18prikazReqD"),
    @NamedQuery(name = "Ipra18Prikaz.findByIpra18prikazDoN", query = "SELECT i FROM Ipra18Prikaz i WHERE i.ipra18prikazDoN = :ipra18prikazDoN"),
    @NamedQuery(name = "Ipra18Prikaz.findByIpra18prikazDoD", query = "SELECT i FROM Ipra18Prikaz i WHERE i.ipra18prikazDoD = :ipra18prikazDoD"),
    @NamedQuery(name = "Ipra18Prikaz.findByIpra18prikazPerechN", query = "SELECT i FROM Ipra18Prikaz i WHERE i.ipra18prikazPerechN = :ipra18prikazPerechN"),
    @NamedQuery(name = "Ipra18Prikaz.findByIpra18prikazPerechD", query = "SELECT i FROM Ipra18Prikaz i WHERE i.ipra18prikazPerechD = :ipra18prikazPerechD"),
    @NamedQuery(name = "Ipra18Prikaz.findByIpra18prikazOznakD", query = "SELECT i FROM Ipra18Prikaz i WHERE i.ipra18prikazOznakD = :ipra18prikazOznakD"),
    @NamedQuery(name = "Ipra18Prikaz.findByIpra18prikazTpmpkD", query = "SELECT i FROM Ipra18Prikaz i WHERE i.ipra18prikazTpmpkD = :ipra18prikazTpmpkD"),
    @NamedQuery(name = "Ipra18Prikaz.findByIpra18prikazTpmpkN", query = "SELECT i FROM Ipra18Prikaz i WHERE i.ipra18prikazTpmpkN = :ipra18prikazTpmpkN"),
    @NamedQuery(name = "Ipra18Prikaz.findByIpra18prikazOmsuprD", query = "SELECT i FROM Ipra18Prikaz i WHERE i.ipra18prikazOmsuprD = :ipra18prikazOmsuprD"),
    @NamedQuery(name = "Ipra18Prikaz.findByIpra18prikazOtchomsu", query = "SELECT i FROM Ipra18Prikaz i WHERE i.ipra18prikazOtchomsu = :ipra18prikazOtchomsu"),
    @NamedQuery(name = "Ipra18Prikaz.findByDateUpd", query = "SELECT i FROM Ipra18Prikaz i WHERE i.dateUpd = :dateUpd"),
    @NamedQuery(name = "Ipra18Prikaz.findByIpra18Id", query = "SELECT i FROM Ipra18Prikaz i WHERE i.ipra18Id = :ipra18Id")})
public class Ipra18Prikaz implements Serializable {

    @Column(name = "IPRA18PRIKAZ_OTCHCENTER")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ipra18prikazOtchcenter;
    @Column(name = "IPRA18PRIKAZ_OTCHCENTER_N")
    private String ipra18prikazOtchcenterN;
    /*private Integer ipra18prikazOtchcenterN;*/

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IPRA18PRIKAZ_ID")
    @SequenceGenerator(name = "SEQ_IPRA18_PRIKAZ", sequenceName = "SEQ_IPRA18_PRIKAZ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SEQ_IPRA18_PRIKAZ")
    private Integer ipra18prikazId;
    /*private Integer ipra18prikazId;*/
    @Size(max = 50)
    @Column(name = "IPRA18PRIKAZ_REQ_N")
    private String ipra18prikazReqN;
    @Column(name = "IPRA18PRIKAZ_REQ_D")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ipra18prikazReqD;
    @Size(max = 50)
    @Column(name = "IPRA18PRIKAZ_DO_N")
    private String ipra18prikazDoN;
    @Column(name = "IPRA18PRIKAZ_DO_D")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ipra18prikazDoD;
    @Size(max = 50)
    @Column(name = "IPRA18PRIKAZ_PERECH_N")
    private String ipra18prikazPerechN;
    @Column(name = "IPRA18PRIKAZ_PERECH_D")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ipra18prikazPerechD;
    @Column(name = "IPRA18PRIKAZ_OZNAK_D")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ipra18prikazOznakD;
    @Column(name = "IPRA18PRIKAZ_TPMPK_D")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ipra18prikazTpmpkD;
    @Size(max = 50)
    @Column(name = "IPRA18PRIKAZ_TPMPK_N")
    private String ipra18prikazTpmpkN;
    @Column(name = "IPRA18PRIKAZ_OMSUPR_D")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ipra18prikazOmsuprD;
    @Column(name = "IPRA18PRIKAZ_OTCHOMSU")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ipra18prikazOtchomsu;
    @Column(name = "DATE_UPD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateUpd;
    @JoinColumn(name = "IPRA18_ID", referencedColumnName = "IPRA18_ID")
    @ManyToOne
    private Ipra18 ipra18Id;
    @JoinColumn(name = "SPROMSU_ID", referencedColumnName = "SPROMSU_ID")
    @ManyToOne
    private SprOmsu spromsuId;
    @JoinColumn(name = "SPROTHERPMPK_ID", referencedColumnName = "SPROTHERPMPK_ID")
    @ManyToOne
    private SprOtherPmpk sprotherpmpkId;
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
    @ManyToOne
    private Users userId;    

    public Ipra18Prikaz() {
    }

    public Ipra18Prikaz(Integer ipra18prikazId) {
        this.ipra18prikazId = ipra18prikazId;
    }

    public Integer getIpra18prikazId() {
        return ipra18prikazId;
    }

    public void setIpra18prikazId(Integer ipra18prikazId) {
        this.ipra18prikazId = ipra18prikazId;
    }

    public String getIpra18prikazReqN() {
        return ipra18prikazReqN;
    }

    public void setIpra18prikazReqN(String ipra18prikazReqN) {
        this.ipra18prikazReqN = ipra18prikazReqN;
    }

    public Date getIpra18prikazReqD() {
        return ipra18prikazReqD;
    }

    public void setIpra18prikazReqD(Date ipra18prikazReqD) {
        this.ipra18prikazReqD = ipra18prikazReqD;
    }

    public String getIpra18prikazDoN() {
        return ipra18prikazDoN;
    }

    public void setIpra18prikazDoN(String ipra18prikazDoN) {
        this.ipra18prikazDoN = ipra18prikazDoN;
    }

    public Date getIpra18prikazDoD() {
        return ipra18prikazDoD;
    }

    public void setIpra18prikazDoD(Date ipra18prikazDoD) {
        this.ipra18prikazDoD = ipra18prikazDoD;
    }

    public String getIpra18prikazPerechN() {
        return ipra18prikazPerechN;
    }

    public void setIpra18prikazPerechN(String ipra18prikazPerechN) {
        this.ipra18prikazPerechN = ipra18prikazPerechN;
    }

    public Date getIpra18prikazPerechD() {
        return ipra18prikazPerechD;
    }

    public void setIpra18prikazPerechD(Date ipra18prikazPerechD) {
        this.ipra18prikazPerechD = ipra18prikazPerechD;
    }

    public Date getIpra18prikazOznakD() {
        return ipra18prikazOznakD;
    }

    public void setIpra18prikazOznakD(Date ipra18prikazOznakD) {
        this.ipra18prikazOznakD = ipra18prikazOznakD;
    }

    public Date getIpra18prikazTpmpkD() {
        return ipra18prikazTpmpkD;
    }

    public void setIpra18prikazTpmpkD(Date ipra18prikazTpmpkD) {
        this.ipra18prikazTpmpkD = ipra18prikazTpmpkD;
    }

    public String getIpra18prikazTpmpkN() {
        return ipra18prikazTpmpkN;
    }

    public void setIpra18prikazTpmpkN(String ipra18prikazTpmpkN) {
        this.ipra18prikazTpmpkN = ipra18prikazTpmpkN;
    }

    public Date getIpra18prikazOmsuprD() {
        return ipra18prikazOmsuprD;
    }

    public void setIpra18prikazOmsuprD(Date ipra18prikazOmsuprD) {
        this.ipra18prikazOmsuprD = ipra18prikazOmsuprD;
    }

    public Date getIpra18prikazOtchomsu() {
        return ipra18prikazOtchomsu;
    }

    public void setIpra18prikazOtchomsu(Date ipra18prikazOtchomsu) {
        this.ipra18prikazOtchomsu = ipra18prikazOtchomsu;
    }

    public Date getDateUpd() {
        return dateUpd;
    }

    public void setDateUpd(Date dateUpd) {
        this.dateUpd = dateUpd;
    }

    public Ipra18 getIpra18Id() {
        return ipra18Id;
    }

    public void setIpra18Id(Ipra18 ipra18Id) {
        this.ipra18Id = ipra18Id;
    }

    public SprOmsu getSpromsuId() {
        return spromsuId;
    }

    public void setSpromsuId(SprOmsu spromsuId) {
        this.spromsuId = spromsuId;
    }

    public SprOtherPmpk getSprotherpmpkId() {
        return sprotherpmpkId;
    }

    public void setSprotherpmpkId(SprOtherPmpk sprotherpmpkId) {
        this.sprotherpmpkId = sprotherpmpkId;
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
        hash += (ipra18prikazId != null ? ipra18prikazId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ipra18Prikaz)) {
            return false;
        }
        Ipra18Prikaz other = (Ipra18Prikaz) object;
        if ((this.ipra18prikazId == null && other.ipra18prikazId != null) || (this.ipra18prikazId != null && !this.ipra18prikazId.equals(other.ipra18prikazId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Ipra18Prikaz[ ipra18prikazId=" + ipra18prikazId + " ]";
    }

    public Date getIpra18prikazOtchcenter() {
        return ipra18prikazOtchcenter;
    }

    public void setIpra18prikazOtchcenter(Date ipra18prikazOtchcenter) {
        this.ipra18prikazOtchcenter = ipra18prikazOtchcenter;
    }

    public String getIpra18prikazOtchcenterN() {
        return ipra18prikazOtchcenterN;
    }

    public void setIpra18prikazOtchcenterN(String ipra18prikazOtchcenterN) {
        this.ipra18prikazOtchcenterN = ipra18prikazOtchcenterN;
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
