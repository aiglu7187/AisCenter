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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "PRIYOM_PROBLEM")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PriyomProblem.findAll", query = "SELECT p FROM PriyomProblem p"),
    @NamedQuery(name = "PriyomProblem.findByPriyomId", query = "SELECT p FROM PriyomProblem p WHERE p.priyomId = :priyomId"),
    @NamedQuery(name = "PriyomProblem.findByPriyomproblemId", query = "SELECT p FROM PriyomProblem p WHERE p.priyomproblemId = :priyomproblemId")})
public class PriyomProblem implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "PRIYOMPROBLEM_ID")
    @SequenceGenerator(name = "SEQ_PRIYOMPROBLEM", sequenceName = "SEQ_PRIYOMPROBLEM", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.IDENTITY, generator="SEQ_PRIYOMPROBLEM")
    private Integer priyomproblemId;
    @JoinColumn(name = "PRIYOM_ID", referencedColumnName = "PRIYOM_ID")
    @ManyToOne
    private Priyom priyomId;
    @Column(name = "USER_ID")
    private Integer userId;
    @Column(name = "DATE_UPD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateUpd;
    @JoinColumn(name = "SPRPROBLEM_ID", referencedColumnName = "SPRPROBLEM_ID")
    @ManyToOne
    private SprProblem sprproblemId;

    public PriyomProblem() {
    }

    public PriyomProblem(Integer priyomproblemId) {
        this.priyomproblemId = priyomproblemId;
    }

    public Integer getPriyomproblemId() {
        return priyomproblemId;
    }

    public void setPriyomproblemId(Integer priyomproblemId) {
        this.priyomproblemId = priyomproblemId;
    }

    public Priyom getPriyomId() {
        return priyomId;
    }

    public void setPriyomId(Priyom priyomId) {
        this.priyomId = priyomId;
    }

    public SprProblem getSprproblemId() {
        return sprproblemId;
    }

    public void setSprproblemId(SprProblem sprproblemId) {
        this.sprproblemId = sprproblemId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (priyomproblemId != null ? priyomproblemId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PriyomProblem)) {
            return false;
        }
        PriyomProblem other = (PriyomProblem) object;
        if ((this.priyomproblemId == null && other.priyomproblemId != null) || (this.priyomproblemId != null && !this.priyomproblemId.equals(other.priyomproblemId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.PriyomProblem[ priyomproblemId=" + priyomproblemId + " ]";
    }
    
     public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    
    public Date getDateUpd() {
        return dateUpd;
    }

    public void setDateUpd(Date dateUpd) {
        this.dateUpd = dateUpd;
    }
}
