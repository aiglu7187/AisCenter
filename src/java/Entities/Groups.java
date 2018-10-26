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
@Table(name = "GROUPS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Groups.findAll", query = "SELECT g FROM Groups g"),
    @NamedQuery(name = "Groups.findByGroupId", query = "SELECT g FROM Groups g WHERE g.groupId = :groupId"),
    @NamedQuery(name = "Groups.findByGroupName", query = "SELECT g FROM Groups g WHERE g.groupName = :groupName")})
public class Groups implements Serializable {

    @Column(name = "GROUP_STATUS")
    private Integer groupStatus;
    @Column(name = "GROUP_KOL")
    private Integer groupKol;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "GROUP_ID")
    @SequenceGenerator(name = "SEQ_GROUPS", sequenceName = "SEQ_GROUPS", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.IDENTITY, generator="SEQ_GROUPS")
    private Integer groupId;
    @Size(max = 50)
    @Column(name = "GROUP_NAME")
    private String groupName;
    @OneToMany(mappedBy = "groupId")
    private Collection<Dogovor> dogovorCollection;
    @OneToMany(mappedBy = "groupId")
    private Collection<GroupLesson> groupLessonCollection;
    @JoinColumn(name = "SPRUSL_ID", referencedColumnName = "SPRUSL_ID")
    @ManyToOne
    private SprUsl spruslId;
    @OneToMany(mappedBy = "groupId")
    private Collection<GroupChild> groupChildCollection;
    @OneToMany(mappedBy = "groupId")
    private Collection<GroupSotrud> groupSotrudCollection;

    public Groups() {
    }

    public Groups(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @XmlTransient
    public Collection<Dogovor> getDogovorCollection() {
        return dogovorCollection;
    }

    public void setDogovorCollection(Collection<Dogovor> dogovorCollection) {
        this.dogovorCollection = dogovorCollection;
    }

    @XmlTransient
    public Collection<GroupLesson> getGroupLessonCollection() {
        return groupLessonCollection;
    }

    public void setGroupLessonCollection(Collection<GroupLesson> groupLessonCollection) {
        this.groupLessonCollection = groupLessonCollection;
    }

    public SprUsl getSpruslId() {
        return spruslId;
    }

    public void setSpruslId(SprUsl spruslId) {
        this.spruslId = spruslId;
    }

    @XmlTransient
    public Collection<GroupChild> getGroupChildCollection() {
        return groupChildCollection;
    }

    public void setGroupChildCollection(Collection<GroupChild> groupChildCollection) {
        this.groupChildCollection = groupChildCollection;
    }

    @XmlTransient
    public Collection<GroupSotrud> getGroupSotrudCollection() {
        return groupSotrudCollection;
    }

    public void setGroupSotrudCollection(Collection<GroupSotrud> groupSotrudCollection) {
        this.groupSotrudCollection = groupSotrudCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (groupId != null ? groupId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Groups)) {
            return false;
        }
        Groups other = (Groups) object;
        if ((this.groupId == null && other.groupId != null) || (this.groupId != null && !this.groupId.equals(other.groupId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Groups[ groupId=" + groupId + " ]";
    }

    public Integer getGroupStatus() {
        return groupStatus;
    }

    public void setGroupStatus(Integer groupStatus) {
        this.groupStatus = groupStatus;
    }
    
    public Integer getGroupKol() {
        return groupKol;
    }

    public void setGroupKol(Integer groupKol) {
        this.groupKol= groupKol;
    }
    
}
