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
@Table(name = "OBR_OBR")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ObrObr.findAll", query = "SELECT o FROM ObrObr o"),
    @NamedQuery(name = "ObrObr.findByObrobrId", query = "SELECT o FROM ObrObr o WHERE o.obrobrId = :obrobrId"),
    @NamedQuery(name = "ObrObr.findBySprobrIdSoo", query = "SELECT o FROM ObrObr o WHERE o.sprobrIdSoo = :sprobrIdSoo"),
    @NamedQuery(name = "ObrObr.findBySprobrIdMain", query = "SELECT o FROM ObrObr o WHERE o.sprobrIdMain = :sprobrIdMain")})
public class ObrObr implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "OBROBR_ID")
    private BigDecimal obrobrId;
    @JoinColumn(name = "SPROBR_ID_MAIN", referencedColumnName = "SPROBR_ID")
    @ManyToOne
    private SprObr sprobrIdMain;
    @JoinColumn(name = "SPROBR_ID_SOO", referencedColumnName = "SPROBR_ID")
    @ManyToOne
    private SprObr sprobrIdSoo;

    public ObrObr() {
    }

    public ObrObr(BigDecimal obrobrId) {
        this.obrobrId = obrobrId;
    }

    public BigDecimal getObrobrId() {
        return obrobrId;
    }

    public void setObrobrId(BigDecimal obrobrId) {
        this.obrobrId = obrobrId;
    }

    public SprObr getSprobrIdMain() {
        return sprobrIdMain;
    }

    public void setSprobrIdMain(SprObr sprobrIdMain) {
        this.sprobrIdMain = sprobrIdMain;
    }

    public SprObr getSprobrIdSoo() {
        return sprobrIdSoo;
    }

    public void setSprobrIdSoo(SprObr sprobrIdSoo) {
        this.sprobrIdSoo = sprobrIdSoo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (obrobrId != null ? obrobrId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ObrObr)) {
            return false;
        }
        ObrObr other = (ObrObr) object;
        if ((this.obrobrId == null && other.obrobrId != null) || (this.obrobrId != null && !this.obrobrId.equals(other.obrobrId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.ObrObr[ obrobrId=" + obrobrId + " ]";
    }
    
}
