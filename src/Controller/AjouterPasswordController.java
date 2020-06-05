package Controller;

import Bean.Password;
import Bean.User;
import ServiceInterface.LoginServiceInterface;
import ServiceInterface.PasswordServiceInterface;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import util.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.*;

import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.paint.Color;


public class AjouterPasswordController  implements Initializable {
    @FXML
    Button aleat;
    @FXML
    Button enregister;
    @FXML
    PasswordField passwordField;
    @FXML
    AnchorPane baseSection;
    @FXML
    TextField choix1;
    @FXML
    TextField loginField;
    @FXML
    TextField siteField;

    @FXML
TextField passwordFieldClear;
    @FXML
    ComboBox siteCombo;
    @FXML
    ComboBox passwordCombo;
    @FXML
    ComboBox securityCombo;
    int securityLevel=8;
    Boolean isAlétoire=null;
    @FXML
    Button clipBoard;
    PasswordServiceInterface ps ;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        siteField.setVisible(false);
        baseSection.setVisible(true);
        clipBoard.setDisable(true);
        passwordFieldListenners();
        setSiteComboListenner();
        securityComboListenner();
        motChoisiListenner();
        passwordComboListenner();
        securityCombo.setVisible(false);
        passwordFieldClear.setVisible(false);
        passwordCombo.getSelectionModel().select(0);
        securityCombo.getSelectionModel().select(0);
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
        System.out.println();
    }




    public void enregister() throws Exception {
       if(!FxUtil.isFilled(passwordFieldClear)|| !FxUtil.isFilled(loginField))
       {
           AlertUtil.showAlert("Formulaire","Certians champs ne sont pas remplies ");
       }
       else if(!FxUtil.isLength(passwordFieldClear,8))
       {
           AlertUtil.showAlert("Mot de passe ","mot de passe doit contenir plus de 7 caracteres");
       }
       else if (!FxUtil.comboBox(siteField, siteCombo))
       {
           AlertUtil.showAlert("le site ","choisir un site ");
       }
       else
       {
           Password p=new Password(passwordFieldClear.getText(),loginField.getText() ,
                   new Date(), new Date(),siteField.getText(),
                  new User(Integer.parseInt(Session.getAttribut("id").toString())));
          ps.ajouterPassword(p);
          // ps.cc(1);
       }
    }



    public void baseeSur() {

        passwordField.setText("");
        passwordFieldClear.setText("");

        baseSection.setVisible(true);
    }
    public void aleatoire() {
        passwordField.setText("");
        passwordFieldClear.setText("");
        baseSection.setVisible(false);
        passwordField.setText(MotDePasse.generate(securityLevel));
        passwordFieldClear.setText(passwordField.getText());

    }
    public void show(ActionEvent event) {
       if(passwordField.isVisible())
       { passwordFieldClear.setVisible(true);passwordField.setVisible(false); }
       else
       { passwordFieldClear.setVisible(false);passwordField.setVisible(true); }
    }

    public void passwordFieldListenners(){
        passwordField.textProperty().addListener((observable, oldValue, newValue) -> {

        passwordFieldClear.setText(passwordField.getText());
        });
        passwordFieldClear.textProperty().addListener((observable, oldValue, newValue) -> {

        passwordField.setText(passwordFieldClear.getText());
        if(newValue.equals("")) clipBoard.setDisable(true);
           else clipBoard.setDisable(false);
        });
    }
    public void setSiteComboListenner(){

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
    public void securityComboListenner(){
        securityCombo.getItems().addAll ( "niveau:minimal","niveau:moyen","niveau:fort","niveau:puissant");
        securityCombo.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String t, String t1) {
                if(t1.equals("niveau:minimal")){ securityLevel=8; }
                else if(t1.equals("niveau:moyen")) { securityLevel=10; }
                else if(t1.equals("niveau:fort"))  { securityLevel=14; }
                else if(t1.equals("niveau:puissant")) { securityLevel=16; }
                if(isAlétoire!=null){
                    if(isAlétoire==true){ aleatoire(); }
                    else { baseeSur(); }
                }
            }
        });
    }
    public void passwordComboListenner(){
        passwordCombo.getItems().addAll ( "Choix d'utilisateur","Aléatoire","Basé");
        passwordCombo.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String t, String t1) {
                if(t1.equals("Aléatoire")){
                    aleatoire();isAlétoire=true;
                    passwordFieldsControl(true);
                    securityCombo.setVisible(true);
                }
                else  if(t1.equals("Basé")) {
                    baseeSur();isAlétoire=false;
                    passwordFieldsControl(true);
                    securityCombo.setVisible(true);

                }
                else{ passwordFieldsControl(false);
                    securityCombo.setVisible(false);
                    baseSection.setVisible(false);

                }
            }
        });
    }
    public void passwordFieldsControl(Boolean val){
        passwordField.setDisable(val);passwordFieldClear.setDisable(val);
    }
    public void motChoisiListenner(){
        choix1.textProperty().addListener((observable, oldValue, newValue) -> {
            passwordField.setText(MotDePasse.base(newValue,securityLevel));
            passwordFieldClear.setText(passwordField.getText());

        });
    }

    public void copyToclipBoard()

    {
    final Clipboard clipboard = Clipboard.getSystemClipboard();
    final ClipboardContent content = new ClipboardContent();
        content.putString(passwordFieldClear.getText());
        clipboard.setContent(content);
}


}
