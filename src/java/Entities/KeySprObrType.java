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
@Table(name = "KEY_SPR_OBR_TYPE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "KeySprObrType.findAll", query = "SELECT k FROM KeySprObrType k"),
    @NamedQuery(name = "KeySprObrType.findByKeysprobrtypeId", query = "SELECT k FROM KeySprObrType k WHERE k.keysprobrtypeId = :keysprobrtypeId"),
    @NamedQuery(name = "KeySprObrType.findByKeysprobrtypeKeys", query = "SELECT k FROM KeySprObrType k WHERE k.keysprobrtypeKeys = :keysprobrtypeKeys")})
public class KeySprObrType implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "KEYSPROBRTYPE_ID")
    private Integer keysprobrtypeId;
    @Size(max = 255)
    @Column(name = "KEYSPROBRTYPE_KEYS")
    private String keysprobrtypeKeys;
    @JoinColumn(name = "SPROBRTYPE_ID", referencedColumnName = "SPROBRTYPE_ID")
    @ManyToOne
    private SprObrType sprobrtypeId;

    public KeySprObrType() {
    }

    public KeySprObrType(Integer keysprobrtypeId) {
        this.keysprobrtypeId = keysprobrtypeId;
    }

    public Integer getKeysprobrtypeId() {
        return keysprobrtypeId;
    }

    public void setKeysprobrtypeId(Integer keysprobrtypeId) {
        this.keysprobrtypeId = keysprobrtypeId;
    }

    public String getKeysprobrtypeKeys() {
        return keysprobrtypeKeys;
    }

    public void setKeysprobrtypeKeys(String keysprobrtypeKeys) {
        this.keysprobrtypeKeys = keysprobrtypeKeys;
    }

    public SprObrType getSprobrtypeId() {
        return sprobrtypeId;
    }

    public void setSprobrtypeId(SprObrType sprobrtypeId) {
        this.sprobrtypeId = sprobrtypeId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (keysprobrtypeId != null ? keysprobrtypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KeySprObrType)) {
            return false;
        }
        KeySprObrType other = (KeySprObrType) object;
        if ((this.keysprobrtypeId == null && other.keysprobrtypeId != null) || (this.keysprobrtypeId != null && !this.keysprobrtypeId.equals(other.keysprobrtypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.KeySprObrType[ keysprobrtypeId=" + keysprobrtypeId + " ]";
    }
    
}
