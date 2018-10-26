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
@Table(name = "SPR_STAGE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SprStage.findAll", query = "SELECT s FROM SprStage s ORDER BY s.sprstageName"),
    @NamedQuery(name = "SprStage.findBySprstageId", query = "SELECT s FROM SprStage s WHERE s.sprstageId = :sprstageId"),
    @NamedQuery(name = "SprStage.findBySprstageName", query = "SELECT s FROM SprStage s WHERE s.sprstageName = :sprstageName")})
public class SprStage implements Serializable {

    @OneToMany(mappedBy = "sprstageId")
    private Collection<ChildrenEducond> childrenEducondCollection;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SPRSTAGE_ID")
    @SequenceGenerator(name = "SEQ_SPR_STAGE", sequenceName = "SEQ_SPR_STAGE", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.IDENTITY, generator="SEQ_SPR_STAGE")
    private Integer sprstageId;
    @Size(max = 50)
    @Column(name = "SPRSTAGE_NAME")
    private String sprstageName;
    @OneToMany(mappedBy = "sprstageId")
    private Collection<ChildEduplace> childEduplaceCollection;

    public SprStage() {
    }

    public SprStage(Integer sprstageId) {
        this.sprstageId = sprstageId;
    }

    public Integer getSprstageId() {
        return sprstageId;
    }

    public void setSprstageId(Integer sprstageId) {
        this.sprstageId = sprstageId;
    }

    public String getSprstageName() {
        return sprstageName;
    }

    public void setSprstageName(String sprstageName) {
        this.sprstageName = sprstageName;
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
        hash += (sprstageId != null ? sprstageId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SprStage)) {
            return false;
        }
        SprStage other = (SprStage) object;
        if ((this.sprstageId == null && other.sprstageId != null) || (this.sprstageId != null && !this.sprstageId.equals(other.sprstageId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.SprStage[ sprstageId=" + sprstageId + " ]";
    }

    @XmlTransient
    public Collection<ChildrenEducond> getChildrenEducondCollection() {
        return childrenEducondCollection;
    }

    public void setChildrenEducondCollection(Collection<ChildrenEducond> childrenEducondCollection) {
        this.childrenEducondCollection = childrenEducondCollection;
    }
    
}
