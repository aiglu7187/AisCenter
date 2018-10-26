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
@Table(name = "PAYUSL_DOLGNTYPE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PayuslDolgntype.findAll", query = "SELECT p FROM PayuslDolgntype p"),
    @NamedQuery(name = "PayuslDolgntype.findByPayusldolgntypeId", query = "SELECT p FROM PayuslDolgntype p WHERE p.payusldolgntypeId = :payusldolgntypeId"),
    @NamedQuery(name = "PayuslDolgntype.findBySprpayuslId", query = "SELECT p FROM PayuslDolgntype p WHERE p.sprpayuslId = :sprpayuslId")})
public class PayuslDolgntype implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "PAYUSLDOLGNTYPE_ID")
    private Integer payusldolgntypeId;
    @JoinColumn(name = "SPRDOLGNTYPE_ID", referencedColumnName = "SPRDOLGNTYPE_ID")
    @ManyToOne
    private SprDolgnType sprdolgntypeId;
    @JoinColumn(name = "SPRPAYUSL_ID", referencedColumnName = "SPRPAYUSL_ID")
    @ManyToOne
    private SprPayUsl sprpayuslId;

    public PayuslDolgntype() {
    }

    public PayuslDolgntype(Integer payusldolgntypeId) {
        this.payusldolgntypeId = payusldolgntypeId;
    }

    public Integer getPayusldolgntypeId() {
        return payusldolgntypeId;
    }

    public void setPayusldolgntypeId(Integer payusldolgntypeId) {
        this.payusldolgntypeId = payusldolgntypeId;
    }

    public SprDolgnType getSprdolgntypeId() {
        return sprdolgntypeId;
    }

    public void setSprdolgntypeId(SprDolgnType sprdolgntypeId) {
        this.sprdolgntypeId = sprdolgntypeId;
    }

    public SprPayUsl getSprpayuslId() {
        return sprpayuslId;
    }

    public void setSprpayuslId(SprPayUsl sprpayuslId) {
        this.sprpayuslId = sprpayuslId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (payusldolgntypeId != null ? payusldolgntypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PayuslDolgntype)) {
            return false;
        }
        PayuslDolgntype other = (PayuslDolgntype) object;
        if ((this.payusldolgntypeId == null && other.payusldolgntypeId != null) || (this.payusldolgntypeId != null && !this.payusldolgntypeId.equals(other.payusldolgntypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.PayuslDolgntype[ payusldolgntypeId=" + payusldolgntypeId + " ]";
    }

}
