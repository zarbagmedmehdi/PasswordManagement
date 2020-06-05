package Service;

import Bean.Password;
import util.DbHelper;
import ServiceInterface.PasswordServiceInterface;
import util.DaoEngigne;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PasswordService extends UnicastRemoteObject implements PasswordServiceInterface
    {
        private static final long serialVersionUID = 1L;


        public PasswordService() throws RemoteException {
    }
        public int ajouterPassword(Password password) throws Exception {
            Connection conn = DbHelper.GetDatabaseConnection();
            int result = 0;
            String sql =DaoEngigne.constructDaoSaveRequette(password);
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




}
