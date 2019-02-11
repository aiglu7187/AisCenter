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
@Table(name = "PMPK_REASON")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PmpkReason.findAll", query = "SELECT p FROM PmpkReason p"),
    @NamedQuery(name = "PmpkReason.findByPmpkreasonId", query = "SELECT p FROM PmpkReason p WHERE p.pmpkreasonId = :pmpkreasonId"),
    @NamedQuery(name = "PmpkReason.findByPmpkId", query = "SELECT p FROM PmpkReason p WHERE p.pmpkId = :pmpkId")})
public class PmpkReason implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "PMPKREASON_ID")
    @SequenceGenerator(name = "SEQ_PMPK_REASON", sequenceName = "SEQ_PMPK_REASON", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.IDENTITY, generator="SEQ_PMPK_REASON")
    private Integer pmpkreasonId;
    @JoinColumn(name = "PMPK_ID", referencedColumnName = "PMPK_ID")
    @ManyToOne
    private Pmpk pmpkId;
    @JoinColumn(name = "SPRREASON_ID", referencedColumnName = "SPRREASON_ID")
    @ManyToOne
    private SprReason sprreasonId;
    @Size(max = 500)
    @Column(name = "PMPKREASON_NAME")
    private String pmpkreasonName;

    public Integer getPmpkreasonId() {
        return pmpkreasonId;
    }

    public void setPmpkreasonId(Integer pmpkreasonId) {
        this.pmpkreasonId = pmpkreasonId;
    }
    
    public Pmpk getPmpkId() {
        return pmpkId;
    }

    public void setPmpkId(Pmpk pmpkId) {
        this.pmpkId = pmpkId;
    }
    
    public SprReason getSprreasonId() {
        return sprreasonId;
    }

    public void setSprreasonId(SprReason sprreasonId) {
        this.sprreasonId = sprreasonId;
    }
    
    public String getPmpkreasonName() {
        return pmpkreasonName;
    }

    public void setPmpkreasonName(String pmpkreasonName) {
        this.pmpkreasonName = pmpkreasonName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pmpkreasonId != null ? pmpkreasonId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PmpkReason)) {
            return false;
        }
        PmpkReason other = (PmpkReason) object;
        if ((this.pmpkreasonId == null && other.pmpkreasonId != null) 
                || (this.pmpkreasonId != null && !this.pmpkreasonId.equals(other.pmpkreasonId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.PmpkReason[ pmpkreasonId=" + pmpkreasonId + " ]";
    }
    
}
