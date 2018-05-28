
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class ChatUI implements Constantes {

	private ChatClient user;
	private static ChatUI chat;

	public ChatUI(ChatClient user) {
		this.user = user;
	}

	public static void main(String args[]) throws Exception {
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		IBoard b = conectar("chat");

		System.out.print("Insira seu usuario: ");
		String nomeUsuario = input.readLine();
		if (b.podeLogar(nomeUsuario)) {
			chat = new ChatUI(new ChatClient(nomeUsuario));
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

	private static IBoard conectar(String endpoint) throws MalformedURLException, RemoteException, NotBoundException {
		return (IBoard) Naming.lookup(ENDPOINT + endpoint);
	}

	private void login(ChatUI chat, IBoard b) throws RemoteException {
		b.login(chat.user.getNomeUsuario(), chat.user);
	}

	private void mostraMenu() {
		System.out.println("||--------------------------------------||");
		System.out.println("||/menu -> Mostra menu                  ||");
		System.out.println("||/chatPublico -> Inicia o chat publico ||");
		System.out.println("||/chatPrivado -> Inicia o chat privado ||");
		System.out.println("||/sair -> Sai do programa              ||");
		System.out.println("||--------------------------------------||");
	}
}