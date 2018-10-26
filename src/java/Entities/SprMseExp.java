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
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
@Table(name = "SPR_MSE_EXP")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SprMseExp.findAll", query = "SELECT s FROM SprMseExp s ORDER BY s.sprmseexpName"),
    @NamedQuery(name = "SprMseExp.findBySprmseexpId", query = "SELECT s FROM SprMseExp s WHERE s.sprmseexpId = :sprmseexpId"),
    @NamedQuery(name = "SprMseExp.findBySprmseexpName", query = "SELECT s FROM SprMseExp s WHERE s.sprmseexpName = :sprmseexpName")})
public class SprMseExp implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SPRMSEEXP_ID")
    private Integer sprmseexpId;
    @Size(max = 100)
    @Column(name = "SPRMSEEXP_NAME")
    private String sprmseexpName;    

    public SprMseExp() {
    }

    public SprMseExp(Integer sprmseexpId) {
        this.sprmseexpId = sprmseexpId;
    }

    public Integer getSprmseexpId() {
        return sprmseexpId;
    }

    public void setSprmseexpId(Integer sprmseexpId) {
        this.sprmseexpId = sprmseexpId;
    }

    public String getSprmseexpName() {
        return sprmseexpName;
    }

    public void setSprmseexpName(String sprmseexpName) {
        this.sprmseexpName = sprmseexpName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sprmseexpId != null ? sprmseexpId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SprMseExp)) {
            return false;
        }
        SprMseExp other = (SprMseExp) object;
        if ((this.sprmseexpId == null && other.sprmseexpId != null) || (this.sprmseexpId != null && !this.sprmseexpId.equals(other.sprmseexpId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.SprMseExp[ sprmseexpId=" + sprmseexpId + " ]";
    }
    
}
