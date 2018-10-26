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
@Table(name = "SPR_VAR")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SprVar.findAll", query = "SELECT s FROM SprVar s"),
    @NamedQuery(name = "SprVar.findBySprvarId", query = "SELECT s FROM SprVar s WHERE s.sprvarId = :sprvarId"),
    @NamedQuery(name = "SprVar.findBySprvarName", query = "SELECT s FROM SprVar s WHERE s.sprvarName = :sprvarName")})
public class SprVar implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SPRVAR_ID")
    private Integer sprvarId;
    @Size(max = 100)
    @Column(name = "SPRVAR_NAME")
    private String sprvarName;    

    public SprVar() {
    }

    public SprVar(Integer sprvarId) {
        this.sprvarId = sprvarId;
    }

    public Integer getSprvarId() {
        return sprvarId;
    }

    public void setSprvarId(Integer sprvarId) {
        this.sprvarId = sprvarId;
    }

    public String getSprvarName() {
        return sprvarName;
    }

    public void setSprvarName(String sprvarName) {
        this.sprvarName = sprvarName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sprvarId != null ? sprvarId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SprVar)) {
            return false;
        }
        SprVar other = (SprVar) object;
        if ((this.sprvarId == null && other.sprvarId != null) || (this.sprvarId != null && !this.sprvarId.equals(other.sprvarId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.SprVar[ sprvarId=" + sprvarId + " ]";
    }

    
}
