package ServiceInterface;

import Bean.Note;
import Bean.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

public interface NoteServiceInterface extends Remote {

    int ajouterNote(Note note)  throws Exception;

   ArrayList<Note> getAllNotes(int id)  throws  Exception;
   int deleteNote(Note note) throws Exception ;
    int updateNote(Note note) throws Exception ;

    ArrayList<Note> getAllNotesByTitre(int i, String text) throws Exception;
}
