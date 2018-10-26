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
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Aiglu
 */
@Entity
@Table(name = "CHILDREN_VIEW")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ChildrenView.findAll", query = "SELECT c FROM ChildrenView c"),
    @NamedQuery(name = "ChildrenView.findByChildId", query = "SELECT c FROM ChildrenView c WHERE c.childId = :childId"),
    @NamedQuery(name = "ChildrenView.findByChfam", query = "SELECT c FROM ChildrenView c WHERE c.chfam = :chfam"),
    @NamedQuery(name = "ChildrenView.findByChname", query = "SELECT c FROM ChildrenView c WHERE c.chname = :chname"),
    @NamedQuery(name = "ChildrenView.findByChpatr", query = "SELECT c FROM ChildrenView c WHERE c.chpatr = :chpatr"),
    @NamedQuery(name = "ChildrenView.findByChildFIO", query = "SELECT c FROM ChildrenView c WHERE (c.chfam like :fam) "
            + "AND (c.chname like :name) AND ((c.chpatr like :patr) OR (c.chpatr IS NULL)) ORDER BY c.chfam,c.chname,c.chpatr")})
public class ChildrenView implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "CHILD_ID")
    private Integer childId;
    @Size(max = 100)
    @Column(name = "CHFAM")
    private String chfam;
    @Size(max = 100)
    @Column(name = "CHNAME")
    private String chname;
    @Size(max = 100)
    @Column(name = "CHPATR")
    private String chpatr;

    public ChildrenView() {
    }

    public ChildrenView(Integer childId) {
        this.childId = childId;
    }

    public Integer getChildId() {
        return childId;
    }

    public void setChildId(Integer childId) {
        this.childId = childId;
    }

    public String getChfam() {
        return chfam;
    }

    public void setChfam(String chfam) {
        this.chfam = chfam;
    }

    public String getChname() {
        return chname;
    }

    public void setChname(String chname) {
        this.chname = chname;
    }

    public String getChpatr() {
        return chpatr;
    }

    public void setChpatr(String chpatr) {
        this.chpatr = chpatr;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (childId != null ? childId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ChildrenView)) {
            return false;
        }
        ChildrenView other = (ChildrenView) object;
        if ((this.childId == null && other.childId != null) || (this.childId != null && !this.childId.equals(other.childId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.ChildrenView[ childId=" + childId + " ]";
    }
    
}
