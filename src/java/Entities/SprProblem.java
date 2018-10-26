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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "SPR_PROBLEM")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SprProblem.findAll", query = "SELECT s FROM SprProblem s"),
    @NamedQuery(name = "SprProblem.findBySprproblemId", query = "SELECT s FROM SprProblem s WHERE s.sprproblemId = :sprproblemId"),
    @NamedQuery(name = "SprProblem.findBySprproblemKod", query = "SELECT s FROM SprProblem s WHERE s.sprproblemKod = :sprproblemKod"),
    @NamedQuery(name = "SprProblem.findBySprproblemName", query = "SELECT s FROM SprProblem s WHERE s.sprproblemName = :sprproblemName"),
    @NamedQuery(name = "SprProblem.findBySprproblemtypeId", query = "SELECT s FROM SprProblem s WHERE s.sprproblemtypeId = :sprproblemtypeId ORDER BY s.sprproblemKod")})
public class SprProblem implements Serializable {

    @OneToMany(mappedBy = "sprproblemId")
    private Collection<PriyomProblem> priyomProblemCollection;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SPRPROBLEM_ID")
    private Integer sprproblemId;
    @Column(name = "SPRPROBLEM_KOD")
    private Integer sprproblemKod;
    @Size(max = 500)
    @Column(name = "SPRPROBLEM_NAME")
    private String sprproblemName;
    @JoinColumn(name = "SPRPROBLEMTYPE_ID", referencedColumnName = "SPRPROBLEMTYPE_ID")
    @ManyToOne
    private SprProblemType sprproblemtypeId;

    public SprProblem() {
    }

    public SprProblem(Integer sprproblemId) {
        this.sprproblemId = sprproblemId;
    }

    public Integer getSprproblemId() {
        return sprproblemId;
    }

    public void setSprproblemId(Integer sprproblemId) {
        this.sprproblemId = sprproblemId;
    }

    public Integer getSprproblemKod() {
        return sprproblemKod;
    }

    public void setSprproblemKod(Integer sprproblemKod) {
        this.sprproblemKod = sprproblemKod;
    }

    public String getSprproblemName() {
        return sprproblemName;
    }

    public void setSprproblemName(String sprproblemName) {
        this.sprproblemName = sprproblemName;
    }

    public SprProblemType getSprproblemtypeId() {
        return sprproblemtypeId;
    }

    public void setSprproblemtypeId(SprProblemType sprproblemtypeId) {
        this.sprproblemtypeId = sprproblemtypeId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sprproblemId != null ? sprproblemId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SprProblem)) {
            return false;
        }
        SprProblem other = (SprProblem) object;
        if ((this.sprproblemId == null && other.sprproblemId != null) || (this.sprproblemId != null && !this.sprproblemId.equals(other.sprproblemId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.SprProblem[ sprproblemId=" + sprproblemId + " ]";
    }

    @XmlTransient
    public Collection<PriyomProblem> getPriyomProblemCollection() {
        return priyomProblemCollection;
    }

    public void setPriyomProblemCollection(Collection<PriyomProblem> priyomProblemCollection) {
        this.priyomProblemCollection = priyomProblemCollection;
    }
    
}
