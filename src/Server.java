
import java.io.File;
import java.io.FileInputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class Server extends UnicastRemoteObject implements ImplServer {
	private static final long serialVersionUID = -5078513634955730630L;

	private HashMap<String, ImplClient> cc = new HashMap<String, ImplClient>();
	private String arquivo = "";

	public Server() throws RemoteException {
	}

	@Override
	public void postMessage(String msg, ImplClient userWhoSent) throws RemoteException {
		Collection<ImplClient> usersOnline = cc.values();
		Iterator<ImplClient> iter = usersOnline.iterator();

		while (iter.hasNext()) {
			ImplClient icc = (ImplClient) iter.next();
			if (!userWhoSent.equals(cc.get(icc.getNomeUsuario()))) {
				if (msg.endsWith("saiu do chat/deixou") || msg.endsWith("entrou no chat/entrou")) {
					icc.viewBoardMsg(msg.substring(0, msg.length() - 7));
				} else {
					icc.viewBoardMsg(userWhoSent.getNomeUsuario() + " disse: " + msg);
				}
			}
		}
	}

	@Override
	public void login(String nomeUsuario, ImplClient c) throws RemoteException {
		cc.put(nomeUsuario, c);
		System.out.println(nomeUsuario + " entrou - Usuarios online: " + cc.size());

	}

	@Override
	public void logout(String nomeUsuario) throws RemoteException {
		cc.remove(nomeUsuario);
		System.out.println(nomeUsuario + " saiu - Usuarios online: " + cc.size());
	}

	@Override
	public boolean podeLogar(String nomeUsuario) {
		if (!cc.containsKey(nomeUsuario)) {
			return true;
		} else {
			System.out.printf("Ja existe um usuario com o nome de \"%s\" na sessao.", nomeUsuario);
		}
		return false;
	}

	@Override
	public HashMap<String, ImplClient> getCC() throws RemoteException {
		return cc;
	}

	@Override
	@SuppressWarnings("resource")
	public boolean login(ImplClient c) throws RemoteException {
		try {
			File arq = new File(arquivo);
			FileInputStream in = new FileInputStream(arq);
			byte[] mydata = new byte[1024 * 1024];
			int mylen = in.read(mydata);
			while (mylen > 0) {
				c.enviaArquivo(arq.getName(), mydata, mylen);
				mylen = in.read(mydata);
			}
		} catch (Exception e) {
			e.printStackTrace();

		}

		return true;
	}

	public void setArquivo(String arquivo) {
		this.arquivo = arquivo;
	}

}