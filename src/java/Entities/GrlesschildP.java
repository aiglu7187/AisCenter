/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Aiglu
 */
@Entity
@Table(name = "GRLESSCHILD_P")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GrlesschildP.findAll", query = "SELECT g FROM GrlesschildP g"),
    @NamedQuery(name = "GrlesschildP.findByGrlesschildpId", query = "SELECT g FROM GrlesschildP g WHERE g.grlesschildpId = :grlesschildpId")})
public class GrlesschildP implements Serializable {

    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
    @ManyToOne
    private Users userId;

    @Column(name = "DATE_UPD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateUpd;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "GRLESSCHILDP_ID")
    @SequenceGenerator(name = "SEQ_GRLESSCHILD_P", sequenceName = "SEQ_GRLESSCHILD_P", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.IDENTITY, generator="SEQ_GRLESSCHILD_P")
    private Integer grlesschildpId;
    @JoinColumn(name = "CHILD_ID", referencedColumnName = "CHILD_ID")
    @ManyToOne
    private Children childId;
    @JoinColumn(name = "GRLESS_ID", referencedColumnName = "GRLESS_ID")
    @ManyToOne
    private GroupLesson grlessId;
    @JoinColumn(name = "SPRPOS_ID", referencedColumnName = "SPRPOS_ID")
    @ManyToOne
    private SprPos sprposId;

    public GrlesschildP() {
    }

    public GrlesschildP(Integer grlesschildpId) {
        this.grlesschildpId = grlesschildpId;
    }

    public Integer getGrlesschildpId() {
        return grlesschildpId;
    }

    public void setGrlesschildpId(Integer grlesschildpId) {
        this.grlesschildpId = grlesschildpId;
    }

    public Children getChildId() {
        return childId;
    }

    public void setChildId(Children childId) {
        this.childId = childId;
    }

    public GroupLesson getGrlessId() {
        return grlessId;
    }

    public void setGrlessId(GroupLesson grlessId) {
        this.grlessId = grlessId;
    }

    public SprPos getSprposId() {
        return sprposId;
    }

    public void setSprposId(SprPos sprposId) {
        this.sprposId = sprposId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (grlesschildpId != null ? grlesschildpId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GrlesschildP)) {
            return false;
        }
        GrlesschildP other = (GrlesschildP) object;
        if ((this.grlesschildpId == null && other.grlesschildpId != null) || (this.grlesschildpId != null && !this.grlesschildpId.equals(other.grlesschildpId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.GrlesschildP[ grlesschildpId=" + grlesschildpId + " ]";
    }

    public Date getDateUpd() {
        return dateUpd;
    }

    public void setDateUpd(Date dateUpd) {
        this.dateUpd = dateUpd;
    }

    public Users getUserId() {
        return userId;
    }

    public void setUserId(Users userId) {
        this.userId = userId;
    }
    
}
