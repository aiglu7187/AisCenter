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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Aiglu
 */
@Entity
@Table(name = "SPR_VH_TYPE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SprVhType.findAll", query = "SELECT s FROM SprVhType s"),
    @NamedQuery(name = "SprVhType.findBySprvhtypeId", query = "SELECT s FROM SprVhType s WHERE s.sprvhtypeId = :sprvhtypeId")})
public class SprVhType implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SPRVHTYPE_ID")
    @SequenceGenerator(name = "SEQ_SPR_VH_TYPE", sequenceName = "SEQ_SPR_VH_TYPE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SEQ_SPR_VH_TYPE")
    private Integer sprvhtypeId;
    @Size(max = 100)
    @Column(name = "SPRVHTYPE_NAME")
    private String sprvhtypeName;

    public Integer getSprvhtypeId() {
        return sprvhtypeId;
    }

    public void setSprvhtypeId(Integer sprvhtypeId) {
        this.sprvhtypeId = sprvhtypeId;
    }
    
    public String getSprvhtypeName() {
        return sprvhtypeName;
    }

    public void setSprvhtypeName(String sprvhtypeName) {
        this.sprvhtypeName = sprvhtypeName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sprvhtypeId != null ? sprvhtypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SprVhType)) {
            return false;
        }
        SprVhType other = (SprVhType) object;
        if ((this.sprvhtypeId == null && other.sprvhtypeId != null) || (this.sprvhtypeId != null && !this.sprvhtypeId.equals(other.sprvhtypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.SprVhType[ sprvhtypeId=" + sprvhtypeId + " ]";
    }
    
}
