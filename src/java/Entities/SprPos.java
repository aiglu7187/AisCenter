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
 * @author Aiglu
 */
@Entity
@Table(name = "SPR_POS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SprPos.findAll", query = "SELECT s FROM SprPos s"),
    @NamedQuery(name = "SprPos.findBySprposId", query = "SELECT s FROM SprPos s WHERE s.sprposId = :sprposId"),
    @NamedQuery(name = "SprPos.findBySprposName", query = "SELECT s FROM SprPos s WHERE s.sprposName = :sprposName")})
public class SprPos implements Serializable {

    @OneToMany(mappedBy = "sprposId")
    private Collection<Payusllespos> payusllesposCollection;

    

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SPRPOS_ID")
    @SequenceGenerator(name = "SEQ_SPR_POS", sequenceName = "SEQ_SPR_POS", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.IDENTITY, generator="SEQ_SPR_POS")
    private Integer sprposId;
    @Size(max = 10)
    @Column(name = "SPRPOS_NAME")
    private String sprposName;
    @OneToMany(mappedBy = "sprposId")
    private Collection<GrlesschildP> grlesschildPCollection;

    public SprPos() {
    }

    public SprPos(Integer sprposId) {
        this.sprposId = sprposId;
    }

    public Integer getSprposId() {
        return sprposId;
    }

    public void setSprposId(Integer sprposId) {
        this.sprposId = sprposId;
    }

    public String getSprposName() {
        return sprposName;
    }

    public void setSprposName(String sprposName) {
        this.sprposName = sprposName;
    }

    @XmlTransient
    public Collection<GrlesschildP> getGrlesschildPCollection() {
        return grlesschildPCollection;
    }

    public void setGrlesschildPCollection(Collection<GrlesschildP> grlesschildPCollection) {
        this.grlesschildPCollection = grlesschildPCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sprposId != null ? sprposId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SprPos)) {
            return false;
        }
        SprPos other = (SprPos) object;
        if ((this.sprposId == null && other.sprposId != null) || (this.sprposId != null && !this.sprposId.equals(other.sprposId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.SprPos[ sprposId=" + sprposId + " ]";
    }

    @XmlTransient
    public Collection<Payusllespos> getPayusllesposCollection() {
        return payusllesposCollection;
    }

    public void setPayusllesposCollection(Collection<Payusllespos> payusllesposCollection) {
        this.payusllesposCollection = payusllesposCollection;
    }

    
    
}
