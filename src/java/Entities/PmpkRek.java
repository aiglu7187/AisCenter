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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Aiglu
 */
@Entity
@Table(name = "PMPK_REK")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PmpkRek.findAll", query = "SELECT p FROM PmpkRek p"),
    @NamedQuery(name = "PmpkRek.findByPmpkrekId", query = "SELECT p FROM PmpkRek p WHERE p.pmpkrekId = :pmpkrekId"),
    @NamedQuery(name = "PmpkRek.findByPmpkId", query = "SELECT p FROM PmpkRek p WHERE p.pmpkId = :pmpkId")})
public class PmpkRek implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "PMPKREK_ID")
    @SequenceGenerator(name = "SEQ_PMPKREK", sequenceName = "SEQ_PMPKREK", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.IDENTITY, generator="SEQ_PMPKREK")
    private Integer pmpkrekId;
    @JoinColumn(name = "SPRREK_ID", referencedColumnName = "SPRREK_ID")
    @ManyToOne
    private SprRekomend sprrekId;
    @JoinColumn(name = "PMPK_ID", referencedColumnName = "PMPK_ID")
    @ManyToOne
    private Pmpk pmpkId;

    public PmpkRek() {
    }

    public PmpkRek(Integer pmpkrekId) {
        this.pmpkrekId = pmpkrekId;
    }

    public Integer getPmpkrekId() {
        return pmpkrekId;
    }

    public void setPmpkrekId(Integer pmpkrekId) {
        this.pmpkrekId = pmpkrekId;
    }

    public SprRekomend getSprrekId() {
        return sprrekId;
    }

    public void setSprrekId(SprRekomend sprrekId) {
        this.sprrekId = sprrekId;
    }

    public Pmpk getPmpkId() {
        return pmpkId;
    }

    public void setPmpkId(Pmpk pmpkId) {
        this.pmpkId = pmpkId;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pmpkrekId != null ? pmpkrekId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PmpkRek)) {
            return false;
        }
        PmpkRek other = (PmpkRek) object;
        if ((this.pmpkrekId == null && other.pmpkrekId != null) || (this.pmpkrekId != null && !this.pmpkrekId.equals(other.pmpkrekId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.PmpkRek[ pmpkrekId=" + pmpkrekId + " ]";
    }
    
}
