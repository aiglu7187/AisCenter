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
@Table(name = "CHILDREN_EDUCOND_EQ")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ChildrenEducondEq.findAll", query = "SELECT c FROM ChildrenEducondEq c"),
    @NamedQuery(name = "ChildrenEducondEq.findByChildreneducondeqId", query = "SELECT c FROM ChildrenEducondEq c WHERE c.childreneducondeqId = :childreneducondeqId"),
    @NamedQuery(name = "ChildrenEducondEq.findByChildreneducondId", query = "SELECT c FROM ChildrenEducondEq c WHERE c.childreneducondId = :childreneducondId")})
public class ChildrenEducondEq implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "CHILDRENEDUCONDEQ_ID")
    @SequenceGenerator(name = "SEQ_CHILDREN_EDUCOND_EQ", sequenceName = "SEQ_CHILDREN_EDUCOND_EQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SEQ_CHILDREN_EDUCOND_EQ")
    private Integer childreneducondeqId;
    @JoinColumn(name = "CHILDRENEDUCOND_ID", referencedColumnName = "CHILDRENEDUCOND_ID")
    @ManyToOne
    private ChildrenEducond childreneducondId;
    @JoinColumn(name = "SPREQUIP_ID", referencedColumnName = "SPREQUIP_ID")
    @ManyToOne
    private SprEquip sprequipId;

    public ChildrenEducondEq() {
    }

    public ChildrenEducondEq(Integer childreneducondeqId) {
        this.childreneducondeqId = childreneducondeqId;
    }

    public Integer getChildreneducondeqId() {
        return childreneducondeqId;
    }

    public void setChildreneducondeqId(Integer childreneducondeqId) {
        this.childreneducondeqId = childreneducondeqId;
    }

    public ChildrenEducond getChildreneducondId() {
        return childreneducondId;
    }

    public void setChildreneducondId(ChildrenEducond childreneducondId) {
        this.childreneducondId = childreneducondId;
    }

    public SprEquip getSprequipId() {
        return sprequipId;
    }

    public void setSprequipId(SprEquip sprequipId) {
        this.sprequipId = sprequipId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (childreneducondeqId != null ? childreneducondeqId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ChildrenEducondEq)) {
            return false;
        }
        ChildrenEducondEq other = (ChildrenEducondEq) object;
        if ((this.childreneducondeqId == null && other.childreneducondeqId != null) || (this.childreneducondeqId != null && !this.childreneducondeqId.equals(other.childreneducondeqId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.ChildrenEducondEq[ childreneducondeqId=" + childreneducondeqId + " ]";
    }

}
