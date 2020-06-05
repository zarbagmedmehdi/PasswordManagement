package Bean;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
//@Auteur:ZARBAG
//Classe note

public class Note implements Serializable {
    @DBTable(columnName ="id")
    private int id ;
    @DBTable(columnName ="titre")
    private String titre ;
    @DBTable(columnName ="contenu")
    private String contenu ;

    @DBTable(columnName ="user")
    private int user;
    @DBTable(columnName ="date")
    private Date date ;

    public Note(String titre, String contenu, int user, Date date) {
        this.titre = titre;
        this.contenu = contenu;
        this.user = user;
        this.date = date;
    }

    public Note() {
        this.titre = "";
        this.contenu = "";
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    @Override
    public String toString() {
        final DateFormat df = new SimpleDateFormat("MM/dd/yyyy");

        return

                " " + titre.toUpperCase() + '\'' +

                "  " + date
               ;
    }
    }
