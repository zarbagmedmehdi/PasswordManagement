package ServiceInterface;

import Bean.Password;

import java.rmi.Remote;

public interface PasswordServiceInterface extends Remote {


     int ajouterPassword(Password password) throws Exception;
}
