package Bean;


import java.io.Serializable;
import java.util.Date;
//@Auteur:ZARBAG
//utilisateur de l'application
public class User implements Serializable {
    private long id;
    private String login;
    private String password;
    private Date dateCreation;

    public User() {
    }

    public User(long id) {
        this.id = id;
    }

    public User(long id, String login, String password, Date dateCreation) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.dateCreation = dateCreation;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    @Override
    public String toString() {
        return "Bean.User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", dateCreation=" + dateCreation +
                '}';
    }
}
