package ServiceInterface;

import Bean.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;

public interface LoginServiceInterface  extends Remote   {

    int signUp(String login ,String password) throws RemoteException, SQLException;
    int login(String login, String password) throws RemoteException ,SQLException;
    User getUser(String login, String password) throws RemoteException , SQLException;

}
