package Bean;

import java.io.Serializable;
import java.util.Date;
//@Auteur:ZARBAG
//Classe password
public class Password implements Serializable {
    @DBTable(columnName ="id")
    private int id;
    @DBTable(columnName ="motDepasse")

    private String motDepasse;
    @DBTable(columnName ="identifiant")

    private String identifiant;
    @DBTable(columnName ="dateCreation")

    private Date dateCreation;
    @DBTable(columnName ="dateModification")

    private Date dateModification;
    @DBTable(columnName ="site")

    private String site;
    @DBTable(columnName ="user")

    private int user;

    public Password( String motDepasse, String identifiant, Date dateCreation, Date dateModification, String site,int user) {
        this.motDepasse = motDepasse;
        this.identifiant = identifiant;
        this.dateCreation = dateCreation;
        this.dateModification = dateModification;
        this.site = site;
        this.user = user;
    }

    public Password(int id) {
        this.id = id;
    }

    public Password() {
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
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
        return

                " Login:'" + identifiant + '\'' +

                ", site:'" + site + '\'' ;

    }
}
