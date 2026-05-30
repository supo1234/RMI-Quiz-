import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Quiz extends Remote {
    String[] getQuestion(int index) throws RemoteException;

    boolean submitAnswer(int index, int selectedAnswer) throws RemoteException;

    int getScore() throws RemoteException;

    int getTotalQuestions() throws RemoteException;
}