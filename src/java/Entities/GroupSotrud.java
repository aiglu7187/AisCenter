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
@Table(name = "GROUP_SOTRUD")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GroupSotrud.findAll", query = "SELECT g FROM GroupSotrud g"),
    @NamedQuery(name = "GroupSotrud.findByGrsotrudId", query = "SELECT g FROM GroupSotrud g WHERE g.grsotrudId = :grsotrudId")})
public class GroupSotrud implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "GRSOTRUD_ID")
    @SequenceGenerator(name = "SEQ_GROUP_SOTRUD", sequenceName = "SEQ_GROUP_SOTRUD", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.IDENTITY, generator="SEQ_GROUP_SOTRUD")
    private Integer grsotrudId;
    @JoinColumn(name = "GROUP_ID", referencedColumnName = "GROUP_ID")
    @ManyToOne
    private Groups groupId;
    @JoinColumn(name = "SOTRUD_ID", referencedColumnName = "SOTRUD_ID")
    @ManyToOne
    private Sotrud sotrudId;

    public GroupSotrud() {
    }

    public GroupSotrud(Integer grsotrudId) {
        this.grsotrudId = grsotrudId;
    }

    public Integer getGrsotrudId() {
        return grsotrudId;
    }

    public void setGrsotrudId(Integer grsotrudId) {
        this.grsotrudId = grsotrudId;
    }

    public Groups getGroupId() {
        return groupId;
    }

    public void setGroupId(Groups groupId) {
        this.groupId = groupId;
    }

    public Sotrud getSotrudId() {
        return sotrudId;
    }

    public void setSotrudId(Sotrud sotrudId) {
        this.sotrudId = sotrudId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (grsotrudId != null ? grsotrudId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GroupSotrud)) {
            return false;
        }
        GroupSotrud other = (GroupSotrud) object;
        if ((this.grsotrudId == null && other.grsotrudId != null) || (this.grsotrudId != null && !this.grsotrudId.equals(other.grsotrudId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.GroupSotrud[ grsotrudId=" + grsotrudId + " ]";
    }
    
}
