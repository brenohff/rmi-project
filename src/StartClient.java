
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

public class StartClient implements Constantes {

	private Client user;
	private static StartClient chat;
	private HashMap<String, ImplClient> cc;

	public StartClient(Client user) {
		this.user = user;
	}

	public static void main(String args[]) throws Exception {
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		ImplServer b = conectar("chat");

		System.out.print("Insira seu usuario: ");
		String nomeUsuario = input.readLine();
		if (b.podeLogar(nomeUsuario)) {
			chat = new StartClient(new Client(nomeUsuario));
			chat.login(chat, b);
			b.postMessage(chat.user.getNomeUsuario() + " entrou no chat/entrou", chat.user);
		} else {
			System.out.println("Encerrando sessao... (ja existe um usuario com este nome na sessao)");
			System.exit(1);
		}

		while (true) {
			String mensagem = input.readLine();

			if (mensagem != null) {
				switch (mensagem) {
				case "/menu":
					chat.mostraMenu();
					break;
				case "/chatPublico":
					chat.login(chat, b);
					break;
				case "/chatPrivado":
					chat.usuariosOnline(b);
					break;
				case "/enviaArquivo":
					chat.enviaArquivo(b);
					break;
				case "/sair":
					b.postMessage(chat.user.getNomeUsuario() + " saiu do chat/deixou", chat.user);
					b.logout(chat.user.getNomeUsuario());
					input.close();
					System.exit(1);
					break;
				default:
					b.postMessage(mensagem, chat.user);
					break;
				}
			}
		}
	}

	private static ImplServer conectar(String endpoint)
			throws MalformedURLException, RemoteException, NotBoundException {
		return (ImplServer) Naming.lookup(ENDPOINT + endpoint);
	}

	private void login(StartClient chat, ImplServer b) throws RemoteException {
		b.login(chat.user.getNomeUsuario(), chat.user);
	}

	private void mostraMenu() {
		System.out.println("||--------------------------------------||");
		System.out.println("||/menu        -> Mostra menu           ||");
		System.out.println("||/chatPublico -> Inicia o chat publico ||");
		System.out.println("||/chatPrivado -> Inicia o chat privado ||");
		System.out.println("||/sair        -> Sai do programa       ||");
		System.out.println("||--------------------------------------||");
	}

	private void usuariosOnline(ImplServer b) throws RemoteException {
		cc = b.getCC();
		List<ImplClient> clients = new ArrayList<>();
		int count = 1;
		for (Entry<String, ImplClient> usuario : cc.entrySet()) {
			clients.add(usuario.getValue());
			System.out.println(count + " - " + usuario.getKey());
			count++;
		}
	}

	private void enviaArquivo(ImplServer b) throws RemoteException {
		user.setNomeArquivo("imed");
		b.login(user);
	}
}