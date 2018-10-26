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
@Table(name = "IPRA_PERECHEN_TPMPK")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IpraPerechenTpmpk.findAll", query = "SELECT i FROM IpraPerechenTpmpk i"),
    @NamedQuery(name = "IpraPerechenTpmpk.findByIshcorrId", query = "SELECT i FROM IpraPerechenTpmpk i WHERE i.ipraperechentpmpkId = :ipraperechentpmpkId")})
public class IpraPerechenTpmpk implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IPRAPERECHENTPMPK_ID")
    @SequenceGenerator(name = "SEQ_IPRA_PERECHEN_TPMPK", sequenceName = "SEQ_IPRA_PERECHEN_TPMPK", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SEQ_IPRA_PERECHEN_TPMPK")
    private Integer ipraperechentpmpkId;
    @JoinColumn(name = "IPRAPERECHENN_ID", referencedColumnName = "IPRAPERECHENN_ID")
    @ManyToOne
    private IpraPerechenN ipraperechennId;
    @JoinColumn(name = "SPROTHERPMPK_ID", referencedColumnName = "SPROTHERPMPK_ID")
    @ManyToOne
    private SprOtherPmpk sprotherpmpkId;

    public IpraPerechenTpmpk() {
    }

    public IpraPerechenTpmpk(Integer ipraperechentpmpkId) {
        this.ipraperechentpmpkId = ipraperechentpmpkId;
    }

    public Integer getIpraperechentpmpkId() {
        return ipraperechentpmpkId;
    }

    public void setIpraperechentpmpkId(Integer ipraperechentpmpkId) {
        this.ipraperechentpmpkId = ipraperechentpmpkId;
    }

    public IpraPerechenN getIpraperechennId() {
        return ipraperechennId;
    }

    public void setIpraperechennId(IpraPerechenN ipraperechennId) {
        this.ipraperechennId = ipraperechennId;
    }

    public SprOtherPmpk getSprotherpmpkId() {
        return sprotherpmpkId;
    }

    public void setSprotherpmpkId(SprOtherPmpk sprotherpmpkId) {
        this.sprotherpmpkId = sprotherpmpkId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ipraperechentpmpkId != null ? ipraperechentpmpkId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IpraPerechenTpmpk)) {
            return false;
        }
        IpraPerechenTpmpk other = (IpraPerechenTpmpk) object;
        if ((this.ipraperechentpmpkId == null && other.ipraperechentpmpkId != null) || (this.ipraperechentpmpkId != null && !this.ipraperechentpmpkId.equals(other.ipraperechentpmpkId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.IpraPerechenTpmpk[ ipraperechentpmpkId=" + ipraperechentpmpkId + " ]";
    }

}
