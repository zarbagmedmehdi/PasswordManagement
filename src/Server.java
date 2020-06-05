import Bean.Password;
import Service.LoginService;
import Service.NoteService;
import Service.PasswordService;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;

public class Server {
    public static void main(String[] args) throws RemoteException, MalformedURLException {
      LoginThread lg=new LoginThread();
       PasswordThread ps=new PasswordThread();
        NoteThread ns=new NoteThread();

}}

class LoginThread extends Thread
{
    public LoginThread() {this.start();
    }


    @Override
    public void run() {
        try {
            serve(); } catch (RemoteException e) { e.printStackTrace(); } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
    public void serve() throws RemoteException, MalformedURLException {
        LoginService lg=new LoginService();
        LocateRegistry.createRegistry(1098);
        Naming.rebind("loginService",lg);
        System.out.println(
                "lobjet distant loginService est enregitsté dans XRmiExample ... serveur Pret"
        );
    }  }

    class PasswordThread extends Thread
    {
        public PasswordThread() {this.start();
        }

        @Override
        public void run() {
            try {
                serve(); } catch (RemoteException e) { e.printStackTrace(); } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        public void serve() throws RemoteException, MalformedURLException {
            PasswordService lg=new PasswordService();
            LocateRegistry.createRegistry(1099);
            Naming.rebind("passwordService",lg);
            System.out.println(
                    "lobjet distant PasswordService est enregitsté dans XRmiExample ... serveur Pret"
            );
        } }

class NoteThread extends Thread
{
    public NoteThread() {this.start();
    }

    @Override
    public void run() {
        try {
            serve(); } catch (RemoteException e) { e.printStackTrace(); } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
    public void serve() throws RemoteException, MalformedURLException {
        NoteService ns=new NoteService();
        LocateRegistry.createRegistry(1097);
        Naming.rebind("noteService",ns);
        System.out.println(
                "lobjet distant NoteService est enregitsté dans XRmiExample ... serveur Pret"
        );
    } }
