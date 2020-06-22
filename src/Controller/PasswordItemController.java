package Controller;

import Bean.Password;
import ServiceInterface.PasswordServiceInterface;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import util.AlertUtil;
import util.FxUtil;
import util.Session;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class PasswordItemController implements Initializable {
    //////PasswordItem

    @FXML
    TextField loginField1;
    @FXML
    Button modifier ;
    @FXML
    Button delete ;
    @FXML
    Button copier ;
    @FXML
    PasswordField passwordField;
    @FXML
    Label creation;
    @FXML
    Label modification;
    @FXML
    Label site;
    PasswordServiceInterface ps ;
    Password selected;
Boolean activate=true;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String u="rmi://localhost/passwordService";
loginField1.setDisable(true);
        passwordField.setDisable(true);

        try {
            ps =(PasswordServiceInterface) Naming.lookup(u);
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        selected= (Password) Session.getAttribut("selectedPassword");
        loginField1.setText(selected.getIdentifiant());
        passwordField.setText(selected.getMotDepasse());
        creation.setText("Date Création  : "+selected.getDateCreation());
        modification.setText("Date modification : "+selected.getDateModification());
        site.setText(selected.getSite());
    }
    public void modifier(ActionEvent actionEvent) throws Exception {
        if(activate){
            loginField1.setDisable(false);
            passwordField.setDisable(false);
            activate=false;
        }
        else{
            if(FxUtil.isLength(passwordField,7))
            {
                selected.setIdentifiant(loginField1.getText());
                selected.setMotDepasse(passwordField.getText());
                ps.update(selected);
                loginField1.setDisable(true);
                passwordField.setDisable(true);
                Client.forward(actionEvent, "../sample/chercherPassword.fxml", this.getClass());

            }
            else{
                AlertUtil.showAlert("Erreur","Mot de passe doit etre plus de 8 caractéres");
            }


        }

    }
    public void copier()

    {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent content = new ClipboardContent();
        content.putString(passwordField.getText());
        clipboard.setContent(content);
    }
    public void delete(ActionEvent actionEvent) throws Exception {
        if(AlertUtil.showConfirmation("Supprresion","Vous voulez vraiment supprimer? ")){
        ps.deletePassword(selected);
            Client.forward(actionEvent, "../sample/chercherPassword.fxml", this.getClass());
        }
    }

}
