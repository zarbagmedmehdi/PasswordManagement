package Service;

import Bean.Note;
import Bean.QuestionReponse;
import ServiceInterface.NoteServiceInterface;
import ServiceInterface.QuestionServiceInterface;
import util.DaoEngigne;
import util.DbHelper;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class QuestionService extends UnicastRemoteObject implements QuestionServiceInterface {
    private static final long serialVersionUID = 1L;

    public QuestionService() throws RemoteException {
    }


    @Override
    public int ajouterQuestion(QuestionReponse questionReponse) throws Exception {
        Connection conn = DbHelper.GetDatabaseConnection();
        int result = 0;
        String sql = DaoEngigne.constructDaoSaveRequette(questionReponse);
        System.out.println(sql);
        PreparedStatement pSt = conn.prepareStatement(sql);
        try {
            pSt.executeUpdate();
            result = 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        conn.close();
        return result;    }

    @Override
    public ArrayList<QuestionReponse> getAllQuesion(int id) throws Exception {
        return (ArrayList<QuestionReponse>) DaoEngigne.selectQuery(QuestionReponse.class,DaoEngigne.getAllObejctsByForeignKey(new QuestionReponse(),"user",id,""));    }
}
