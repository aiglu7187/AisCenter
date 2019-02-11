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
@Table(name = "PMPK_RESULT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PmpkResult.findAll", query = "SELECT p FROM PmpkResult p"),
    @NamedQuery(name = "PmpkResult.findByPmpkresultId", query = "SELECT p FROM PmpkResult p WHERE p.pmpkresultId = :pmpkresultId"),
    @NamedQuery(name = "PmpkResult.findByPmpkId", query = "SELECT p FROM PmpkResult p WHERE p.pmpkId = :pmpkId")})
public class PmpkResult implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "PMPKRESULT_ID")
    @SequenceGenerator(name = "SEQ_PMPK_RESULT", sequenceName = "SEQ_PMPK_RESULT", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SEQ_PMPK_RESULT")
    private Integer pmpkresultId;
    @JoinColumn(name = "PMPK_ID", referencedColumnName = "PMPK_ID")
    @ManyToOne
    private Pmpk pmpkId;
    @JoinColumn(name = "SPRSTAT_ID", referencedColumnName = "SPRSTAT_ID")
    @ManyToOne
    private SprStat sprstatId;
    @Column(name = "PMPKRESULT_FIRST")
    private Integer pmpkresultFirst;

    public Integer getPmpkresultId() {
        return pmpkresultId;
    }

    public void setPmpkresultId(Integer pmpkresultId) {
        this.pmpkresultId = pmpkresultId;
    }
    
    public Pmpk getPmpkId() {
        return pmpkId;
    }

    public void setPmpkId(Pmpk pmpkId) {
        this.pmpkId = pmpkId;
    }
    
    public SprStat getSprstatId() {
        return sprstatId;
    }

    public void setSprstatId(SprStat sprstatId) {
        this.sprstatId = sprstatId;
    }
    
    public Integer getPmpkresultFirst() {
        return pmpkresultFirst;
    }

    public void setPmpkresultFirst(Integer pmpkresultFirst) {
        this.pmpkresultFirst = pmpkresultFirst;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pmpkresultId != null ? pmpkresultId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PmpkResult)) {
            return false;
        }
        PmpkResult other = (PmpkResult) object;
        if ((this.pmpkresultId == null && other.pmpkresultId != null) 
                || (this.pmpkresultId != null && !this.pmpkresultId.equals(other.pmpkresultId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.PmpkResult[ pmpkresultId=" + pmpkresultId + " ]";
    }
    
}
