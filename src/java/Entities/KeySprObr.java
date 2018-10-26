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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Aiglu
 */
@Entity
@Table(name = "KEY_SPR_OBR")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "KeySprObr.findAll", query = "SELECT k FROM KeySprObr k"),
    @NamedQuery(name = "KeySprObr.findByKeysprobrId", query = "SELECT k FROM KeySprObr k WHERE k.keysprobrId = :keysprobrId"),
    @NamedQuery(name = "KeySprObr.findByKeysprobrKeys", query = "SELECT k FROM KeySprObr k WHERE k.keysprobrKeys = :keysprobrKeys")})
public class KeySprObr implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "KEYSPROBR_ID")
    private Integer keysprobrId;
    @Size(max = 255)
    @Column(name = "KEYSPROBR_KEYS")
    private String keysprobrKeys;
    @JoinColumn(name = "SPROBR_ID", referencedColumnName = "SPROBR_ID")
    @ManyToOne
    private SprObr sprobrId;

    public KeySprObr() {
    }

    public KeySprObr(Integer keysprobrId) {
        this.keysprobrId = keysprobrId;
    }

    public Integer getKeysprobrId() {
        return keysprobrId;
    }

    public void setKeysprobrId(Integer keysprobrId) {
        this.keysprobrId = keysprobrId;
    }

    public String getKeysprobrKeys() {
        return keysprobrKeys;
    }

    public void setKeysprobrKeys(String keysprobrKeys) {
        this.keysprobrKeys = keysprobrKeys;
    }

    public SprObr getSprobrId() {
        return sprobrId;
    }

    public void setSprobrId(SprObr sprobrId) {
        this.sprobrId = sprobrId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (keysprobrId != null ? keysprobrId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KeySprObr)) {
            return false;
        }
        KeySprObr other = (KeySprObr) object;
        if ((this.keysprobrId == null && other.keysprobrId != null) || (this.keysprobrId != null && !this.keysprobrId.equals(other.keysprobrId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.KeySprObr[ keysprobrId=" + keysprobrId + " ]";
    }
    
}
