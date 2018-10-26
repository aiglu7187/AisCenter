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
@Table(name = "KEY_SPR_OU_TYPE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "KeySprOuType.findAll", query = "SELECT k FROM KeySprOuType k"),
    @NamedQuery(name = "KeySprOuType.findByKeysproutypeId", query = "SELECT k FROM KeySprOuType k WHERE k.keysproutypeId = :keysproutypeId"),
    @NamedQuery(name = "KeySprOuType.findByKeysproutypesKeys", query = "SELECT k FROM KeySprOuType k WHERE k.keysproutypesKeys = :keysproutypesKeys")})
public class KeySprOuType implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "KEYSPROUTYPE_ID")
    private Integer keysproutypeId;
    @Size(max = 255)
    @Column(name = "KEYSPROUTYPES_KEYS")
    private String keysproutypesKeys;
    @JoinColumn(name = "SPROUTYPE_ID", referencedColumnName = "SPROUTYPE_ID")
    @ManyToOne
    private SprOuType sproutypeId;

    public KeySprOuType() {
    }

    public KeySprOuType(Integer keysproutypeId) {
        this.keysproutypeId = keysproutypeId;
    }

    public Integer getKeysproutypeId() {
        return keysproutypeId;
    }

    public void setKeysproutypeId(Integer keysproutypeId) {
        this.keysproutypeId = keysproutypeId;
    }

    public String getKeysproutypesKeys() {
        return keysproutypesKeys;
    }

    public void setKeysproutypesKeys(String keysproutypesKeys) {
        this.keysproutypesKeys = keysproutypesKeys;
    }

    public SprOuType getSproutypeId() {
        return sproutypeId;
    }

    public void setSproutypeId(SprOuType sproutypeId) {
        this.sproutypeId = sproutypeId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (keysproutypeId != null ? keysproutypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KeySprOuType)) {
            return false;
        }
        KeySprOuType other = (KeySprOuType) object;
        if ((this.keysproutypeId == null && other.keysproutypeId != null) || (this.keysproutypeId != null && !this.keysproutypeId.equals(other.keysproutypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.KeySprOuType[ keysproutypeId=" + keysproutypeId + " ]";
    }
    
}
