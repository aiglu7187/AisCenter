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
@Table(name = "PMPK_OU")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PmpkOu.findAll", query = "SELECT p FROM PmpkOu p"),
    @NamedQuery(name = "PmpkOu.findByPmpkouId", query = "SELECT p FROM PmpkOu p WHERE p.pmpkouId = :pmpkouId"),
    @NamedQuery(name = "PmpkOu.findByPmpkId", query = "SELECT p FROM PmpkOu p WHERE p.pmpkId = :pmpkId")})
public class PmpkOu implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "PMPKOU_ID")
    @SequenceGenerator(name = "SEQ_PMPK_OU", sequenceName = "SEQ_PMPK_OU", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.IDENTITY, generator="SEQ_PMPK_OU")
    private Integer pmpkouId;
    @JoinColumn(name = "PMPK_ID", referencedColumnName = "PMPK_ID")
    @ManyToOne
    private Pmpk pmpkId;
    @JoinColumn(name = "SPROU_ID", referencedColumnName = "SPROU_ID")
    @ManyToOne
    private SprOu sprouId;
    @JoinColumn(name = "SPRSTAGE_ID", referencedColumnName = "SPRSTAGE_ID")
    @ManyToOne
    private SprStage sprstageId;
    @JoinColumn(name = "SPROBRTYPE_ID", referencedColumnName = "SPROBRTYPE_ID")
    @ManyToOne
    private SprObrType sprobrtypeId;
    @JoinColumn(name = "SPROBR_ID", referencedColumnName = "SPROBR_ID")
    @ManyToOne
    private SprObr sprobrId;
    @JoinColumn(name = "SPROBRVAR_ID", referencedColumnName = "SPROBRVAR_ID")
    @ManyToOne
    private SprObrVar sprobrvarId;
    @Size(max = 1000)
    @Column(name = "PMPKOU_OBR_NAME")
    private String pmpkouObrName;

    public Integer getPmpkouId() {
        return pmpkouId;
    }

    public void setPmpkouId(Integer pmpkouId) {
        this.pmpkouId = pmpkouId;
    }
    
    public Pmpk getPmpkId() {
        return pmpkId;
    }

    public void setPmpkId(Pmpk pmpkId) {
        this.pmpkId = pmpkId;
    }
    
    public SprOu getSprouId() {
        return sprouId;
    }

    public void setSprouId(SprOu sprouId) {
        this.sprouId = sprouId;
    }
    
    public SprStage getSprstageId() {
        return sprstageId;
    }

    public void setSprstageId(SprStage sprstageId) {
        this.sprstageId = sprstageId;
    }
    
    public SprObrType getSprobrtypeId() {
        return sprobrtypeId;
    }

    public void setSprobrtypeId(SprObrType sprobrtypeId) {
        this.sprobrtypeId = sprobrtypeId;
    }
    
    public SprObr getSprobrId() {
        return sprobrId;
    }

    public void setSprobrId(SprObr sprobrId) {
        this.sprobrId = sprobrId;
    }
    
    public SprObrVar getSprobrvarId() {
        return sprobrvarId;
    }

    public void setSprobrvarId(SprObrVar sprobrvarId) {
        this.sprobrvarId = sprobrvarId;
    }
    
    public String getPmpkouObrName() {
        return pmpkouObrName;
    }

    public void setPmpkouObrName(String pmpkouObrName) {
        this.pmpkouObrName = pmpkouObrName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pmpkouId != null ? pmpkouId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PmpkOu)) {
            return false;
        }
        PmpkOu other = (PmpkOu) object;
        if ((this.pmpkouId == null && other.pmpkouId != null) 
                || (this.pmpkouId != null && !this.pmpkouId.equals(other.pmpkouId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.PmpkOu[ pmpkouId=" + pmpkouId + " ]";
    }
    
}
