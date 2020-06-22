package Controller;

import Bean.Password;
import Bean.QuestionReponse;
import Service.QuestionService;
import ServiceInterface.PasswordServiceInterface;
import ServiceInterface.QuestionServiceInterface;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import java.util.ResourceBundle;

public class ChercherPasswordContoller  implements Initializable {
    //////PasswordItem

    ///////////
    @FXML
    Label label;
    @FXML
    TextField loginField;
    @FXML
    TextField siteField;
    @FXML
    ComboBox siteCombo;
    @FXML
    Button search;
    @FXML
    Button montrer;
    @FXML
    Button look;
    @FXML
    ListView<Password> passwordListView;
    PasswordServiceInterface ps ;
    QuestionServiceInterface qs;

    ObservableList<Password> passwordObservableList = FXCollections.observableList(new ArrayList<>());
    Password selected=null;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
         siteField.setVisible(false);
        setSiteComboListenner();
        String u="rmi://localhost/passwordService";

        try {
            ps =(PasswordServiceInterface) Naming.lookup(u);
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
fetchPasswords();
        setPasswordListView();
    }

    public void search(ActionEvent actionEvent) {
        FetchNotesByCriteria();
    }

    public void look(ActionEvent actionEvent) {
        fetchPasswords();
    }

    public void montrer(ActionEvent actionEvent) throws Exception {
        if(passwordListView.getSelectionModel().getSelectedItem()!=null){
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
        ArrayList<QuestionReponse> list=qs.getAllQuesion(Integer.parseInt(Session.getAttribut("id").toString()));
        int r=FxUtil.getRandom(list.size());
            if (AlertUtil.showInputalert("Question",list.get(r).getQuestion(),list.get(r).getReponse()))
            {Session.createAtrribute(selected,"selectedPassword");
                Client.forward(actionEvent, "../sample/passwordIIem.fxml", this.getClass());

            }

            else {AlertUtil.showAlert("Réponse","incorrecte");}

        }
        else             AlertUtil.showAlert("Selection","sélectionnez un élement");


    }

    public void setSiteComboListenner(){
siteField.setText("twitter");
        siteCombo.getItems().addAll ( "twitter","facebook", "amazon","imdb","pinterest", "tripadvisor","instagram","instagram", "pinterest","tripadvisor","", "ebay","linkedin","apple", "microsoft",
                "quora","netflix", "yahoo", "spotify", "paypal","Autre...");
        siteCombo.getSelectionModel().select(0);
        siteCombo.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String t, String t1) {
                if(t1.equals("Autre...")){  siteField.setVisible(true);
                    siteField.setText("");
                }
                else {  siteField.setVisible(false);
                    siteField.setText(t1);
                }
            }
        });
    }
    public void fetchPasswords()
    {passwordObservableList= FXCollections.observableList(new ArrayList<>());
        try {
            passwordObservableList= FXCollections.observableList
                    (ps.getAllPasswords(Integer.parseInt(Session.getAttribut("id").toString())));

        } catch (Exception e) {
            e.printStackTrace();
        }
        passwordListView.
                setItems
                        ( passwordObservableList);
        passwordListView.refresh();

    }

   public void FetchNotesByCriteria()
    {passwordObservableList=FXCollections.observableList(new ArrayList<>());
        try {
            passwordObservableList= FXCollections.observableList(
                   ps.getPasswordByCriteria(Integer.parseInt(Session.getAttribut("id").toString()),
                            siteField.getText(),loginField.getText()));

        } catch (Exception e) {
            e.printStackTrace();
        }
        passwordListView.setItems(passwordObservableList);
        passwordListView.refresh();

    }
    public void setPasswordListView()
    {
        passwordListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        passwordListView.getSelectionModel().selectedItemProperty().addListener((obs, ov, nv) -> {
        if(passwordListView.getSelectionModel().getSelectedItem()!=null){
            selected=nv;

        }
    });
    }



    /// PasswordItem


}