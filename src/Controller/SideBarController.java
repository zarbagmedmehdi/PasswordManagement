package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import util.Session;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SideBarController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
    @FXML
    protected void gotoDashboard(ActionEvent event) throws IOException {
        Client.forward(event, "../sample/dashboard.fxml", this.getClass());
    }
    @FXML
    protected void goToAjoutPassword(ActionEvent event) throws IOException {
        Client.forward(event, "../sample/ajouterPassword.fxml", this.getClass());
    }
    @FXML
    protected void goToAjoutNote(ActionEvent event) throws IOException {
        Client.forward(event, "../sample/ajouterNote.fxml", this.getClass());
    }


    @FXML
    protected void disconnect(ActionEvent event) throws IOException {
        Session.clear();
        Client.forward(event, "../sample/main.fxml", this.getClass());
    }


}
