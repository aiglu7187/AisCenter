/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;
import java.util.Collection;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author admin_ai
 */
@Entity
@Table(name = "SOTRUD")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sotrud.findAll", query = "SELECT s FROM Sotrud s ORDER BY s.sotrudFam,s.sotrudName,s.sotrudPatr"),
    @NamedQuery(name = "Sotrud.findBySotrudId", query = "SELECT s FROM Sotrud s WHERE s.sotrudId = :sotrudId"),
    @NamedQuery(name = "Sotrud.findBySotrudFam", query = "SELECT s FROM Sotrud s WHERE s.sotrudFam like :sotrudFam"),
    @NamedQuery(name = "Sotrud.findBySotrudName", query = "SELECT s FROM Sotrud s WHERE s.sotrudName = :sotrudName"),
    @NamedQuery(name = "Sotrud.findBySotrudPatr", query = "SELECT s FROM Sotrud s WHERE s.sotrudPatr = :sotrudPatr"),
    @NamedQuery(name = "Sotrud.findBySprDolgnId", query = "SELECT s FROM Sotrud s WHERE s.sprdolgnId = :sprdolgnId ORDER BY s.sotrudFam,s.sotrudName,s.sotrudPatr"),
    @NamedQuery(name = "Sotrud.findSpec", query = "SELECT s FROM Sotrud s, SprDolgn d WHERE d.sprdolgnType = :sprdolgnType"),
    @NamedQuery(name = "Sotrud.findByDolgnFam", query = "SELECT s FROM Sotrud s WHERE s.sprdolgnId = :sprdolgnId AND s.sotrudFam = :sotrudFam ORDER BY s.sotrudFam,s.sotrudName,s.sotrudPatr"),
    @NamedQuery(name = "Sotrud.findByDolgnAct", query = "SELECT s FROM Sotrud s WHERE s.sprdolgnId = :sprdolgnId AND s.sotrudActive = 1 ORDER BY s.sotrudFam,s.sotrudName,s.sotrudPatr"),
    @NamedQuery(name = "Sotrud.findAllAct", query = "SELECT s FROM Sotrud s WHERE s.sotrudActive = 1 ORDER BY s.sotrudFam,s.sotrudName,s.sotrudPatr")})
public class Sotrud implements Serializable {

    @Column(name = "SOTRUD_ACTIVE")
    private Integer sotrudActive;
    /*@Column(name = "SOTRUD_ACTIVE")
    private Integer sotrudActive;*/
    @OneToMany(mappedBy = "sotrudId")
    private Collection<SotrudDolgn> sotrudDolgnCollection;

    @OneToMany(mappedBy = "sotrudId")
    private Collection<PayuslSotrud> payuslSotrudCollection;
    @OneToMany(mappedBy = "sotrudId")
    private Collection<GroupSotrud> groupSotrudCollection;
    @OneToMany(mappedBy = "sotrudId")
    private Collection<PriyomSotrud> priyomSotrudCollection;
    @OneToMany(mappedBy = "sotrudId")
    private Collection<Users> usersCollection;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SOTRUD_ID")
    @SequenceGenerator(name = "SEQ_SOTRUD", sequenceName = "SEQ_SOTRUD", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SEQ_SOTRUD")
    private Integer sotrudId;
    @Size(max = 100)
    @Column(name = "SOTRUD_FAM")
    private String sotrudFam;
    @Size(max = 100)
    @Column(name = "SOTRUD_NAME")
    private String sotrudName;
    @Size(max = 100)
    @Column(name = "SOTRUD_PATR")
    private String sotrudPatr;
    @JoinColumn(name = "SPRDOLGN_ID", referencedColumnName = "SPRDOLGN_ID")
    @ManyToOne
    private SprDolgn sprdolgnId;

    public Sotrud() {
    }

    public Sotrud(Integer sotrudId) {
        this.sotrudId = sotrudId;
    }

    public Integer getSotrudId() {
        return sotrudId;
    }

    public void setSotrudId(Integer sotrudId) {
        this.sotrudId = sotrudId;
    }

    public String getSotrudFam() {
        return sotrudFam;
    }

    public void setSotrudFam(String sotrudFam) {
        this.sotrudFam = sotrudFam;
    }

    public String getSotrudName() {
        return sotrudName;
    }

    public void setSotrudName(String sotrudName) {
        this.sotrudName = sotrudName;
    }

    public String getSotrudPatr() {
        return sotrudPatr;
    }

    public void setSotrudPatr(String sotrudPatr) {
        this.sotrudPatr = sotrudPatr;
    }

    public SprDolgn getSprdolgnId() {
        return sprdolgnId;
    }

    public void setSprdolgnId(SprDolgn sprdolgnId) {
        this.sprdolgnId = sprdolgnId;
    }

    public String getSotrudFIO() {
        String fio = sotrudFam + " " + sotrudName.charAt(0) + "." + sotrudPatr.charAt(0) + ".";
        return fio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sotrudId != null ? sotrudId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sotrud)) {
            return false;
        }
        Sotrud other = (Sotrud) object;
        if ((this.sotrudId == null && other.sotrudId != null) || (this.sotrudId != null && !this.sotrudId.equals(other.sotrudId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Sotrud[ sotrudId=" + sotrudId + " ]";
    }

    @XmlTransient
    public Collection<Users> getUsersCollection() {
        return usersCollection;
    }

    public void setUsersCollection(Collection<Users> usersCollection) {
        this.usersCollection = usersCollection;
    }

    @XmlTransient
    public Collection<PriyomSotrud> getPriyomSotrudCollection() {
        return priyomSotrudCollection;
    }

    public void setPriyomSotrudCollection(Collection<PriyomSotrud> priyomSotrudCollection) {
        this.priyomSotrudCollection = priyomSotrudCollection;
    }

    public Integer getSotrudActive() {
        return sotrudActive;
    }

    public void setSotrudActive(Integer sotrudActive) {
        this.sotrudActive = sotrudActive;
    }

    @XmlTransient
    public Collection<GroupSotrud> getGroupSotrudCollection() {
        return groupSotrudCollection;
    }

    public void setGroupSotrudCollection(Collection<GroupSotrud> groupSotrudCollection) {
        this.groupSotrudCollection = groupSotrudCollection;
    }

    @XmlTransient
    public Collection<PayuslSotrud> getPayuslSotrudCollection() {
        return payuslSotrudCollection;
    }

    public void setPayuslSotrudCollection(Collection<PayuslSotrud> payuslSotrudCollection) {
        this.payuslSotrudCollection = payuslSotrudCollection;
    }

    @XmlTransient
    public Collection<SotrudDolgn> getSotrudDolgnCollection() {
        return sotrudDolgnCollection;
    }

    public void setSotrudDolgnCollection(Collection<SotrudDolgn> sotrudDolgnCollection) {
        this.sotrudDolgnCollection = sotrudDolgnCollection;
    }

}
