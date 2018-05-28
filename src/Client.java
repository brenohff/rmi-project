
import java.io.File;
import java.io.FileOutputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Client extends UnicastRemoteObject implements ImplClient {
	private static final long serialVersionUID = 6322158345847138669L;

	private String nomeUsuario;
	private String nomeArquivo;

	public Client(String nomeUsuario) throws RemoteException {
		this.nomeUsuario = nomeUsuario;
	}

	@Override
	public String getNomeUsuario() throws RemoteException {
		return nomeUsuario;
	}

	@Override
	public void viewBoardMsg(String msg) throws RemoteException {
		System.out.println(msg);
	}

	@Override
	public boolean enviaArquivo(String filename, byte[] data, int len) throws RemoteException {
		try {
			File arquivo = new File(filename);
			arquivo.createNewFile();
			FileOutputStream out = new FileOutputStream(arquivo, true);
			out.write(data, 0, len);
			out.flush();
			out.close();
			System.out.println("Done writing data...");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public String getNomeArquivo() throws RemoteException {
		return nomeArquivo;
	}

	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}

}