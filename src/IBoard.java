import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IBoard extends Remote {

	public void postMessage(String msg, IChatClient client) throws RemoteException;

	public void login(String alias, IChatClient c) throws RemoteException;

	public void logout(String alias) throws RemoteException;
}