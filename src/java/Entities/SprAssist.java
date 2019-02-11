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
@Table(name = "SPR_ASSIST")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SprAssist.findAll", query = "SELECT s FROM SprAssist s"),
    @NamedQuery(name = "SprAssist.findBySprassistId", query = "SELECT s FROM SprAssist s WHERE s.sprassistId = :sprassistId"),
    @NamedQuery(name = "SprAssist.findBySprassistName", query = "SELECT s FROM SprAssist s WHERE s.sprassistName = :sprassistName")})
public class SprAssist implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SPRASSIST_ID")
    private Integer sprassistId;
    @Size(max = 100)
    @Column(name = "SPRASSIST_NAME")
    private String sprassistName;

    public Integer getSprassistId() {
        return sprassistId;
    }

    public void setSprassistId(Integer sprassistId) {
        this.sprassistId = sprassistId;
    }
    
    public String getSprassistName() {
        return sprassistName;
    }

    public void setSprassistName(String sprassistName) {
        this.sprassistName = sprassistName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sprassistId != null ? sprassistId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SprAssist)) {
            return false;
        }
        SprAssist other = (SprAssist) object;
        if ((this.sprassistId == null && other.sprassistId != null) 
                || (this.sprassistId != null && !this.sprassistId.equals(other.sprassistId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.SprAssist[ sprassistId=" + sprassistId + " ]";
    }
    
}
