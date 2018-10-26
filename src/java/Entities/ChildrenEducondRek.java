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
@Table(name = "CHILDREN_EDUCOND_REK")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ChildrenEducondRek.findAll", query = "SELECT c FROM ChildrenEducondRek c"),
    @NamedQuery(name = "ChildrenEducondRek.findByChildreneducondrekId", query = "SELECT c FROM ChildrenEducondRek c WHERE c.childreneducondrekId = :childreneducondrekId"),
    @NamedQuery(name = "ChildrenEducondRek.findByChildreneducondId", query = "SELECT c FROM ChildrenEducondRek c WHERE c.childreneducondId = :childreneducondId")})
public class ChildrenEducondRek implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "CHILDRENEDUCONDREK_ID")
    @SequenceGenerator(name = "SEQ_CHILDREN_EDUCOND_REK", sequenceName = "SEQ_CHILDREN_EDUCOND_REK", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SEQ_CHILDREN_EDUCOND_REK")
    private Integer childreneducondrekId;
    @JoinColumn(name = "CHILDRENEDUCOND_ID", referencedColumnName = "CHILDRENEDUCOND_ID")
    @ManyToOne
    private ChildrenEducond childreneducondId;
    @JoinColumn(name = "SPRREK_ID", referencedColumnName = "SPRREK_ID")
    @ManyToOne
    private SprRekomend sprrekId;

    public ChildrenEducondRek() {
    }

    public ChildrenEducondRek(Integer childreneducondrekId) {
        this.childreneducondrekId = childreneducondrekId;
    }

    public Integer getChildreneducondrekId() {
        return childreneducondrekId;
    }

    public void setChildreneducondrekId(Integer childreneducondrekId) {
        this.childreneducondrekId = childreneducondrekId;
    }

    public ChildrenEducond getChildreneducondId() {
        return childreneducondId;
    }

    public void setChildreneducondId(ChildrenEducond childreneducondId) {
        this.childreneducondId = childreneducondId;
    }

    public SprRekomend getSprrekId() {
        return sprrekId;
    }

    public void setSprrekId(SprRekomend sprrekId) {
        this.sprrekId = sprrekId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (childreneducondrekId != null ? childreneducondrekId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ChildrenEducondRek)) {
            return false;
        }
        ChildrenEducondRek other = (ChildrenEducondRek) object;
        if ((this.childreneducondrekId == null && other.childreneducondrekId != null) || (this.childreneducondrekId != null && !this.childreneducondrekId.equals(other.childreneducondrekId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.ChildrenEducondRek[ childreneducondrekId=" + childreneducondrekId + " ]";
    }

}
