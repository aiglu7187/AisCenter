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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Aiglu
 */
@Entity
@Table(name = "USERS_REGION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UsersRegion.findAll", query = "SELECT u FROM UsersRegion u"),
    @NamedQuery(name = "UsersRegion.findByUsregId", query = "SELECT u FROM UsersRegion u WHERE u.usregId = :usregId"),
    @NamedQuery(name = "UsersRegion.findBySprregId", query = "SELECT u.userId FROM UsersRegion u WHERE u.sprregId = :sprregId")})
public class UsersRegion implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "USREG_ID")
    private Integer usregId;
    @JoinColumn(name = "SPRREG_ID", referencedColumnName = "SPRREG_ID")
    @ManyToOne
    private SprRegion sprregId;
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
    @ManyToOne
    private Users userId;

    public UsersRegion() {
    }

    public UsersRegion(Integer usregId) {
        this.usregId = usregId;
    }

    public Integer getUsregId() {
        return usregId;
    }

    public void setUsregId(Integer usregId) {
        this.usregId = usregId;
    }

    public SprRegion getSprregId() {
        return sprregId;
    }

    public void setSprregId(SprRegion sprregId) {
        this.sprregId = sprregId;
    }

    public Users getUserId() {
        return userId;
    }

    public void setUserId(Users userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usregId != null ? usregId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsersRegion)) {
            return false;
        }
        UsersRegion other = (UsersRegion) object;
        if ((this.usregId == null && other.usregId != null) || (this.usregId != null && !this.usregId.equals(other.usregId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.UsersRegion[ usregId=" + usregId + " ]";
    }
    
}
