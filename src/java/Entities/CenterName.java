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
@Table(name = "CENTER_NAME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CenterName.findAll", query = "SELECT c FROM CenterName c"),
    @NamedQuery(name = "CenterName.findById", query = "SELECT c FROM CenterName c WHERE c.id = :id"),
    @NamedQuery(name = "CenterName.findByName", query = "SELECT c FROM CenterName c WHERE c.name = :name")})
public class CenterName implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Size(max = 255)
    @Column(name = "NAME")
    private String name;
    @Size(max = 20)
    @Column(name = "SHORT_NAME")
    private String shortName;
    @Size(max = 20)
    @Column(name = "PMPK_SHNAME")
    private String pmpkShname;
    @Size(max = 255)
    @Column(name = "PMPK_NAME_ROD")
    private String pmpkNameRod;

    public CenterName() {
    }

    public CenterName(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
    
    public String getPmpkShname() {
        return pmpkShname;
    }

    public void setPmpkShname(String pmpkShname) {
        this.pmpkShname = pmpkShname;
    }
    
    public String getPmpkNameRod() {
        return pmpkNameRod;
    }

    public void setPmpkNameRod(String pmpkNameRod) {
        this.pmpkNameRod = pmpkNameRod;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CenterName)) {
            return false;
        }
        CenterName other = (CenterName) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.CenterName[ id=" + id + " ]";
    }
    
}
