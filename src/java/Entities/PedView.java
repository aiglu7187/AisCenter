/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Aiglu
 */
@Entity
@Table(name = "PED_VIEW")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PedView.findAll", query = "SELECT p FROM PedView p"),
    @NamedQuery(name = "PedView.findByPedId", query = "SELECT p FROM PedView p WHERE p.pedId = :pedId"),
    @NamedQuery(name = "PedView.findByPedfam", query = "SELECT p FROM PedView p WHERE p.pedfam = :pedfam"),
    @NamedQuery(name = "PedView.findByPedname", query = "SELECT p FROM PedView p WHERE p.pedname = :pedname"),
    @NamedQuery(name = "PedView.findByPedpatr", query = "SELECT p FROM PedView p WHERE p.pedpatr = :pedpatr"),
    @NamedQuery(name = "PedView.findByPedFIO", query = "SELECT p FROM PedView p WHERE (p.pedfam like :fam) "
            + "AND (p.pedname like :name) AND ((p.pedpatr like :patr) OR (p.pedpatr IS NULL)) ORDER BY p.pedfam,p.pedname,p.pedpatr")})
public class PedView implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "PED_ID")
    private Integer pedId;
    @Size(max = 100)
    @Column(name = "PEDFAM")
    private String pedfam;
    @Size(max = 100)
    @Column(name = "PEDNAME")
    private String pedname;
    @Size(max = 100)
    @Column(name = "PEDPATR")
    private String pedpatr;

    public PedView() {
    }

    public PedView(Integer pedId) {
        this.pedId = pedId;
    }

    public Integer getPedId() {
        return pedId;
    }

    public void setPedId(Integer pedId) {
        this.pedId = pedId;
    }

    public String getPedfam() {
        return pedfam;
    }

    public void setPedfam(String pedfam) {
        this.pedfam = pedfam;
    }

    public String getPedname() {
        return pedname;
    }

    public void setPedname(String pedname) {
        this.pedname = pedname;
    }

    public String getPedpatr() {
        return pedpatr;
    }

    public void setPedpatr(String pedpatr) {
        this.pedpatr = pedpatr;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pedId != null ? pedId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PedView)) {
            return false;
        }
        PedView other = (PedView) object;
        if ((this.pedId == null && other.pedId != null) || (this.pedId != null && !this.pedId.equals(other.pedId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.PedView[ pedId=" + pedId + " ]";
    }
    
}
