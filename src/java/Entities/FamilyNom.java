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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Aiglu
 */
@Entity
@Table(name = "FAMILY_NOM")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FamilyNom.findAll", query = "SELECT f FROM FamilyNom f"),
    @NamedQuery(name = "FamilyNom.findByFamNom", query = "SELECT f FROM FamilyNom f WHERE f.famNom = :famNom")})
public class FamilyNom implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "FAM_NOM")
    @SequenceGenerator(name = "SEQ_FAMILY_NOM", sequenceName = "SEQ_FAMILY_NOM", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.IDENTITY, generator="SEQ_FAMILY_NOM")
    private Integer famNom;

    public FamilyNom() {
    }

    public FamilyNom(Integer famNom) {
        this.famNom = famNom;
    }

    public Integer getFamNom() {
        return famNom;
    }

    public void setFamNom(Integer famNom) {
        this.famNom = famNom;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (famNom != null ? famNom.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FamilyNom)) {
            return false;
        }
        FamilyNom other = (FamilyNom) object;
        if ((this.famNom == null && other.famNom != null) || (this.famNom != null && !this.famNom.equals(other.famNom))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.FamilyNom[ famNom=" + famNom + " ]";
    }
    
}
