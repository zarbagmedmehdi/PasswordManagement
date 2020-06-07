package Controller;


import Bean.QuestionReponse;
import Service.PasswordService;
import Service.QuestionService;
import ServiceInterface.PasswordServiceInterface;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import util.DaoEngigne;
import util.Session;


import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Client extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../sample/main.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) throws Exception {
       // System.out.println(  DaoEngigne.constructDaoCreateTableRequette(QuestionReponse.class));
        PasswordServiceInterface q=new PasswordService();
        System.out.println(q.getPasswordByCriteria(1,"twitter","sdqsdqs"));
      launch(args);


    }
    public static void forward(ActionEvent actionEvent, String pageName, Class myClass) throws IOException {

        Parent parent = FXMLLoader.load(myClass.getResource(pageName));
        Scene scene = new Scene(parent);
        Stage app_stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        app_stage.setScene(scene);
        app_stage.show();

    }
    public static void forward2(ActionEvent actionEvent, String pageName, Class myClass) throws IOException {
        Parent parent = FXMLLoader.load(myClass.getResource(pageName));
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();


}

}
