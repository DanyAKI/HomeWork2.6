import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private Scanner scanner;


    public Client() {
        try {
            this.socket = new Socket("127.0.0.1", 8080);
            this.scanner = new Scanner(System.in);
            start();
            in = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void start() throws IOException {
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());


        new Thread(() -> {
            while (true) {

                try {
                    String message = in.readUTF();
                    System.out.println("Cообщение с сервера: " + message);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        })
                .start();

        while (true) {
            System.out.println("Введите сообщение: ");
            String outboundMessage = scanner.nextLine();
            out.writeUTF(outboundMessage);
        }


    }
}
