/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Aiglu
 */
@Entity
@Table(name = "SPR_IPRAEDUCOND")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SprIpraeducond.findAll", query = "SELECT s FROM SprIpraeducond s"),
    @NamedQuery(name = "SprIpraeducond.findBySpripraeducondId", query = "SELECT s FROM SprIpraeducond s WHERE s.spripraeducondId = :spripraeducondId"),
    @NamedQuery(name = "SprIpraeducond.findBySpripraeducondName", query = "SELECT s FROM SprIpraeducond s WHERE s.spripraeducondName = :spripraeducondName")})
public class SprIpraeducond implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SPRIPRAEDUCOND_ID")
    @SequenceGenerator(name = "SEQ_SPR_IPRAEDUCOND", sequenceName = "SEQ_SPR_IPRAEDUCOND", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.IDENTITY, generator="SEQ_SPR_IPRAEDUCOND")
    private Integer spripraeducondId;
    @Size(max = 1000)
    @Column(name = "SPRIPRAEDUCOND_NAME")
    private String spripraeducondName;
    @JoinColumn(name = "IPRAEDUCONDTYPE_ID", referencedColumnName = "IPRAEDUCONDTYPE_ID")
    @ManyToOne
    private IpraeducondType ipraeducondtypeId;

    public SprIpraeducond() {
    }

    public SprIpraeducond(Integer spripraeducondId) {
        this.spripraeducondId = spripraeducondId;
    }

    public Integer getSpripraeducondId() {
        return spripraeducondId;
    }

    public void setSpripraeducondId(Integer spripraeducondId) {
        this.spripraeducondId = spripraeducondId;
    }

    public String getSpripraeducondName() {
        return spripraeducondName;
    }

    public void setSpripraeducondName(String spripraeducondName) {
        this.spripraeducondName = spripraeducondName;
    }

    public IpraeducondType getIpraeducondtypeId() {
        return ipraeducondtypeId;
    }

    public void setIpraeducondtypeId(IpraeducondType ipraeducondtypeId) {
        this.ipraeducondtypeId = ipraeducondtypeId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (spripraeducondId != null ? spripraeducondId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SprIpraeducond)) {
            return false;
        }
        SprIpraeducond other = (SprIpraeducond) object;
        if ((this.spripraeducondId == null && other.spripraeducondId != null) || (this.spripraeducondId != null && !this.spripraeducondId.equals(other.spripraeducondId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.SprIpraeducond[ spripraeducondId=" + spripraeducondId + " ]";
    }
    
}
