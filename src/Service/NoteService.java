package Service;

import Bean.Note;
import util.DbHelper;
import ServiceInterface.NoteServiceInterface;
import util.DaoEngigne;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.util.ArrayList;

public class NoteService extends UnicastRemoteObject implements NoteServiceInterface {
    private static final long serialVersionUID = 1L;

    public NoteService() throws RemoteException {
    }
    public int ajouterNote(Note note) throws Exception {
    Connection conn = DbHelper.GetDatabaseConnection();
    int result = 0;
    String sql = DaoEngigne.constructDaoSaveRequette(note);
    System.out.println(sql);
    PreparedStatement pSt = conn.prepareStatement(sql);
            try {
        pSt.executeUpdate();
        result = 1;
    } catch (SQLException e) {
        e.printStackTrace();
    }
            conn.close();
            return result;
    }

    public ArrayList<Note> getAllNotesByTitre(int id,String titre) throws Exception {
        Connection conn = DbHelper.GetDatabaseConnection();
        int result = 0;
        String extras=" and titre  LIKE'%"+titre+"%'"+" OR contenu  LIKE'%"+titre+"%'";
        System.out.println(DaoEngigne.selectQuery(Note.class,DaoEngigne.getAllObejctsByForeignKey(new Note(),"user",id,extras)));
        return (ArrayList<Note>) DaoEngigne.selectQuery(Note.class,DaoEngigne.getAllObejctsByForeignKey(new Note(),"user",id,extras));

    }
    public ArrayList<Note> getAllNotes(int id) throws Exception {
        Connection conn = DbHelper.GetDatabaseConnection();
        int result = 0;
        System.out.println(DaoEngigne.selectQuery(Note.class,DaoEngigne.getAllObejctsByForeignKey(new Note(),"user",id,"")));
        return (ArrayList<Note>) DaoEngigne.selectQuery(Note.class,DaoEngigne.getAllObejctsByForeignKey(new Note(),"user",id,""));

    }
    public int deleteNote(Note note) throws Exception {
        Connection conn = DbHelper.GetDatabaseConnection();
        int result = 0;
        PreparedStatement preparedStatement=conn.prepareStatement( DaoEngigne.constructDaoDeleteRequette(note));
result=preparedStatement.executeUpdate();
        System.out.println(result);
return result;
    }
    public int updateNote(Note note) throws Exception {
        Connection conn = DbHelper.GetDatabaseConnection();
        int result = 0;
        PreparedStatement preparedStatement=conn.prepareStatement( DaoEngigne.constructDaoUpdateRequette(note,note.getId()));
        result=preparedStatement.executeUpdate();
        System.out.println(result);
        return result;
    }


}
