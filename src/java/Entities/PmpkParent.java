/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Aiglu
 */
@Entity
@Table(name = "PMPK_PARENT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PmpkParent.findAll", query = "SELECT p FROM PmpkParent p"),
    @NamedQuery(name = "PmpkParent.findByPmpkparentId", query = "SELECT p FROM PmpkParent p WHERE p.pmpkparentId = :pmpkparentId"),
    @NamedQuery(name = "PmpkParent.findByParentId", query = "SELECT p FROM PmpkParent p WHERE p.parentId = :parentId"),
    @NamedQuery(name = "PmpkParent.findByPmpkId", query = "SELECT p FROM PmpkParent p WHERE p.pmpkId = :pmpkId")})
public class PmpkParent implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "PMPKPARENT_ID")
    @SequenceGenerator(name = "SEQ_PMPK_PARENT", sequenceName = "SEQ_PMPK_PARENT", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.IDENTITY, generator="SEQ_PMPK_PARENT")
    private Integer pmpkparentId;
    @JoinColumn(name = "PARENT_ID", referencedColumnName = "PARENT_ID")
    @ManyToOne
    private Parents parentId;
    @JoinColumn(name = "PMPK_ID", referencedColumnName = "PMPK_ID")
    @ManyToOne
    private Pmpk pmpkId;
    @JoinColumn(name = "SPRPARENTTYPE_ID", referencedColumnName = "SPRPARENTTYPE_ID")
    @ManyToOne
    private SprParentType sprparenttypeId;

    public PmpkParent() {
    }

    public PmpkParent(Integer pmpkparentId) {
        this.pmpkparentId = pmpkparentId;
    }

    public Integer getPmpkparentId() {
        return pmpkparentId;
    }

    public void setPmpkparentId(Integer pmpkparentId) {
        this.pmpkparentId = pmpkparentId;
    }

    public Parents getParentId() {
        return parentId;
    }

    public void setParentId(Parents parentId) {
        this.parentId = parentId;
    }

    public Pmpk getPmpkId() {
        return pmpkId;
    }

    public void setPmpkId(Pmpk pmpkId) {
        this.pmpkId = pmpkId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pmpkparentId != null ? pmpkparentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PmpkParent)) {
            return false;
        }
        PmpkParent other = (PmpkParent) object;
        if ((this.pmpkparentId == null && other.pmpkparentId != null) || (this.pmpkparentId != null && !this.pmpkparentId.equals(other.pmpkparentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.PmpkParent[ pmpkparentId=" + pmpkparentId + " ]";
    }
    
    public SprParentType getSprparenttypeId() {
        return sprparenttypeId;
    }

    public void setSprparenttypeId(SprParentType sprparenttypeId) {
        this.sprparenttypeId = sprparenttypeId;
    }
    
}
