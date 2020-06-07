package ServiceInterface;

import Bean.Password;

import java.rmi.Remote;
import java.util.ArrayList;

public interface PasswordServiceInterface extends Remote {


     int ajouterPassword(Password password) throws Exception;
     public ArrayList<Password> getAllPasswords(int id) throws Exception ;
     public int deletePassword(Password password) throws Exception ;
     public int update(Password password) throws Exception ;

     public ArrayList<Password> getPasswordByCriteria(int id,String site,String login) throws Exception ;

     }
