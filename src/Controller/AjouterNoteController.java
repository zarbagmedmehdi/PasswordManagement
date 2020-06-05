package Controller;

import Bean.Note;

import ServiceInterface.NoteServiceInterface;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
//@Auteur:ZARBAG
//Controlleur pour la ajouter note
public class AjouterNoteController   implements Initializable {
    @FXML
    Button search;
    @FXML
    Button delete;
    @FXML
    Button modifier;
    @FXML
    Button enregister;
    @FXML
    TextField titreField;
    @FXML
    TextArea  contenuTextArea;
    @FXML
    TextField titreSearchField;
    @FXML
    TextArea  contenuTextArea1;
    @FXML
    TextField titreField1;
    @FXML
    ListView<Note>   noteListView;
    ObservableList<Note> noteObservableList = FXCollections.observableList(new ArrayList<>());
    Note selected=null;
    @FXML
     AnchorPane noteAnchorPane;
    @FXML
    AnchorPane noteAnchorPane1;
    NoteServiceInterface ns;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Appel RMI
        String u="rmi://localhost/noteService";

        try {
            ns = (NoteServiceInterface) Naming.lookup(u);
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        noteAnchorPane1.setVisible(false);
        fetchNotes();
        noteListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        noteListView.getSelectionModel().selectedItemProperty().addListener((obs, ov, nv) -> {
            if(noteListView.getSelectionModel().getSelectedItem()!=null){
                selected=nv;
                form1( nv,true);
                noteAnchorPane1.setVisible(true);
                System.out.println(nv.getId());
            }
        });

    }
//Creation de la note
    public void creer() throws Exception {
        if(!FxUtil.isFilled(titreField) ||  !FxUtil.isFilled(contenuTextArea)){
            AlertUtil.showAlert("Erreur","Remplir les données");
        }
        else{
            ns.ajouterNote(new Note( titreField.getText(), contenuTextArea.getText(),
                      Integer.parseInt(Session.getAttribut("id").toString()),new Date()));

            form( new Note(),false);
            fetchNotes();
            AlertUtil.showAlert("Succées","Note Enregistré", Alert.AlertType.CONFIRMATION);

        }
    }
    //Modifier La note
    public void modifier() throws Exception {
           if(titreField1.isDisable() && contenuTextArea1.isDisable() ) {
               titreField1.setDisable(false);
               contenuTextArea1.setDisable(false);
           }
           else{
               if(AlertUtil.showConfirmation("modification","Vous voulez modifier cette note")){
                   selected.setTitre(titreField1.getText());
                   selected.setContenu(contenuTextArea1.getText());
                   selected.setDate(new Date());
                   ns.updateNote(selected);
                   form1(new Note(),false) ;
                   noteAnchorPane1.setVisible(false);
                   fetchNotes();
               }
           }

    }
    //Suppression de la note
    public void delete() throws Exception {
        if(noteListView.getSelectionModel().getSelectedItem()!=null){
            if(AlertUtil.showConfirmation("Suppression","Vous voulez supprimer cette note")){
            Note deleted=(noteListView.getSelectionModel().getSelectedItem());
            System.out.println(ns.deleteNote(deleted));
            noteObservableList.remove(deleted);
            noteListView.refresh();
            form1( new Note(),false);
            noteAnchorPane1.setVisible(false);
            fetchNotes();

            }

        }
        else AlertUtil.showAlert("Suppression","Vous devez choisir un note");


    }
    public void fetchNotes()
    {noteObservableList=FXCollections.observableList(new ArrayList<>());
        try {
            noteObservableList= FXCollections.observableList
                    (ns.getAllNotes(Integer.parseInt(Session.getAttribut("id").toString())));

        } catch (Exception e) {
            e.printStackTrace();
        }
        noteListView.setItems(noteObservableList);
        noteListView.refresh();

    }

    public void fetchNotesByTitre()
    {noteObservableList=FXCollections.observableList(new ArrayList<>());
        try {
            noteObservableList= FXCollections.observableList(
                    ns.getAllNotesByTitre(Integer.parseInt(Session.getAttribut("id").toString()),
                            titreSearchField.getText()));

        } catch (Exception e) {
            e.printStackTrace();
        }
        noteListView.setItems(noteObservableList);
        noteListView.refresh();

    }
    public void form(Note nv,Boolean val) {
        titreField.setText(nv.getTitre());
        contenuTextArea.setText(nv.getContenu());
        titreField.setDisable(val);
        contenuTextArea.setDisable(val);
    }
    public void form1(Note nv,Boolean val) {
        titreField1.setText(nv.getTitre());
        contenuTextArea1.setText(nv.getContenu());
        titreField1.setDisable(val);
        contenuTextArea1.setDisable(val);
    }
    public void search(ActionEvent actionEvent) {
        fetchNotesByTitre();
    }
}
