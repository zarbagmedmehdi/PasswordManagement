package ServiceInterface;

import Bean.Note;
import Bean.QuestionReponse;

import java.rmi.Remote;
import java.util.ArrayList;

public interface QuestionServiceInterface extends Remote {

   public int ajouterQuestion(QuestionReponse questionReponse)  throws Exception;

  public ArrayList<QuestionReponse> getAllQuesion(int id)  throws  Exception;


}
