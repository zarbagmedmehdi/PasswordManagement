package Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import util.Session;

import java.net.URL;
import java.util.ResourceBundle;

public class DashBoardController  implements Initializable {
    @FXML
    Label label;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        label.setText(Session.getAttribut("login").toString());
    }
}
