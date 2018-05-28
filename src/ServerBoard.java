import java.rmi.Naming;

public class ServerBoard {

	public ServerBoard() {

	}

	public static void main(String args[]) {
		try {
			Board b = new Board();
			Naming.rebind("chat", b);
		} catch (Exception e) {
			System.out.println("Erro ao iniciar servidor.");
			System.out.println(e.getMessage());
			return;
		}
		System.out.println("Servidor pronto...");
	}
}