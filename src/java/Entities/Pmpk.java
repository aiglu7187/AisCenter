/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Collection;
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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Aiglu
 */
@Entity
@Table(name = "PMPK")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pmpk.findAll", query = "SELECT p FROM Pmpk p"),
    @NamedQuery(name = "Pmpk.findByPmpkId", query = "SELECT p FROM Pmpk p WHERE p.pmpkId = :pmpkId"),
    @NamedQuery(name = "Pmpk.findByPmpkOu", query = "SELECT p FROM Pmpk p WHERE p.pmpkOu = :pmpkOu"),
    @NamedQuery(name = "Pmpk.findByPmpkNp", query = "SELECT p FROM Pmpk p WHERE p.pmpkNp = :pmpkNp"),
    @NamedQuery(name = "Pmpk.findByPmpkTpmpk", query = "SELECT p FROM Pmpk p WHERE p.pmpkTpmpk = :pmpkTpmpk"),
    @NamedQuery(name = "Pmpk.findByPmpkGia", query = "SELECT p FROM Pmpk p WHERE p.pmpkGia = :pmpkGia"),
    @NamedQuery(name = "Pmpk.findByPmpkDop", query = "SELECT p FROM Pmpk p WHERE p.pmpkDop = :pmpkDop"),
    @NamedQuery(name = "Pmpk.findByPmpkIpr", query = "SELECT p FROM Pmpk p WHERE p.pmpkIpr = :pmpkIpr"),
    @NamedQuery(name = "Pmpk.findByPmpkSogl", query = "SELECT p FROM Pmpk p WHERE p.pmpkSogl = :pmpkSogl"),
    @NamedQuery(name = "Pmpk.findByPrclId", query = "SELECT p FROM Pmpk p WHERE p.prclId = :prclId")})
public class Pmpk implements Serializable {

    @Column(name = "PMPK_OU")
    private Integer pmpkOu;
    @Column(name = "PMPK_TPMPK")
    private Integer pmpkTpmpk;
    @Column(name = "PMPK_GIA")
    private Integer pmpkGia;
    @Column(name = "PMPK_DOP")
    private Integer pmpkDop;
    @Column(name = "PMPK_IPR")
    private Integer pmpkIpr;
    @Column(name = "PMPK_SOGL")
    private Integer pmpkSogl;
    @OneToMany(mappedBy = "pmpkId")
    private Collection<PmpkParent> pmpkParentCollection;
    @OneToMany(mappedBy = "pmpkId")
    private Collection<MonitoringData> monitoringDataCollection;
    @OneToMany(mappedBy = "pmpkId")
    private Collection<PmpkRek> pmpkRekCollection;
    @Column(name = "PMPK_DATEK")
    @Temporal(TemporalType.TIMESTAMP)
    private Date pmpkDatek;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "PMPK_ID")
    @SequenceGenerator(name = "SEQ_PMPK", sequenceName = "SEQ_PMPK", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.IDENTITY, generator="SEQ_PMPK")
    private Integer pmpkId;
    @Size(max = 20)
    @Column(name = "PMPK_NP")
    private String pmpkNp;
    @JoinColumn(name = "PRCL_ID", referencedColumnName = "PRCL_ID")
    @ManyToOne
    private PriyomClient prclId;
    @JoinColumn(name = "SPROBR_ID", referencedColumnName = "SPROBR_ID")
    @ManyToOne
    private SprObr sprobrId;
    @JoinColumn(name = "SPROBRVAR_ID", referencedColumnName = "SPROBRVAR_ID")
    @ManyToOne
    private SprObrVar sprobrvarId;

    public Pmpk() {
    }

    public Pmpk(Integer pmpkId) {
        this.pmpkId = pmpkId;
    }

    public Integer getPmpkId() {
        return pmpkId;
    }

    public void setPmpkId(Integer pmpkId) {
        this.pmpkId = pmpkId;
    }

    public Integer getPmpkOu() {
        return pmpkOu;
    }

    public void setPmpkOu(Integer pmpkOu) {
        this.pmpkOu = pmpkOu;
    }

    public String getPmpkNp() {
        return pmpkNp;
    }

    public void setPmpkNp(String pmpkNp) {
        this.pmpkNp = pmpkNp;
    }

    public Integer getPmpkTpmpk() {
        return pmpkTpmpk;
    }

    public void setPmpkTpmpk(Integer pmpkTpmpk) {
        this.pmpkTpmpk = pmpkTpmpk;
    }

    public Integer getPmpkGia() {
        return pmpkGia;
    }

    public void setPmpkGia(Integer pmpkGia) {
        this.pmpkGia = pmpkGia;
    }

    public Integer getPmpkDop() {
        return pmpkDop;
    }

    public void setPmpkDop(Integer pmpkDop) {
        this.pmpkDop = pmpkDop;
    }

    public Integer getPmpkIpr() {
        return pmpkIpr;
    }

    public void setPmpkIpr(Integer pmpkIpr) {
        this.pmpkIpr = pmpkIpr;
    }

    public Integer getPmpkSogl() {
        return pmpkSogl;
    }

    public void setPmpkSogl(Integer pmpkSogl) {
        this.pmpkSogl = pmpkSogl;
    }

    public PriyomClient getPrclId() {
        return prclId;
    }

    public void setPrclId(PriyomClient prclId) {
        this.prclId = prclId;
    }

    public SprObr getSprobrId() {
        return sprobrId;
    }

    public void setSprobrId(SprObr sprobrId) {
        this.sprobrId = sprobrId;
    }

    public SprObrVar getSprobrvarId() {
        return sprobrvarId;
    }

    public void setSprobrvarId(SprObrVar sprobrvarId) {
        this.sprobrvarId = sprobrvarId;
    }
    
    public Date getPmpkDatek() {
        return pmpkDatek;
    }

    public void setPmpkDatek(Date pmpkDatek) {
        this.pmpkDatek = pmpkDatek;
    }
    
    public String getFormatDatek(){
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("yyyy-MM-dd");
        Date date = this.getPmpkDatek();
        String strD = "";
        try{
            strD  = format.format(date);
        }
        catch(Exception ex){            
        }
        return strD;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pmpkId != null ? pmpkId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pmpk)) {
            return false;
        }
        Pmpk other = (Pmpk) object;
        if ((this.pmpkId == null && other.pmpkId != null) || (this.pmpkId != null && !this.pmpkId.equals(other.pmpkId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Pmpk[ pmpkId=" + pmpkId + " ]";
    }

    @XmlTransient
    public Collection<MonitoringData> getMonitoringDataCollection() {
        return monitoringDataCollection;
    }

    public void setMonitoringDataCollection(Collection<MonitoringData> monitoringDataCollection) {
        this.monitoringDataCollection = monitoringDataCollection;
    }
    
    public Collection<PmpkRek> getPmpkRekCollection() {
        return pmpkRekCollection;
    }

    public void setPmpkRekCollection(Collection<PmpkRek> pmpkRekCollection) {
        this.pmpkRekCollection = pmpkRekCollection;
    }

    @XmlTransient
    public Collection<PmpkParent> getPmpkParentCollection() {
        return pmpkParentCollection;
    }

    public void setPmpkParentCollection(Collection<PmpkParent> pmpkParentCollection) {
        this.pmpkParentCollection = pmpkParentCollection;
    }
    
}
