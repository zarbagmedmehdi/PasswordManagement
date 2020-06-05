package Controller;

import Bean.User;
import ServiceInterface.LoginServiceInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import util.AlertUtil;
import util.Session;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ResourceBundle;
//@Auteur:ZARBAG
//Connexion et inscription
public class LoginController  implements Initializable {
    private int p=0;
    LoginServiceInterface lg;
    @FXML
    AnchorPane anchorPane;
    @FXML
    Label labelText;
    @FXML
    Button loginBtn;
    @FXML
    Button signBtn;
    @FXML
    Button valider;
    @FXML
    TextField userField;
    @FXML
    PasswordField passwordField;

    public void connecterBtn(ActionEvent event) {
        anchorPane.setVisible(true);
        labelText.setText("Connexion");
        p=1;

    }
    public void inscriptionBtn(ActionEvent event) {
        anchorPane.setVisible(true);
        labelText.setText("Inscription");
        p=2;

    }


public Boolean validerBtn(ActionEvent event) throws IOException, SQLException {
     int login=userField.getText().length();
     int password=passwordField.getText().length();
     int result;
    if( login>6 && password>6){
        if( p==1) {
            if(lg.login(userField.getText(),passwordField.getText())==1){
                session();

                Client.forward(event, "../sample/dashBoard.fxml", this.getClass());
            }
            else{
                AlertUtil.showAlert("Erreur","pas d'utilisateur avec ce login/password");
            }
        }
        else {
            if(lg.signUp(userField.getText(),passwordField.getText())==1){
                session();
                Client.forward(event, "../sample/dashBoard.fxml", this.getClass());

            }
            else{
                AlertUtil.showAlert("Erreur","difficulté pour s'inscrire");

            }        }
        return true;
    }
    else {
        AlertUtil.loginError();
        return false;
    }
}

public void session() throws RemoteException, SQLException {
        User u=lg.getUser(userField.getText(),passwordField.getText());
    System.out.println(u);
    if( u.getId()==0)
    AlertUtil.showAlert("Erreur","Erreur de connexion veuillez resseyer");
    else  {
        Session.createAtrribute(u.getId(),"id");
        Session.createAtrribute(u.getLogin(),"login");
        Session.createAtrribute(u.getPassword(),"password");
    }
}
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

anchorPane.setVisible(false);
//Appel RMI
        String u="rmi://localhost/loginService";

        try {
            lg=(LoginServiceInterface) Naming.lookup(u);
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        System.out.println();
    }
}
