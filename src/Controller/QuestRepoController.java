package Controller;

import Bean.Note;
import Bean.QuestionReponse;
import ServiceInterface.PasswordServiceInterface;
import ServiceInterface.QuestionServiceInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import util.AlertUtil;
import util.FxUtil;
import util.Session;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class QuestRepoController implements Initializable {
    @FXML
    Label label;
    @FXML
    TextField questionField;
    @FXML
    TextField reponseField;
    @FXML
    Button enregister;
    @FXML
    ListView<QuestionReponse> questionListView;
    ObservableList<QuestionReponse> questObservableList = FXCollections.observableList(new ArrayList<>());
    QuestionServiceInterface qs;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("hhh"+Integer.parseInt(Session.getAttribut("id").toString()));
        String u="rmi://localhost/questionService";
        try {
            qs =(QuestionServiceInterface) Naming.lookup(u);
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        fetchQuestions();
    }


    public void enregister(ActionEvent actionEvent) throws Exception {
        if(!FxUtil.isFilled(questionField) ||  !FxUtil.isFilled(reponseField)){
            AlertUtil.showAlert("Erreur","Remplir les données");
        }
        else{
           qs.ajouterQuestion(
                   new QuestionReponse
                           ( questionField.getText(),
                                   reponseField.getText(),
                   Integer.parseInt(Session.getAttribut("id").toString()),
                                   new Date()));
            questionField.setText("");
            reponseField.setText("");
            fetchQuestions();
            AlertUtil.showAlert("Succées","Question/réponse Enregistré", Alert.AlertType.CONFIRMATION);

        }
}

    public void fetchQuestions()
    {questObservableList= FXCollections.observableList(new ArrayList<>());
        try {
            questObservableList= FXCollections.observableList
                    (qs.getAllQuesion(Integer.parseInt(Session.getAttribut("id").toString())));
            System.out.println(qs.getAllQuesion(Integer.parseInt(Session.getAttribut("id").toString())));
        } catch (Exception e) {
            e.printStackTrace();
        }
        questionListView.setItems(questObservableList);
        questionListView.refresh();

    }    }