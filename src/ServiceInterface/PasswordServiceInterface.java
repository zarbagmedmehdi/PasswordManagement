package ServiceInterface;

import Bean.Password;

import java.rmi.Remote;
import java.util.ArrayList;

public interface PasswordServiceInterface extends Remote {


     int ajouterPassword(Password password) throws Exception;
      ArrayList<Password> getAllPasswords(int id) throws Exception ;
      int deletePassword(Password password) throws Exception ;
      int update(Password password) throws Exception ;

      ArrayList<Password> getPasswordByCriteria(int id,String site,String login) throws Exception ;

     }
