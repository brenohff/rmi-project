import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IBoard extends Remote {

	public void postMessage(String msg, IChatClient client) throws RemoteException;

	public void login(String nomeUsuario, IChatClient c) throws RemoteException;

	public void logout(String nomeUsuario) throws RemoteException;
	
	public boolean podeLogar(String nomeUsuario) throws RemoteException;
}