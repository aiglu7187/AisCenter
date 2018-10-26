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
@Table(name = "KEY_SPR_OBR_SPEC")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "KeySprObrSpec.findAll", query = "SELECT k FROM KeySprObrSpec k"),
    @NamedQuery(name = "KeySprObrSpec.findByKeysprobrspecId", query = "SELECT k FROM KeySprObrSpec k WHERE k.keysprobrspecId = :keysprobrspecId"),
    @NamedQuery(name = "KeySprObrSpec.findByKeysprobrspecKeys", query = "SELECT k FROM KeySprObrSpec k WHERE k.keysprobrspecKeys = :keysprobrspecKeys")})
public class KeySprObrSpec implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "KEYSPROBRSPEC_ID")
    private Integer keysprobrspecId;
    @Size(max = 255)
    @Column(name = "KEYSPROBRSPEC_KEYS")
    private String keysprobrspecKeys;
    @JoinColumn(name = "SPROBRSPEC_ID", referencedColumnName = "SPROBRSPEC_ID")
    @ManyToOne
    private SprObrSpec sprobrspecId;

    public KeySprObrSpec() {
    }

    public KeySprObrSpec(Integer keysprobrspecId) {
        this.keysprobrspecId = keysprobrspecId;
    }

    public Integer getKeysprobrspecId() {
        return keysprobrspecId;
    }

    public void setKeysprobrspecId(Integer keysprobrspecId) {
        this.keysprobrspecId = keysprobrspecId;
    }

    public String getKeysprobrspecKeys() {
        return keysprobrspecKeys;
    }

    public void setKeysprobrspecKeys(String keysprobrspecKeys) {
        this.keysprobrspecKeys = keysprobrspecKeys;
    }

    public SprObrSpec getSprobrspecId() {
        return sprobrspecId;
    }

    public void setSprobrspecId(SprObrSpec sprobrspecId) {
        this.sprobrspecId = sprobrspecId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (keysprobrspecId != null ? keysprobrspecId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KeySprObrSpec)) {
            return false;
        }
        KeySprObrSpec other = (KeySprObrSpec) object;
        if ((this.keysprobrspecId == null && other.keysprobrspecId != null) || (this.keysprobrspecId != null && !this.keysprobrspecId.equals(other.keysprobrspecId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.KeySprObrSpec[ keysprobrspecId=" + keysprobrspecId + " ]";
    }
    
}
