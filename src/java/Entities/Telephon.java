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
@Table(name = "TELEPHON")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Telephon.findAll", query = "SELECT t FROM Telephon t"),
    @NamedQuery(name = "Telephon.findByTelId", query = "SELECT t FROM Telephon t WHERE t.telId = :telId"),
    @NamedQuery(name = "Telephon.findByTel", query = "SELECT t FROM Telephon t WHERE t.tel = :tel"),
    @NamedQuery(name = "Telephon.findByParentId", query = "SELECT t FROM Telephon t WHERE t.parentId = :parentId")})
public class Telephon implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "TEL_ID")
    @SequenceGenerator(name = "SEQ_TELEPHON", sequenceName = "SEQ_TELEPHON", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.IDENTITY, generator="SEQ_TELEPHON")
    private Integer telId;
    @Size(max = 50)
    @Column(name = "TEL")
    private String tel;
    @JoinColumn(name = "PARENT_ID", referencedColumnName = "PARENT_ID")
    @ManyToOne
    private Parents parentId;

    public Telephon() {
    }

    public Telephon(Integer telId) {
        this.telId = telId;
    }

    public Integer getTelId() {
        return telId;
    }

    public void setTelId(Integer telId) {
        this.telId = telId;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Parents getParentId() {
        return parentId;
    }

    public void setParentId(Parents parentId) {
        this.parentId = parentId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (telId != null ? telId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Telephon)) {
            return false;
        }
        Telephon other = (Telephon) object;
        if ((this.telId == null && other.telId != null) || (this.telId != null && !this.telId.equals(other.telId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Telephon[ telId=" + telId + " ]";
    }
    
}
