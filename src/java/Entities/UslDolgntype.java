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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Aiglu
 */
@Entity
@Table(name = "USL_DOLGNTYPE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UslDolgntype.findAll", query = "SELECT u FROM UslDolgntype u"),
    @NamedQuery(name = "UslDolgntype.findByUsldolgntypeId", query = "SELECT u FROM UslDolgntype u WHERE u.usldolgntypeId = :usldolgntypeId"),
    @NamedQuery(name = "UslDolgntype.findBySpruslId", query = "SELECT u FROM UslDolgntype u WHERE u.spruslId = :spruslId")})

public class UslDolgntype implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "USLDOLGNTYPE_ID")
    private Integer usldolgntypeId;
    @JoinColumn(name = "SPRDOLGNTYPE_ID", referencedColumnName = "SPRDOLGNTYPE_ID")
    @ManyToOne
    private SprDolgnType sprdolgntypeId;
    @JoinColumn(name = "SPRUSL_ID", referencedColumnName = "SPRUSL_ID")
    @ManyToOne
    private SprUsl spruslId;

    public UslDolgntype() {
    }

    public UslDolgntype(Integer usldolgntypeId) {
        this.usldolgntypeId = usldolgntypeId;
    }

    public Integer getUsldolgntypeId() {
        return usldolgntypeId;
    }

    public void setUsldolgntypeId(Integer usldolgntypeId) {
        this.usldolgntypeId = usldolgntypeId;
    }

    public SprDolgnType getSprdolgntypeId() {
        return sprdolgntypeId;
    }

    public void setSprdolgntypeId(SprDolgnType sprdolgntypeId) {
        this.sprdolgntypeId = sprdolgntypeId;
    }
    
    public SprUsl getSpruslId() {
        return spruslId;
    }

    public void setSpruslId(SprUsl spruslId) {
        this.spruslId = spruslId;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usldolgntypeId != null ? usldolgntypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UslDolgntype)) {
            return false;
        }
        UslDolgntype other = (UslDolgntype) object;
        if ((this.usldolgntypeId == null && other.usldolgntypeId != null) || (this.usldolgntypeId != null && !this.usldolgntypeId.equals(other.usldolgntypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.UslDolgntype[ usldolgntypeId=" + usldolgntypeId + " ]";
    }
    
}
