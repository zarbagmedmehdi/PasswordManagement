package Service;

import Bean.Note;
import Bean.Password;
import util.DbHelper;
import ServiceInterface.PasswordServiceInterface;
import util.DaoEngigne;
import util.SearchUtil;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

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

        public ArrayList<Password> getAllPasswords(int id) throws Exception {
            Connection conn = DbHelper.GetDatabaseConnection();
            int result = 0;
            System.out.println(DaoEngigne.selectQuery(Password.class,DaoEngigne.getAllObejctsByForeignKey(new Password(),"user",id,"")));
            return (ArrayList<Password>) DaoEngigne.selectQuery(Password.class,DaoEngigne.getAllObejctsByForeignKey(new Password(),"user",id,""));

        }
        public int deletePassword(Password password) throws Exception {
            Connection conn = DbHelper.GetDatabaseConnection();
            int result = 0;
            PreparedStatement preparedStatement=conn.prepareStatement( DaoEngigne.constructDaoDeleteRequette(password));
            result=preparedStatement.executeUpdate();
            System.out.println(result);
            return result;
        }

        @Override
        public int update(Password password) throws Exception {
            Connection conn = DbHelper.GetDatabaseConnection();
            int result = 0;
            PreparedStatement preparedStatement=conn.prepareStatement( DaoEngigne.constructDaoUpdateRequette(password,password.getId()));
            result=preparedStatement.executeUpdate();
            System.out.println(result);
            return result;        }

        public ArrayList<Password> getPasswordByCriteria(int id,String site,String login) throws Exception {
            Connection conn = DbHelper.GetDatabaseConnection();
            int result = 0;
          String  extras=SearchUtil.addConstraint("password", "site", "=",site) ;
            extras+=SearchUtil.addConstraint("password", "identifiant", "=",login) ;
            System.out.println(DaoEngigne.getAllObejctsByForeignKey(new Password(),"user",id,extras));
            System.out.println(DaoEngigne.selectQuery(Password.class,DaoEngigne.getAllObejctsByForeignKey(new Password(),"user",id,extras)));
            return (ArrayList<Password>) DaoEngigne.selectQuery(Password.class,DaoEngigne.getAllObejctsByForeignKey(new Password(),"user",id,extras));

        }


}
