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
@Table(name = "PRIYOM_SUBJECT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PriyomSubject.findAll", query = "SELECT p FROM PriyomSubject p"),
    @NamedQuery(name = "PriyomSubject.findByPrsubId", query = "SELECT p FROM PriyomSubject p WHERE p.prsubId = :prsubId"),
    @NamedQuery(name = "PriyomSubject.findByPriyomId", query = "SELECT p FROM PriyomSubject p WHERE p.priyomId = :priyomId"),
    @NamedQuery(name = "PriyomSubject.findByChildId", query = "SELECT p FROM PriyomSubject p WHERE p.childId = :childId")})
public class PriyomSubject implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "PRSUB_ID")
    @SequenceGenerator(name = "SEQ_PRIYOMSUBJECT", sequenceName = "SEQ_PRIYOMSUBJECT", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.IDENTITY, generator="SEQ_PRIYOMSUBJECT")
    private Integer prsubId;
    @JoinColumn(name = "CHILD_ID", referencedColumnName = "CHILD_ID")
    @ManyToOne
    private Children childId;
    @JoinColumn(name = "PRIYOM_ID", referencedColumnName = "PRIYOM_ID")
    @ManyToOne
    private Priyom priyomId;

    public PriyomSubject() {
    }

    public PriyomSubject(Integer prsubId) {
        this.prsubId = prsubId;
    }

    public Integer getPrsubId() {
        return prsubId;
    }

    public void setPrsubId(Integer prsubId) {
        this.prsubId = prsubId;
    }

    public Children getChildId() {
        return childId;
    }

    public void setChildId(Children childId) {
        this.childId = childId;
    }

    public Priyom getPriyomId() {
        return priyomId;
    }

    public void setPriyomId(Priyom priyomId) {
        this.priyomId = priyomId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (prsubId != null ? prsubId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PriyomSubject)) {
            return false;
        }
        PriyomSubject other = (PriyomSubject) object;
        if ((this.prsubId == null && other.prsubId != null) || (this.prsubId != null && !this.prsubId.equals(other.prsubId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.PriyomSubject[ prsubId=" + prsubId + " ]";
    }
    
}
