package Bean;

import java.io.Serializable;
import java.util.Date;
//@Auteur:ZARBAG
//Classe password
public class Password implements Serializable {

    private long id;
    private String motDepasse;
    private String identifiant;
    private Date dateCreation;
    private Date dateModification;
    private String site;
   private User user;

    public Password( String motDepasse, String identifiant, Date dateCreation, Date dateModification, String site,User user) {
        this.motDepasse = motDepasse;
        this.identifiant = identifiant;
        this.dateCreation = dateCreation;
        this.dateModification = dateModification;
        this.site = site;
        this.user = user;
    }

    public Password(long id) {
        this.id = id;
    }

    public Password() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMotDepasse() {
        return motDepasse;
    }

    public void setMotDepasse(String motDepasse) {
        this.motDepasse = motDepasse;
    }

    public String getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Date getDateModification() {
        return dateModification;
    }

    public void setDateModification(Date dateModification) {
        this.dateModification = dateModification;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    @Override
    public String toString() {
        return "Password{" +
                "id=" + id +
                ", motDepasse='" + motDepasse + '\'' +
                ", identifiant='" + identifiant + '\'' +
                ", dateCreation=" + dateCreation +
                ", dateModification=" + dateModification +
                ", site='" + site + '\'' +
                ", user=" + user +
                '}';
    }
}
