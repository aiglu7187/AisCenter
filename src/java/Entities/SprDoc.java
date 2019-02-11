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
@Table(name = "SPR_DOC")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SprDoc.findAll", query = "SELECT s FROM SprDoc s"),
    @NamedQuery(name = "SprDoc.findBySprdocId", query = "SELECT s FROM SprDoc s WHERE s.sprdocId = :sprdocId"),
    @NamedQuery(name = "SprDoc.findBySprdocName", query = "SELECT s FROM SprDoc s WHERE s.sprdocName = :sprdocName"),
    @NamedQuery(name = "SprDoc.findBySprdocMain", query = "SELECT s FROM SprDoc s WHERE s.sprdocMain = :sprdocMain"),
    @NamedQuery(name = "SprDoc.findBySprdocOth", query = "SELECT s FROM SprDoc s WHERE s.sprdocOth = :sprdocOth")})
public class SprDoc implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SPRDOC_ID")
    private Integer sprdocId;
    @Size(max = 500)
    @Column(name = "SPRDOC_NAME")
    private String sprdocName;
    @Column(name = "SPRDOC_MAIN")
    private Integer sprdocMain;
    @Column(name = "SPRDOC_OTH")
    private Integer sprdocOth;

    public Integer getSprdocId() {
        return sprdocId;
    }

    public void setSprdocId(Integer sprdocId) {
        this.sprdocId = sprdocId;
    }
    
    public Integer getSprdocMain() {
        return sprdocMain;
    }

    public void setSprdocMain(Integer sprdocMain) {
        this.sprdocMain = sprdocMain;
    }
    
    public Integer getSprdocOth() {
        return sprdocOth;
    }

    public void setSprdocOth(Integer sprdocOth) {
        this.sprdocOth = sprdocOth;
    }
    
    public String getSprdocName() {
        return sprdocName;
    }

    public void setSprdocName(String sprdocName) {
        this.sprdocName = sprdocName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sprdocId != null ? sprdocId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SprDoc)) {
            return false;
        }
        SprDoc other = (SprDoc) object;
        if ((this.sprdocId == null && other.sprdocId != null)
                || (this.sprdocId != null && !this.sprdocId.equals(other.sprdocId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.SprDoc[ id=" + sprdocId + " ]";
    }

}
