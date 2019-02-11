/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Aiglu
 */
@Entity
@Table(name = "SPR_OU")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SprOu.findAll", query = "SELECT s FROM SprOu s ORDER BY s.sprregId.sprregName, s.sproutypeId, s.sprouShname "),
    @NamedQuery(name = "SprOu.findBySprouId", query = "SELECT s FROM SprOu s WHERE s.sprouId = :sprouId"),
    @NamedQuery(name = "SprOu.findBySprouFullname", query = "SELECT s FROM SprOu s WHERE s.sprouFullname = :sprouFullname"),
    @NamedQuery(name = "SprOu.findBySprouShname", query = "SELECT s FROM SprOu s WHERE s.sprouShname = :sprouShname"),
    @NamedQuery(name = "SprOu.findBySprregId", query = "SELECT s FROM SprOu s WHERE s.sprregId = :sprregId"),
    @NamedQuery(name = "SprOu.findBySprouNom", query = "SELECT s FROM SprOu s WHERE s.sprouNom = :sprouNom"),
    @NamedQuery(name = "SprOu.findBySprouName", query = "SELECT s FROM SprOu s WHERE s.sprouName = :sprouName"),
    @NamedQuery(name = "SprOu.findBySproutypeId", query = "SELECT s FROM SprOu s WHERE s.sproutypeId = :sproutypeId"),
    @NamedQuery(name = "SprOu.findBySprregId", query = "SELECT s FROM SprOu s WHERE s.sprregId = :sprregId")})
public class SprOu implements Serializable {

    @Column(name = "SPROU_NOM")
    private Integer sprouNom;
    @OneToMany(mappedBy = "sprouId")
    private Collection<ChildrenEducond> childrenEducondCollection;
    @JoinColumn(name = "SPRREG_ID", referencedColumnName = "SPRREG_ID")
    @ManyToOne
    private SprRegion sprregId;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SPROU_ID")
    private Integer sprouId;
    @Size(max = 1000)
    @Column(name = "SPROU_FULLNAME")
    private String sprouFullname;
    @Size(max = 255)
    @Column(name = "SPROU_SHNAME")
    private String sprouShname;    
    @Size(max = 100)
    @Column(name = "SPROU_NAME")
    private String sprouName;
    @Size(max = 100)
    @Column(name= "SPROU_DOP_KEY")
    private String sprouDopKey;
    @JoinColumn(name = "SPROUTYPE_ID", referencedColumnName = "SPROUTYPE_ID")
    @ManyToOne
    private SprOuType sproutypeId;

    public SprOu() {
    }

    public SprOu(Integer sprouId) {
        this.sprouId = sprouId;
    }

    public Integer getSprouId() {
        return sprouId;
    }

    public void setSprouId(Integer sprouId) {
        this.sprouId = sprouId;
    }

    public String getSprouFullname() {
        return sprouFullname;
    }

    public void setSprouFullname(String sprouFullname) {
        this.sprouFullname = sprouFullname;
    }

    public String getSprouShname() {
        return sprouShname;
    }

    public void setSprouShname(String sprouShname) {
        this.sprouShname = sprouShname;
    }

    public Integer getSprouNom() {
        return sprouNom;
    }

    public void setSprouNom(Integer sprouNom) {
        this.sprouNom = sprouNom;
    }

    public String getSprouName() {
        return sprouName;
    }

    public void setSprouName(String sprouName) {
        this.sprouName = sprouName;
    }
    
    public String getSprouDopKey() {
        return sprouDopKey;
    }

    public void setSprouDopKey(String sprouDopKey) {
        this.sprouDopKey = sprouDopKey;
    }

    public SprOuType getSproutypeId() {
        return sproutypeId;
    }

    public void setSproutypeId(SprOuType sproutypeId) {
        this.sproutypeId = sproutypeId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sprouId != null ? sprouId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SprOu)) {
            return false;
        }
        SprOu other = (SprOu) object;
        if ((this.sprouId == null && other.sprouId != null) || (this.sprouId != null && !this.sprouId.equals(other.sprouId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.SprOu[ sprouId=" + sprouId + " ]";
    }

    public SprRegion getSprregId() {
        return sprregId;
    }

    public void setSprregId(SprRegion sprregId) {
        this.sprregId = sprregId;
    }


    @XmlTransient
    public Collection<ChildrenEducond> getChildrenEducondCollection() {
        return childrenEducondCollection;
    }

    public void setChildrenEducondCollection(Collection<ChildrenEducond> childrenEducondCollection) {
        this.childrenEducondCollection = childrenEducondCollection;
    }

}
