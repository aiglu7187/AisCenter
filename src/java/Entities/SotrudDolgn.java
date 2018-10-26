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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Aiglu
 */
@Entity
@Table(name = "SOTRUD_DOLGN")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SotrudDolgn.findAll", query = "SELECT s FROM SotrudDolgn s"),
    @NamedQuery(name = "SotrudDolgn.findBySotruddolgnId", query = "SELECT s FROM SotrudDolgn s WHERE s.sotruddolgnId = :sotruddolgnId"),
    @NamedQuery(name = "SotrudDolgn.findBySotruddolgnActive", query = "SELECT s FROM SotrudDolgn s WHERE s.sotruddolgnActive = :sotruddolgnActive"),
    @NamedQuery(name = "SotrudDolgn.findBySotrudId", query = "SELECT s FROM SotrudDolgn s WHERE s.sotrudId = :sotrudId"),
    @NamedQuery(name = "SotrudDolgn.findBySprdolgnId", query = "SELECT s FROM SotrudDolgn s WHERE s.sprdolgnId = :sprdolgnId")})
public class SotrudDolgn implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SOTRUDDOLGN_ID")
    @SequenceGenerator(name = "SEQ_SOTRUD_DOLGN", sequenceName = "SEQ_SOTRUD_DOLGN", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.IDENTITY, generator="SEQ_SOTRUD_DOLGN")
    private Integer sotruddolgnId;
    @Column(name = "SOTRUDDOLGN_ACTIVE")
    private Integer sotruddolgnActive;
    @JoinColumn(name = "SOTRUD_ID", referencedColumnName = "SOTRUD_ID")
    @ManyToOne
    private Sotrud sotrudId;
    @JoinColumn(name = "SPRDOLGN_ID", referencedColumnName = "SPRDOLGN_ID")
    @ManyToOne
    private SprDolgn sprdolgnId;
    @OneToMany(mappedBy = "sotruddolgnId")
    private Collection<PriyomSotrud> priyomSotrudCollection;

    public SotrudDolgn() {
    }

    public SotrudDolgn(Integer sotruddolgnId) {
        this.sotruddolgnId = sotruddolgnId;
    }

    public Integer getSotruddolgnId() {
        return sotruddolgnId;
    }

    public void setSotruddolgnId(Integer sotruddolgnId) {
        this.sotruddolgnId = sotruddolgnId;
    }

    public Integer getSotruddolgnActive() {
        return sotruddolgnActive;
    }

    public void setSotruddolgnActive(Integer sotruddolgnActive) {
        this.sotruddolgnActive = sotruddolgnActive;
    }

    public Sotrud getSotrudId() {
        return sotrudId;
    }

    public void setSotrudId(Sotrud sotrudId) {
        this.sotrudId = sotrudId;
    }

    public SprDolgn getSprdolgnId() {
        return sprdolgnId;
    }

    public void setSprdolgnId(SprDolgn sprdolgnId) {
        this.sprdolgnId = sprdolgnId;
    }

    @XmlTransient
    public Collection<PriyomSotrud> getPriyomSotrudCollection() {
        return priyomSotrudCollection;
    }

    public void setPriyomSotrudCollection(Collection<PriyomSotrud> priyomSotrudCollection) {
        this.priyomSotrudCollection = priyomSotrudCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sotruddolgnId != null ? sotruddolgnId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SotrudDolgn)) {
            return false;
        }
        SotrudDolgn other = (SotrudDolgn) object;
        if ((this.sotruddolgnId == null && other.sotruddolgnId != null) || (this.sotruddolgnId != null && !this.sotruddolgnId.equals(other.sotruddolgnId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.SotrudDolgn[ sotruddolgnId=" + sotruddolgnId + " ]";
    }

}
