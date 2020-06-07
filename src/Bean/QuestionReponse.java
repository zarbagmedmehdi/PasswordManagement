package Bean;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
//@Auteur:ZARBAG
//Classe QuestionReponse

public class QuestionReponse implements Serializable {
    @DBTable(columnName ="id")
    private int id ;
    @DBTable(columnName ="question")
    private String question ;
    @DBTable(columnName ="reponse")
    private String reponse ;
    @DBTable(columnName ="user")
    private int user;
    @DBTable(columnName ="date")
    private Date date ;

    public QuestionReponse() {
    }

    public QuestionReponse(String question, String reponse, int user, Date date) {
        this.question = question;
        this.reponse = reponse;
        this.user = user;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getReponse() {
        return reponse;
    }

    public void setReponse(String reponse) {
        this.reponse = reponse;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {

            return    "question: '" + question + '\'' ;

    }
}
