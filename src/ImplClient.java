import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ImplClient extends Remote {

	public void viewBoardMsg(String msg) throws RemoteException;

	public String getNomeUsuario() throws RemoteException;

	public boolean enviaArquivo(String filename, byte[] data, int len) throws RemoteException;
}