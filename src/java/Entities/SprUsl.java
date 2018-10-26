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
 * @author admin_ai
 */
@Entity
@Table(name = "SPR_USL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SprUsl.findAll", query = "SELECT s FROM SprUsl s ORDER BY s.sprosnuslId, s.spruslId"),
    @NamedQuery(name = "SprUsl.findBySpruslId", query = "SELECT s FROM SprUsl s WHERE s.spruslId = :spruslId"),
    @NamedQuery(name = "SprUsl.findBySpruslName", query = "SELECT s FROM SprUsl s WHERE s.spruslName = :spruslName"),
    @NamedQuery(name = "SprUsl.findBySpruslTemplate", query = "SELECT s FROM SprUsl s WHERE s.spruslTemplate = :spruslTemplate"),
    @NamedQuery(name = "SprUsl.findBySpruslCenter", query = "SELECT s FROM SprUsl s WHERE s.spruslCenter = :spruslCenter"),
    @NamedQuery(name = "SprUsl.findBySprosnuslId", query = "SELECT s FROM SprUsl s WHERE s.sprosnuslId = :sprosnuslId")})
public class SprUsl implements Serializable {

    @Column(name = "SPRUSL_PLAT")
    private Integer spruslPlat;
    @Column(name = "SPRUSL_CENTER")
    private Integer spruslCenter;
    @Column(name = "SPRUSL_PROBLEM")
    private Integer spruslProblem;
    @Column(name = "SPRUSL_STAT")
    private Integer spruslStat;
    @Column(name = "SPRUSL_MONIT")
    private Integer spruslMonit;
    @Column(name = "SPRUSL_SEANS")
    private Integer spruslSeans;
    @Column(name = "SPRUSL_PMPK")
    private Integer spruslPmpk;
    @Column(name = "SPRUSL_SUBJ")
    private Integer spruslSubj;
    @Column(name = "SPRUSL_LESSON")
    private Integer spruslLesson;
    @Column(name = "SPRUSL_GROUP")
    private Integer spruslGroup;
    /*@Column(name = "SPRUSL_PLAT")
    private Integer spruslPlat;
    @Column(name = "SPRUSL_CENTER")
    private Integer spruslCenter;
    @Column(name = "SPRUSL_PROBLEM")
    private Integer spruslProblem;
    @Column(name = "SPRUSL_STAT")
    private Integer spruslStat;
    @Column(name = "SPRUSL_MONIT")
    private Integer spruslMonit;
    @Column(name = "SPRUSL_SEANS")
    private Integer spruslSeans;
    @Column(name = "SPRUSL_PMPK")
    private Integer spruslPmpk;
    @Column(name = "SPRUSL_SUBJ")
    private Integer spruslSubj;
    @Column(name = "SPRUSL_LESSON")
    private Integer spruslLesson;
    @Column(name = "SPRUSL_GROUP")
    private Integer spruslGroup;*/
    
    @OneToMany(mappedBy = "spruslId")
    private Collection<Groups> groupsCollection;
    @OneToMany(mappedBy = "spruslId")
    private Collection<Priyom> priyomCollection;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SPRUSL_ID")
    private Integer spruslId;
    @Size(max = 300)
    @Column(name = "SPRUSL_NAME")
    private String spruslName;    
    @Size(max = 300)
    @Column(name = "SPRUSL_TEMPLATE")
    private String spruslTemplate;
    @JoinColumn(name = "SPROSNUSL_ID", referencedColumnName = "SPROSNUSL_ID")
    @ManyToOne
    private SprOsnusl sprosnuslId;

    public SprUsl() {
    }

    public SprUsl(Integer spruslId) {
        this.spruslId = spruslId;
    }

    public Integer getSpruslId() {
        return spruslId;
    }

    public void setSpruslId(Integer spruslId) {
        this.spruslId = spruslId;
    }

    public String getSpruslName() {
        return spruslName;
    }

    public void setSpruslName(String spruslName) {
        this.spruslName = spruslName;
    }

    public String getSpruslTemplate() {
        return spruslTemplate;
    }

    public void setSpruslTemplate(String spruslTemplate) {
        this.spruslTemplate = spruslTemplate;
    }

    public Integer getSpruslCenter() {
        return spruslCenter;
    }

    public void setSpruslCenter(Integer spruslCenter) {
        this.spruslCenter = spruslCenter;
    }

    public Integer getSpruslProblem() {
        return spruslProblem;
    }

    public void setSpruslProblem(Integer spruslProblem) {
        this.spruslCenter = spruslProblem;
    }
    
    public Integer getSpruslStat() {
        return spruslStat;
    }

    public void setSpruslStat(Integer spruslStat) {
        this.spruslStat = spruslStat;
    }
    
    public Integer getSpruslMonit() {
        return spruslMonit;
    }

    public void setSpruslMonit(Integer spruslMonit) {
        this.spruslMonit = spruslMonit;
    }
    
    public Integer getSpruslSeans() {
        return spruslSeans;
    }

    public void setSpruslSeans(Integer spruslSeans) {
        this.spruslSeans = spruslSeans;
    }
    
    public Integer getSpruslPmpk() {
        return spruslPmpk;
    }

    public void setSpruslPmpk(Integer spruslPmpk) {
        this.spruslPmpk = spruslPmpk;
    }
    
    public Integer getSpruslSubj() {
        return spruslSubj;
    }

    public void setSpruslSubj(Integer spruslSubj) {
        this.spruslSubj = spruslSubj;
    }
    
    public SprOsnusl getSprosnuslId() {
        return sprosnuslId;
    }

    public void setSprosnuslId(SprOsnusl sprosnuslId) {
        this.sprosnuslId = sprosnuslId;
    }
    
    public Integer getSpruslLesson(){
        return spruslLesson;
    }
    
    public void setSpruslLesson(Integer spruslLesson){
        this.spruslLesson = spruslLesson;
    }    
        
    public Integer getSpruslGroup(){
        return spruslGroup;
    }
    
    public void setSpruslGroup(Integer spruslGroup){
        this.spruslGroup = spruslGroup;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (spruslId != null ? spruslId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SprUsl)) {
            return false;
        }
        SprUsl other = (SprUsl) object;
        if ((this.spruslId == null && other.spruslId != null) || (this.spruslId != null && !this.spruslId.equals(other.spruslId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.SprUsl[ spruslId=" + spruslId + " ]";
    }

    @XmlTransient
    public Collection<Priyom> getPriyomCollection() {
        return priyomCollection;
    }

    public void setPriyomCollection(Collection<Priyom> priyomCollection) {
        this.priyomCollection = priyomCollection;
    }

    @XmlTransient
    public Collection<Groups> getGroupsCollection() {
        return groupsCollection;
    }

    public void setGroupsCollection(Collection<Groups> groupsCollection) {
        this.groupsCollection = groupsCollection;
    }

}
