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
@Table(name = "SPR_ISH_TYPE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SprIshType.findAll", query = "SELECT s FROM SprIshType s"),
    @NamedQuery(name = "SprIshType.findBySprishtypeId", query = "SELECT s FROM SprIshType s WHERE s.sprishtypeId = :sprishtypeId")})
public class SprIshType implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SPRISHTYPE_ID")
    @SequenceGenerator(name = "SEQ_SPR_ISH_TYPE", sequenceName = "SEQ_SPR_ISH_TYPE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SEQ_SPR_ISH_TYPE")
    private Integer sprishtypeId;
    @Size(max = 100)
    @Column(name = "SPRISHTYPE_NAME")
    private String sprishtypeName;
    @Size(max = 200)
    @Column(name = "SPRISHTYPE_RU_NAME")
    private String sprishtypeRuName;

    public Integer getSprishtypeId() {
        return sprishtypeId;
    }

    public void setSprishtypeId(Integer sprishtypeId) {
        this.sprishtypeId = sprishtypeId;
    }
    
    public String getSprishtypeName() {
        return sprishtypeName;
    }

    public void setSprishtypeName(String sprishtypeName) {
        this.sprishtypeName = sprishtypeName;
    }
    
    public String getSprishtypeRuName() {
        return sprishtypeRuName;
    }

    public void setSprishtypeRuName(String sprishtypeRuName) {
        this.sprishtypeRuName = sprishtypeRuName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sprishtypeId != null ? sprishtypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SprIshType)) {
            return false;
        }
        SprIshType other = (SprIshType) object;
        if ((this.sprishtypeId == null && other.sprishtypeId != null) || (this.sprishtypeId != null && !this.sprishtypeId.equals(other.sprishtypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.SprIshType[ sprishtypeId=" + sprishtypeId + " ]";
    }
    
}
