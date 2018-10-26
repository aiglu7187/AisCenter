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
@Table(name = "KEY_SPR_OBR_VID")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "KeySprObrVid.findAll", query = "SELECT k FROM KeySprObrVid k"),
    @NamedQuery(name = "KeySprObrVid.findByKeysprobrvidId", query = "SELECT k FROM KeySprObrVid k WHERE k.keysprobrvidId = :keysprobrvidId"),
    @NamedQuery(name = "KeySprObrVid.findByKeysprobrvidKeys", query = "SELECT k FROM KeySprObrVid k WHERE k.keysprobrvidKeys = :keysprobrvidKeys")})
public class KeySprObrVid implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "KEYSPROBRVID_ID")
    private Integer keysprobrvidId;
    @Size(max = 255)
    @Column(name = "KEYSPROBRVID_KEYS")
    private String keysprobrvidKeys;
    @JoinColumn(name = "SPROBRVID_ID", referencedColumnName = "SPROBRVID_ID")
    @ManyToOne
    private SprObrVid sprobrvidId;

    public KeySprObrVid() {
    }

    public KeySprObrVid(Integer keysprobrvidId) {
        this.keysprobrvidId = keysprobrvidId;
    }

    public Integer getKeysprobrvidId() {
        return keysprobrvidId;
    }

    public void setKeysprobrvidId(Integer keysprobrvidId) {
        this.keysprobrvidId = keysprobrvidId;
    }

    public String getKeysprobrvidKeys() {
        return keysprobrvidKeys;
    }

    public void setKeysprobrvidKeys(String keysprobrvidKeys) {
        this.keysprobrvidKeys = keysprobrvidKeys;
    }

    public SprObrVid getSprobrvidId() {
        return sprobrvidId;
    }

    public void setSprobrvidId(SprObrVid sprobrvidId) {
        this.sprobrvidId = sprobrvidId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (keysprobrvidId != null ? keysprobrvidId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KeySprObrVid)) {
            return false;
        }
        KeySprObrVid other = (KeySprObrVid) object;
        if ((this.keysprobrvidId == null && other.keysprobrvidId != null) || (this.keysprobrvidId != null && !this.keysprobrvidId.equals(other.keysprobrvidId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.KeySprObrVid[ keysprobrvidId=" + keysprobrvidId + " ]";
    }
    
}
