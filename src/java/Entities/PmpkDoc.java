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
@Table(name = "PMPK_DOC")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PmpkDoc.findAll", query = "SELECT p FROM PmpkDoc p"),
    @NamedQuery(name = "PmpkDoc.findByPmpkdocId", query = "SELECT p FROM PmpkDoc p WHERE p.pmpkdocId = :pmpkdocId"),
    @NamedQuery(name = "PmpkDoc.findByPmpkId", query = "SELECT p FROM PmpkDoc p WHERE p.pmpkId = :pmpkId")})
public class PmpkDoc implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "PMPKDOC_ID")
    @SequenceGenerator(name = "SEQ_PMPK_DOC", sequenceName = "SEQ_PMPK_DOC", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SEQ_PMPK_DOC")
    private Integer pmpkdocId;
    @JoinColumn(name = "PMPK_ID", referencedColumnName = "PMPK_ID")
    @ManyToOne
    private Pmpk pmpkId;
    @JoinColumn(name = "SPRDOC_ID", referencedColumnName = "SPRDOC_ID")
    @ManyToOne
    private SprDoc sprdocId;
    @Size(max = 500)
    @Column(name = "PMPKDOC_NAME")
    private String pmpkdocName;

    public Integer getPmpkdocId() {
        return pmpkdocId;
    }

    public void setPmpkdocId(Integer pmpkdocId) {
        this.pmpkdocId = pmpkdocId;
    }

    public Pmpk getPmpkId() {
        return pmpkId;
    }

    public void setPmpkId(Pmpk pmpkId) {
        this.pmpkId = pmpkId;
    }
    
    public SprDoc getSprdocId() {
        return sprdocId;
    }

    public void setSprdocId(SprDoc sprdocId) {
        this.sprdocId = sprdocId;
    }
    
    public String getPmpkdocName() {
        return pmpkdocName;
    }

    public void setPmpkdocName(String pmpkdocName) {
        this.pmpkdocName = pmpkdocName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pmpkdocId != null ? pmpkdocId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PmpkDoc)) {
            return false;
        }
        PmpkDoc other = (PmpkDoc) object;
        if ((this.pmpkdocId == null && other.pmpkdocId != null) 
                || (this.pmpkdocId != null && !this.pmpkdocId.equals(other.pmpkdocId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.PmpkDoc[ pmpkdocId=" + pmpkdocId + " ]";
    }

}
