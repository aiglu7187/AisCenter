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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Aiglu
 */
@Entity
@Table(name = "SPR_LESSON")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SprLesson.findAll", query = "SELECT s FROM SprLesson s"),
    @NamedQuery(name = "SprLesson.findBySprlessonId", query = "SELECT s FROM SprLesson s WHERE s.sprlessonId = :sprlessonId"),
    @NamedQuery(name = "SprLesson.findBySprlessonName", query = "SELECT s FROM SprLesson s WHERE s.sprlessonName = :sprlessonName")})
public class SprLesson implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SPRLESSON_ID")
    private Integer sprlessonId;
    @Size(max = 100)
    @Column(name = "SPRLESSON_NAME")
    private String sprlessonName;

    public Integer getSprlessonId() {
        return sprlessonId;
    }

    public void setSprlessonId(Integer sprlessonId) {
        this.sprlessonId = sprlessonId;
    }
    
    public String getSprlessonName() {
        return sprlessonName;
    }

    public void setSprlessonName(String sprlessonName) {
        this.sprlessonName = sprlessonName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sprlessonId != null ? sprlessonId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SprLesson)) {
            return false;
        }
        SprLesson other = (SprLesson) object;
        if ((this.sprlessonId == null && other.sprlessonId != null) 
                || (this.sprlessonId != null && !this.sprlessonId.equals(other.sprlessonId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.SprLesson[ sprlessonId=" + sprlessonId + " ]";
    }
    
}
