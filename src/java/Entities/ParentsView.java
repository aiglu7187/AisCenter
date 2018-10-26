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
@Table(name = "PARENTS_VIEW")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ParentsView.findAll", query = "SELECT p FROM ParentsView p"),
    @NamedQuery(name = "ParentsView.findByParentId", query = "SELECT p FROM ParentsView p WHERE p.parentId = :parentId"),
    @NamedQuery(name = "ParentsView.findByParfam", query = "SELECT p FROM ParentsView p WHERE p.parfam = :parfam"),
    @NamedQuery(name = "ParentsView.findByParname", query = "SELECT p FROM ParentsView p WHERE p.parname = :parname"),
    @NamedQuery(name = "ParentsView.findByParpatr", query = "SELECT p FROM ParentsView p WHERE p.parpatr = :parpatr"),
    @NamedQuery(name = "ParentsView.findByParentFIO", query = "SELECT p FROM ParentsView p WHERE (p.parfam like :fam) "
            + "AND (p.parname like :name) AND ((p.parpatr like :patr) OR (p.parpatr IS NULL)) ORDER BY p.parfam,p.parname,p.parpatr")})
public class ParentsView implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "PARENT_ID")
    private Integer parentId;
    @Size(max = 100)
    @Column(name = "PARFAM")
    private String parfam;
    @Size(max = 100)
    @Column(name = "PARNAME")
    private String parname;
    @Size(max = 100)
    @Column(name = "PARPATR")
    private String parpatr;

    public ParentsView() {
    }

    public ParentsView(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getParfam() {
        return parfam;
    }

    public void setParfam(String parfam) {
        this.parfam = parfam;
    }

    public String getParname() {
        return parname;
    }

    public void setParname(String parname) {
        this.parname = parname;
    }

    public String getParpatr() {
        return parpatr;
    }

    public void setParpatr(String parpatr) {
        this.parpatr = parpatr;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (parentId != null ? parentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ParentsView)) {
            return false;
        }
        ParentsView other = (ParentsView) object;
        if ((this.parentId == null && other.parentId != null) || (this.parentId != null && !this.parentId.equals(other.parentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.ParentsView[ parentId=" + parentId + " ]";
    }
    
}
