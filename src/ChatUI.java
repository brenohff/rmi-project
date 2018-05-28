
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.Naming;

public class ChatUI {

	private ChatClient user;

	public ChatUI(ChatClient user) {
		this.user = user;
	}

	public static void main(String args[]) throws Exception {
		// User keyboard input
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		IBoard b = (IBoard) Naming.lookup("rmi://192.168.7.170/chat");

		// Login
		System.out.print("Insira seu usuario: ");
		ChatUI chat = new ChatUI(new ChatClient(input.readLine()));
		b.login(chat.user.getAlias(), chat.user);
		System.out.println("Usuario " + chat.user.getAlias() + " entrou!");

		// Send each line users enter
		while (true) {
			String mensagem = input.readLine();
			if (mensagem.equalsIgnoreCase("/logout")) {
				b.logout(chat.user.getAlias());
				input.close();
				break;
			}
			b.postMessage(mensagem, chat.user);
		}
	}
}