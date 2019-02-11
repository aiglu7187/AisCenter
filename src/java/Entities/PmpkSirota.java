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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Aiglu
 */
@Entity
@Table(name = "PMPK_SIROTA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PmpkSirota.findAll", query = "SELECT p FROM PmpkSirota p"),
    @NamedQuery(name = "PmpkSirota.findByPmpksirotaId", query = "SELECT p FROM PmpkSirota p WHERE p.pmpksirotaId = :pmpksirotaId"),
    @NamedQuery(name = "PmpkSirota.findByPmpkId", query = "SELECT p FROM PmpkSirota p WHERE p.pmpkId = :pmpkId")})
public class PmpkSirota implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "PMPKSIROTA_ID")
    @SequenceGenerator(name = "SEQ_PMPK_SIROTA", sequenceName = "SEQ_PMPK_SIROTA", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.IDENTITY, generator="SEQ_PMPK_SIROTA")
    private Integer pmpksirotaId;
    @JoinColumn(name = "PMPK_ID", referencedColumnName = "PMPK_ID")
    @ManyToOne
    private Pmpk pmpkId;
    @Column(name = "PMPKSIROTA_DDI")
    private Integer pmpksirotaDdi;
    @Column(name = "PMPKSIROTA_PNI")
    private Integer pmpksirotaPni;

    public Integer getPmpksirotaId() {
        return pmpksirotaId;
    }

    public void setPmpksirotaId(Integer pmpksirotaId) {
        this.pmpksirotaId = pmpksirotaId;
    }
    
    public Pmpk getPmpkId() {
        return pmpkId;
    }

    public void setPmpkId(Pmpk pmpkId) {
        this.pmpkId = pmpkId;
    }
    
    public Integer getPmpksirotaDdi() {
        return pmpksirotaDdi;
    }

    public void setPmpksirotaDdi(Integer pmpksirotaDdi) {
        this.pmpksirotaDdi = pmpksirotaDdi;
    }
    
    public Integer getPmpksirotaPni() {
        return pmpksirotaPni;
    }

    public void setPmpksirotaPni(Integer pmpksirotaPni) {
        this.pmpksirotaPni = pmpksirotaPni;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pmpksirotaId != null ? pmpksirotaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PmpkSirota)) {
            return false;
        }
        PmpkSirota other = (PmpkSirota) object;
        if ((this.pmpksirotaId == null && other.pmpksirotaId != null) 
                || (this.pmpksirotaId != null && !this.pmpksirotaId.equals(other.pmpksirotaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.PmpkSirota[ pmpksirotaId=" + pmpksirotaId + " ]";
    }
    
}
