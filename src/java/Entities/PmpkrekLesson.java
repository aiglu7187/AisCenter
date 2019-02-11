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
@Table(name = "PMPKREK_LESSON")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PmpkrekLesson.findAll", query = "SELECT p FROM PmpkrekLesson p"),
    @NamedQuery(name = "PmpkrekLesson.findByPmpkreklessonId", query = "SELECT p FROM PmpkrekLesson p WHERE p.pmpkreklessonId = :pmpkreklessonId"),
    @NamedQuery(name = "PmpkrekLesson.findByPmpkId", query = "SELECT p FROM PmpkrekLesson p WHERE p.pmpkId = :pmpkId")})
public class PmpkrekLesson implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "PMPKREKLESSON_ID")
    @SequenceGenerator(name = "SEQ_PMPKREK_LESSON", sequenceName = "SEQ_PMPKREK_LESSON", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SEQ_PMPKREK_LESSON")
    private Integer pmpkreklessonId;
    @JoinColumn(name = "PMPK_ID", referencedColumnName = "PMPK_ID")
    @ManyToOne
    private Pmpk pmpkId;
    @JoinColumn(name = "SPRLESSON_ID", referencedColumnName = "SPRLESSON_ID")
    @ManyToOne
    private SprLesson sprlessonId;

    public Integer getPmpkreklessonId() {
        return pmpkreklessonId;
    }

    public void setPmpkreklessonId(Integer pmpkreklessonId) {
        this.pmpkreklessonId = pmpkreklessonId;
    }
    
    public Pmpk getPmpkId() {
        return pmpkId;
    }

    public void setPmpkId(Pmpk pmpkId) {
        this.pmpkId = pmpkId;
    }
    
    public SprLesson getSprlessonId() {
        return sprlessonId;
    }

    public void setSprlessonId(SprLesson sprlessonId) {
        this.sprlessonId = sprlessonId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pmpkreklessonId != null ? pmpkreklessonId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PmpkrekLesson)) {
            return false;
        }
        PmpkrekLesson other = (PmpkrekLesson) object;
        if ((this.pmpkreklessonId == null && other.pmpkreklessonId != null) 
                || (this.pmpkreklessonId != null && !this.pmpkreklessonId.equals(other.pmpkreklessonId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.PmpkrekLesson[ pmpkreklessonId=" + pmpkreklessonId + " ]";
    }
    
}
