/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Aiglu
 */
@Entity
@Table(name = "KALENDAR")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Kalendar.findAll", query = "SELECT k FROM Kalendar k ORDER BY k.kalendarDate "),
    @NamedQuery(name = "Kalendar.findByKalendarId", query = "SELECT k FROM Kalendar k WHERE k.kalendarId = :kalendarId"),
    @NamedQuery(name = "Kalendar.findByKalendarDate", query = "SELECT k FROM Kalendar k WHERE k.kalendarDate = :kalendarDate"),
    @NamedQuery(name = "Kalendar.findByKalendarWeekend", query = "SELECT k FROM Kalendar k WHERE k.kalendarWeekend = :kalendarWeekend")})
public class Kalendar implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "KALENDAR_ID")
    @SequenceGenerator(name = "SEQ_KALENDAR", sequenceName = "SEQ_KALENDAR", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.IDENTITY, generator="SEQ_KALENDAR")
    private Integer kalendarId;
    @Column(name = "KALENDAR_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date kalendarDate;
    @Column(name = "KALENDAR_WEEKEND")
    private Integer kalendarWeekend;

    public Kalendar() {
    }

    public Kalendar(Integer kalendarId) {
        this.kalendarId = kalendarId;
    }

    public Integer getKalendarId() {
        return kalendarId;
    }

    public void setKalendarId(Integer kalendarId) {
        this.kalendarId = kalendarId;
    }

    public Date getKalendarDate() {
        return kalendarDate;
    }

    public void setKalendarDate(Date kalendarDate) {
        this.kalendarDate = kalendarDate;
    }

    public Integer getKalendarWeekend() {
        return kalendarWeekend;
    }

    public void setKalendarWeekend(Integer kalendarWeekend) {
        this.kalendarWeekend = kalendarWeekend;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kalendarId != null ? kalendarId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Kalendar)) {
            return false;
        }
        Kalendar other = (Kalendar) object;
        if ((this.kalendarId == null && other.kalendarId != null) || (this.kalendarId != null && !this.kalendarId.equals(other.kalendarId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Kalendar[ kalendarId=" + kalendarId + " ]";
    }
    
}
