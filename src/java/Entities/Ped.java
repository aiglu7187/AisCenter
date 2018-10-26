/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;
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
 * @author admin_ai
 */
@Entity
@Table(name = "PED")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ped.findAll", query = "SELECT p FROM Ped p ORDER BY p.pedFam, p.pedName, p.pedPatr"),
    @NamedQuery(name = "Ped.findByPedId", query = "SELECT p FROM Ped p WHERE p.pedId = :pedId"),
    @NamedQuery(name = "Ped.findByPedFam", query = "SELECT p FROM Ped p WHERE p.pedFam = :pedFam"),
    @NamedQuery(name = "Ped.findByPedName", query = "SELECT p FROM Ped p WHERE p.pedName = :pedName"),
    @NamedQuery(name = "Ped.findByPedPatr", query = "SELECT p FROM Ped p WHERE p.pedPatr = :pedPatr"),
    @NamedQuery(name = "Ped.findByPedNom", query = "SELECT p FROM Ped p WHERE p.pedNom = :pedNom"),
    @NamedQuery(name = "Ped.findByPedOrg", query = "SELECT p FROM Ped p WHERE p.pedOrg = :pedOrg"),
    @NamedQuery(name = "Ped.findByPedDolgn", query = "SELECT p FROM Ped p WHERE p.pedDolgn = :pedDolgn")})
public class Ped implements Serializable {

    @Column(name = "PED_NOM")
    private Integer pedNom;
    @Column(name = "USER_ID")
    private Integer userId;
    @OneToMany(mappedBy = "pedId")
    private Collection<PedReg> pedRegCollection;
    @JoinColumn(name = "SPRORG_ID", referencedColumnName = "SPRORG_ID")
    @ManyToOne
    private SprOrg sprorgId;
    @JoinColumn(name = "SPRPEDDOLG_ID", referencedColumnName = "SPRPEDDOLG_ID")
    @ManyToOne
    private SprPeddolg sprpeddolgId;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "PED_ID")
    @SequenceGenerator(name = "SEQ_PED", sequenceName = "SEQ_PED", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.IDENTITY, generator="SEQ_PED")  
    private Integer pedId;
    @Size(max = 100)
    @Column(name = "PED_FAM")
    private String pedFam;
    @Size(max = 100)
    @Column(name = "PED_NAME")
    private String pedName;
    @Size(max = 100)
    @Column(name = "PED_PATR")
    private String pedPatr;
    @Size(max = 200)
    @Column(name = "PED_ORG")
    private String pedOrg;
    @Size(max = 100)
    @Column(name = "PED_DOLGN")
    private String pedDolgn;
    @Column(name = "DATE_UPD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateUpd;
    @JoinColumn(name = "SPRREG_ID", referencedColumnName = "SPRREG_ID")
    @ManyToOne
    private SprRegion sprregId;

    public Ped() {
    }

    public Ped(Integer pedId) {
        this.pedId = pedId;
    }

    public Integer getPedId() {
        return pedId;
    }

    public void setPedId(Integer pedId) {
        this.pedId = pedId;
    }

    public String getPedFam() {
        return pedFam;
    }

    public void setPedFam(String pedFam) {
        this.pedFam = pedFam;
    }

    public String getPedName() {
        return pedName;
    }

    public void setPedName(String pedName) {
        this.pedName = pedName;
    }

    public String getPedPatr() {
        return pedPatr;
    }

    public void setPedPatr(String pedPatr) {
        this.pedPatr = pedPatr;
    }

    public Integer getPedNom() {
        return pedNom;
    }

    public void setPedNom(Integer pedNom) {
        this.pedNom = pedNom;
    }

    public String getPedOrg() {
        return pedOrg;
    }

    public void setPedOrg(String pedOrg) {
        this.pedOrg = pedOrg;
    }

    public String getPedDolgn() {
        return pedDolgn;
    }

    public void setPedDolgn(String pedDolgn) {
        this.pedDolgn = pedDolgn;
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
        hash += (pedId != null ? pedId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ped)) {
            return false;
        }
        Ped other = (Ped) object;
        if ((this.pedId == null && other.pedId != null) || (this.pedId != null && !this.pedId.equals(other.pedId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Ped[ pedId=" + pedId + " ]";
    }

    public SprOrg getSprorgId() {
        return sprorgId;
    }

    public void setSprorgId(SprOrg sprorgId) {
        this.sprorgId = sprorgId;
    }

    public SprPeddolg getSprpeddolgId() {
        return sprpeddolgId;
    }

    public void setSprpeddolgId(SprPeddolg sprpeddolgId) {
        this.sprpeddolgId = sprpeddolgId;
    }
    
    public String getFIO(){
        if (pedPatr != null){
            return pedFam + " " + pedName + " " + pedPatr;
        }
        else {
            return pedFam + " " + pedName;
        }
    }
    
     public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    
    public Date getDateUpd() {
        return dateUpd;
    }

    public void setDateUpd(Date dateUpd) {
        this.dateUpd = dateUpd;
    }

    @XmlTransient
    public Collection<PedReg> getPedRegCollection() {
        return pedRegCollection;
    }

    public void setPedRegCollection(Collection<PedReg> pedRegCollection) {
        this.pedRegCollection = pedRegCollection;
    }
    
}
