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
@Table(name = "SPR_PARENT_TYPE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SprParentType.findAll", query = "SELECT s FROM SprParentType s ORDER BY s.sprparenttypeId"),
    @NamedQuery(name = "SprParentType.findBySprparenttypeId", query = "SELECT s FROM SprParentType s WHERE s.sprparenttypeId = :sprparenttypeId")})
public class SprParentType implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SPRPARENTTYPE_ID")
    @SequenceGenerator(name = "SEQ_SPR_PARENT_TYPE", sequenceName = "SEQ_SPR_PARENT_TYPE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SEQ_SPR_PARENT_TYPE")
    private Integer sprparenttypeId;
    @Size(max = 50)
    @Column(name = "SPRPARENTTYPE_NAME")
    private String sprparenttypeName;
    
    public SprParentType() {
    }

    public SprParentType(Integer sprparenttypeId) {
        this.sprparenttypeId = sprparenttypeId;
    }

    public Integer getSprparenttypeId() {
        return sprparenttypeId;
    }

    public void setSprparenttypeId(Integer sprparenttypeId) {
        this.sprparenttypeId = sprparenttypeId;
    }

    public String getSprparenttypeName() {
        return sprparenttypeName;
    }

    public void setSprparenttypeName(String sprparenttypeName) {
        this.sprparenttypeName = sprparenttypeName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sprparenttypeId != null ? sprparenttypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SprParentType)) {
            return false;
        }
        SprParentType other = (SprParentType) object;
        if ((this.sprparenttypeId == null && other.sprparenttypeId != null) || (this.sprparenttypeId != null && !this.sprparenttypeId.equals(other.sprparenttypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.SprParentType[ sprparenttypeId=" + sprparenttypeId + " ]";
    }

}
