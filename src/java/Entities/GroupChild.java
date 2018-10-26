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
@Table(name = "GROUP_CHILD")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GroupChild.findAll", query = "SELECT g FROM GroupChild g"),
    @NamedQuery(name = "GroupChild.findByGrchildId", query = "SELECT g FROM GroupChild g WHERE g.grchildId = :grchildId")})
public class GroupChild implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "GRCHILD_ID")
    @SequenceGenerator(name = "SEQ_GROUP_CHILD", sequenceName = "SEQ_GROUP_CHILD", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.IDENTITY, generator="SEQ_GROUP_CHILD")
    private Integer grchildId;
    @JoinColumn(name = "CHILD_ID", referencedColumnName = "CHILD_ID")
    @ManyToOne
    private Children childId;
    @JoinColumn(name = "GROUP_ID", referencedColumnName = "GROUP_ID")
    @ManyToOne
    private Groups groupId;

    public GroupChild() {
    }

    public GroupChild(Integer grchildId) {
        this.grchildId = grchildId;
    }

    public Integer getGrchildId() {
        return grchildId;
    }

    public void setGrchildId(Integer grchildId) {
        this.grchildId = grchildId;
    }

    public Children getChildId() {
        return childId;
    }

    public void setChildId(Children childId) {
        this.childId = childId;
    }

    public Groups getGroupId() {
        return groupId;
    }

    public void setGroupId(Groups groupId) {
        this.groupId = groupId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (grchildId != null ? grchildId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GroupChild)) {
            return false;
        }
        GroupChild other = (GroupChild) object;
        if ((this.grchildId == null && other.grchildId != null) || (this.grchildId != null && !this.grchildId.equals(other.grchildId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.GroupChild[ grchildId=" + grchildId + " ]";
    }
    
}
