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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author admin_ai
 */
@Entity
@Table(name = "FAMILY")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Family.findAll", query = "SELECT f FROM Family f"),
    @NamedQuery(name = "Family.findByFamId", query = "SELECT f FROM Family f WHERE f.famId = :famId"),
    @NamedQuery(name = "Family.findByFamNom", query = "SELECT f FROM Family f WHERE f.famNom = :famNom"),
    @NamedQuery(name = "Family.findByClientId", query = "SELECT f FROM Family f WHERE f.clientId = :clientId"),
    @NamedQuery(name = "Family.findByFamKatcl", query = "SELECT f FROM Family f WHERE f.famKatcl = :famKatcl"),    
    @NamedQuery(name = "Family.findByClientAndKatcl", query = "SELECT f FROM Family f WHERE f.famKatcl = :famKatcl AND f.clientId = :clientId")})
public class Family implements Serializable {

    @Column(name = "CLIENT_ID")
    private Integer clientId;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "FAM_ID")
    @SequenceGenerator(name = "SEQ_FAMILY", sequenceName = "SEQ_FAMILY", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.IDENTITY, generator="SEQ_FAMILY")
    private Integer famId;
    @Column(name = "FAM_NOM")
    private Integer famNom;
    @Size(max = 20)
    @Column(name = "FAM_KATCL")
    private String famKatcl;

    public Family() {
    }

    public Family(Integer famId) {
        this.famId = famId;
    }

    public Integer getFamId() {
        return famId;
    }

    public void setFamId(Integer famId) {
        this.famId = famId;
    }

    public Integer getFamNom() {
        return famNom;
    }

    public void setFamNom(Integer famNom) {
        this.famNom = famNom;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public String getFamKatcl() {
        return famKatcl;
    }

    public void setFamKatcl(String famKatcl) {
        this.famKatcl = famKatcl;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (famId != null ? famId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Family)) {
            return false;
        }
        Family other = (Family) object;
        if ((this.famId == null && other.famId != null) || (this.famId != null && !this.famId.equals(other.famId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Family[ famId=" + famId + " ]";
    }

}
