package Service;

import Bean.User;
import util.DbHelper;
import ServiceInterface.LoginServiceInterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.util.Calendar;

public class LoginService extends UnicastRemoteObject implements LoginServiceInterface {
    private static final long serialVersionUID = 1L;

    public LoginService() throws RemoteException {
    }

    public int signUp(String login, String password) throws SQLException {
        Connection conn = DbHelper.GetDatabaseConnection();
        int result = 0;
        String sql = "INSERT INTO user"
                + "(login, password,date_creation) VALUES"
                + "(?,?,?)";
        PreparedStatement pSt = conn.prepareStatement(sql);
        pSt.setString(1, login);
        pSt.setString(2, password);
        pSt.setDate(3, new Date(Calendar.getInstance().getTimeInMillis()));

        try {
            pSt.executeUpdate();
            result = 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        conn.close();
        return result;
    }


    public int login(String login, String password) throws SQLException {
        Connection conn = DbHelper.GetDatabaseConnection();
        String sqlString = "SELECT * from user WHERE login=? and password=?";
        PreparedStatement log = conn.prepareStatement(sqlString);
        log.setString(1, login);
        log.setString(2, password);
        ResultSet rs = log.executeQuery();
        if( rs.next()){
            return 1;
        } else {
            return 0;
        } }
    public User getUser(String login, String password) throws SQLException {
        Connection conn = DbHelper.GetDatabaseConnection();
        String sqlString = "SELECT * from user WHERE login=? and password=?";
        PreparedStatement log = conn.prepareStatement(sqlString);
        log.setString(1, login);
        log.setString(2, password);
        ResultSet rs = log.executeQuery();
        if( rs.next()){
           return new User( rs.getLong(1), rs.getString(2), rs.getString(3), rs.getDate(4));
        } else {
            return new User(0);
        } }
}
