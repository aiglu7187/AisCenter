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
@Table(name = "SPR_REASON")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SprReason.findAll", query = "SELECT s FROM SprReason s ORDER BY s.sprreasonOth, s.sprreasonId"),
    @NamedQuery(name = "SprReason.findBySprreasonId", query = "SELECT s FROM SprReason s WHERE s.sprreasonId = :sprreasonId"),
    @NamedQuery(name = "SprReason.findBySprreasonName", query = "SELECT s FROM SprReason s WHERE s.sprreasonName = :sprreasonName"),
    @NamedQuery(name = "SprReason.findBySprreasonOth", query = "SELECT s FROM SprReason s WHERE s.sprreasonOth = :sprreasonOth")})
public class SprReason implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SPRREASON_ID")
    private Integer sprreasonId;
    @Size(max = 500)
    @Column(name = "SPRREASON_NAME")
    private String sprreasonName;    
    @Column(name = "SPRREASON_OTH")
    private Integer sprreasonOth;

    public Integer getSprreasonId() {
        return sprreasonId;
    }

    public void setSprreasonId(Integer sprreasonId) {
        this.sprreasonId = sprreasonId;
    }
    
    public String getSprreasonName() {
        return sprreasonName;
    }

    public void setSprreasonName(String sprreasonName) {
        this.sprreasonName = sprreasonName;
    }
    
    public Integer getSprreasonOth() {
        return sprreasonOth;
    }

    public void setSprreasonOth(Integer sprreasonOth) {
        this.sprreasonOth = sprreasonOth;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sprreasonId != null ? sprreasonId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SprReason)) {
            return false;
        }
        SprReason other = (SprReason) object;
        if ((this.sprreasonId == null && other.sprreasonId != null) || 
                (this.sprreasonId != null && !this.sprreasonId.equals(other.sprreasonId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.SprReason[ id=" + sprreasonId + " ]";
    }
    
}
