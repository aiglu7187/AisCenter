/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Aiglu
 */
@Entity
@Table(name = "SPR_PROBLEM_TYPE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SprProblemType.findAll", query = "SELECT s FROM SprProblemType s ORDER BY s.sprproblemtypeKod"),
    @NamedQuery(name = "SprProblemType.findBySprproblemtypeId", query = "SELECT s FROM SprProblemType s WHERE s.sprproblemtypeId = :sprproblemtypeId"),
    @NamedQuery(name = "SprProblemType.findBySprproblemtypeKod", query = "SELECT s FROM SprProblemType s WHERE s.sprproblemtypeKod = :sprproblemtypeKod"),
    @NamedQuery(name = "SprProblemType.findBySprproblemtypeName", query = "SELECT s FROM SprProblemType s WHERE s.sprproblemtypeName = :sprproblemtypeName")})
public class SprProblemType implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SPRPROBLEMTYPE_ID")
    private Integer sprproblemtypeId;
    @Column(name = "SPRPROBLEMTYPE_KOD")
    private Integer sprproblemtypeKod;
    @Size(max = 500)
    @Column(name = "SPRPROBLEMTYPE_NAME")
    private String sprproblemtypeName;
    @OneToMany(mappedBy = "sprproblemtypeId")
    private Collection<SprProblem> sprProblemCollection;

    public SprProblemType() {
    }

    public SprProblemType(Integer sprproblemtypeId) {
        this.sprproblemtypeId = sprproblemtypeId;
    }

    public Integer getSprproblemtypeId() {
        return sprproblemtypeId;
    }

    public void setSprproblemtypeId(Integer sprproblemtypeId) {
        this.sprproblemtypeId = sprproblemtypeId;
    }

    public Integer getSprproblemtypeKod() {
        return sprproblemtypeKod;
    }

    public void setSprproblemtypeKod(Integer sprproblemtypeKod) {
        this.sprproblemtypeKod = sprproblemtypeKod;
    }

    public String getSprproblemtypeName() {
        return sprproblemtypeName;
    }

    public void setSprproblemtypeName(String sprproblemtypeName) {
        this.sprproblemtypeName = sprproblemtypeName;
    }

    @XmlTransient
    public Collection<SprProblem> getSprProblemCollection() {
        return sprProblemCollection;
    }

    public void setSprProblemCollection(Collection<SprProblem> sprProblemCollection) {
        this.sprProblemCollection = sprProblemCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sprproblemtypeId != null ? sprproblemtypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SprProblemType)) {
            return false;
        }
        SprProblemType other = (SprProblemType) object;
        if ((this.sprproblemtypeId == null && other.sprproblemtypeId != null) || (this.sprproblemtypeId != null && !this.sprproblemtypeId.equals(other.sprproblemtypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.SprProblemType[ sprproblemtypeId=" + sprproblemtypeId + " ]";
    }
    
}
