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
 * @author Aiglu
 */
@Entity
@Table(name = "SPR_OO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SprOo.findAll", query = "SELECT s FROM SprOo s"),
    @NamedQuery(name = "SprOo.findBySprooId", query = "SELECT s FROM SprOo s WHERE s.sprooId = :sprooId"),
    @NamedQuery(name = "SprOo.findBySprooName", query = "SELECT s FROM SprOo s WHERE s.sprooName = :sprooName"),
    @NamedQuery(name = "SprOo.findBySprooNameAndSprregId", query = "SELECT s FROM SprOo s WHERE s.sprooName = :sprooName AND s.sprregId = :sprregId")})
public class SprOo implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SPROO_ID")
    @SequenceGenerator(name = "SEQ_SPR_OO", sequenceName = "SEQ_SPR_OO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SEQ_SPR_OO")
    private Integer sprooId;
    @Size(max = 500)
    @Column(name = "SPROO_NAME")
    private String sprooName;
    @JoinColumn(name = "SPRREG_ID", referencedColumnName = "SPRREG_ID")
    @ManyToOne
    private SprRegion sprregId;
    @OneToMany(mappedBy = "sprooId")
    private Collection<ChildEduplace> childEduplaceCollection;

    public SprOo() {
    }

    public SprOo(Integer sprooId) {
        this.sprooId = sprooId;
    }

    public Integer getSprooId() {
        return sprooId;
    }

    public void setSprooId(Integer sprooId) {
        this.sprooId = sprooId;
    }

    public String getSprooName() {
        return sprooName;
    }

    public void setSprooName(String sprooName) {
        this.sprooName = sprooName;
    }

    public SprRegion getSprregId() {
        return sprregId;
    }

    public void setSprregId(SprRegion sprregId) {
        this.sprregId = sprregId;
    }

    @XmlTransient
    public Collection<ChildEduplace> getChildEduplaceCollection() {
        return childEduplaceCollection;
    }

    public void setChildEduplaceCollection(Collection<ChildEduplace> childEduplaceCollection) {
        this.childEduplaceCollection = childEduplaceCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sprooId != null ? sprooId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SprOo)) {
            return false;
        }
        SprOo other = (SprOo) object;
        if ((this.sprooId == null && other.sprooId != null) || (this.sprooId != null && !this.sprooId.equals(other.sprooId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.SprOo[ sprooId=" + sprooId + " ]";
    }

}
