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
@Table(name = "CHILDREN_EDUCOND")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ChildrenEducond.findAll", query = "SELECT c FROM ChildrenEducond c"),
    @NamedQuery(name = "ChildrenEducond.findByChildreneducondId", query = "SELECT c FROM ChildrenEducond c WHERE c.childreneducondId = :childreneducondId"),
    @NamedQuery(name = "ChildrenEducond.findByChildreneducondZakl", query = "SELECT c FROM ChildrenEducond c WHERE c.childreneducondZakl = :childreneducondZakl"),
    @NamedQuery(name = "ChildrenEducond.findByChildId", query = "SELECT c FROM ChildrenEducond c WHERE c.childId = :childId")})
public class ChildrenEducond implements Serializable {

    @Column(name = "CHILDRENEDUCOND_ZAKL")
    private Integer childreneducondZakl;
    @OneToMany(mappedBy = "childreneducondId")
    private Collection<MonitoringEducond> monitoringEducondCollection;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "CHILDRENEDUCOND_ID")
    @SequenceGenerator(name = "SEQ_CHILDREN_EDUCOND", sequenceName = "SEQ_CHILDREN_EDUCOND", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SEQ_CHILDREN_EDUCOND")
    private Integer childreneducondId;
    @OneToMany(mappedBy = "childreneducondId")
    private Collection<ChildrenEducondRek> childrenEducondRekCollection;
    @JoinColumn(name = "CHILD_ID", referencedColumnName = "CHILD_ID")
    @ManyToOne
    private Children childId;
    @JoinColumn(name = "SPREDUFORM_ID", referencedColumnName = "SPREDUFORM_ID")
    @ManyToOne
    private SprEduform spreduformId;
    @JoinColumn(name = "SPROBR_ID", referencedColumnName = "SPROBR_ID")
    @ManyToOne
    private SprObr sprobrId;
    @JoinColumn(name = "SPRVAR_ID", referencedColumnName = "SPRVAR_ID")
    @ManyToOne
    private SprVar sprvarId;
    @JoinColumn(name = "SPROU_ID", referencedColumnName = "SPROU_ID")
    @ManyToOne
    private SprOu sprouId;
    @JoinColumn(name = "SPRSTAGE_ID", referencedColumnName = "SPRSTAGE_ID")
    @ManyToOne
    private SprStage sprstageId;
    @OneToMany(mappedBy = "childreneducondId")
    private Collection<ChildrenEducondEq> childrenEducondEqCollection;

    public ChildrenEducond() {
    }

    public ChildrenEducond(Integer childreneducondId) {
        this.childreneducondId = childreneducondId;
    }

    public Integer getChildreneducondId() {
        return childreneducondId;
    }

    public void setChildreneducondId(Integer childreneducondId) {
        this.childreneducondId = childreneducondId;
    }

    public Integer getChildreneducondZakl() {
        return childreneducondZakl;
    }

    public void setChildreneducondZakl(Integer childreneducondZakl) {
        this.childreneducondZakl = childreneducondZakl;
    }

    @XmlTransient
    public Collection<ChildrenEducondRek> getChildrenEducondRekCollection() {
        return childrenEducondRekCollection;
    }

    public void setChildrenEducondRekCollection(Collection<ChildrenEducondRek> childrenEducondRekCollection) {
        this.childrenEducondRekCollection = childrenEducondRekCollection;
    }

    public Children getChildId() {
        return childId;
    }

    public void setChildId(Children childId) {
        this.childId = childId;
    }

    public SprEduform getSpreduformId() {
        return spreduformId;
    }

    public void setSpreduformId(SprEduform spreduformId) {
        this.spreduformId = spreduformId;
    }

    public SprObr getSprobrId() {
        return sprobrId;
    }

    public void setSprobrId(SprObr sprobrId) {
        this.sprobrId = sprobrId;
    }

    public SprVar getSprvarId() {
        return sprvarId;
    }

    public void setSprvarId(SprVar sprvarId) {
        this.sprvarId = sprvarId;
    }

    public SprOu getSprouId() {
        return sprouId;
    }

    public void setSprouId(SprOu sprouId) {
        this.sprouId = sprouId;
    }

    public SprStage getSprstageId() {
        return sprstageId;
    }

    public void setSprstageId(SprStage sprstageId) {
        this.sprstageId = sprstageId;
    }

    @XmlTransient
    public Collection<ChildrenEducondEq> getChildrenEducondEqCollection() {
        return childrenEducondEqCollection;
    }

    public void setChildrenEducondEqCollection(Collection<ChildrenEducondEq> childrenEducondEqCollection) {
        this.childrenEducondEqCollection = childrenEducondEqCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (childreneducondId != null ? childreneducondId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ChildrenEducond)) {
            return false;
        }
        ChildrenEducond other = (ChildrenEducond) object;
        if ((this.childreneducondId == null && other.childreneducondId != null) || (this.childreneducondId != null && !this.childreneducondId.equals(other.childreneducondId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.ChildrenEducond[ childreneducondId=" + childreneducondId + " ]";
    }

    @XmlTransient
    public Collection<MonitoringEducond> getMonitoringEducondCollection() {
        return monitoringEducondCollection;
    }

    public void setMonitoringEducondCollection(Collection<MonitoringEducond> monitoringEducondCollection) {
        this.monitoringEducondCollection = monitoringEducondCollection;
    }

}
