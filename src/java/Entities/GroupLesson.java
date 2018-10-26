/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Aiglu
 */
@Entity
@Table(name = "GROUP_LESSON")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GroupLesson.findAll", query = "SELECT g FROM GroupLesson g"),
    @NamedQuery(name = "GroupLesson.findByGrlessId", query = "SELECT g FROM GroupLesson g WHERE g.grlessId = :grlessId"),
    @NamedQuery(name = "GroupLesson.findByGrlessDate", query = "SELECT g FROM GroupLesson g WHERE g.grlessDate = :grlessDate")})
public class GroupLesson implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "GRLESS_ID")
    @SequenceGenerator(name = "SEQ_GROUP_LESSON", sequenceName = "SEQ_GROUP_LESSON", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.IDENTITY, generator="SEQ_GROUP_LESSON")
    private Integer grlessId;
    @Column(name = "GRLESS_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date grlessDate;
    @JoinColumn(name = "GROUP_ID", referencedColumnName = "GROUP_ID")
    @ManyToOne
    private Groups groupId;
    @OneToMany(mappedBy = "grlessId")
    private Collection<GrlesschildP> grlesschildPCollection;

    public GroupLesson() {
    }

    public GroupLesson(Integer grlessId) {
        this.grlessId = grlessId;
    }

    public Integer getGrlessId() {
        return grlessId;
    }

    public void setGrlessId(Integer grlessId) {
        this.grlessId = grlessId;
    }

    public Date getGrlessDate() {
        return grlessDate;
    }

    public void setGrlessDate(Date grlessDate) {
        this.grlessDate = grlessDate;
    }

    public Groups getGroupId() {
        return groupId;
    }

    public void setGroupId(Groups groupId) {
        this.groupId = groupId;
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
        hash += (grlessId != null ? grlessId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GroupLesson)) {
            return false;
        }
        GroupLesson other = (GroupLesson) object;
        if ((this.grlessId == null && other.grlessId != null) || (this.grlessId != null && !this.grlessId.equals(other.grlessId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.GroupLesson[ grlessId=" + grlessId + " ]";
    }
    
}
