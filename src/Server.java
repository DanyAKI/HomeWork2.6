import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    private ServerSocket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private Scanner scanner;

    public Server() {
        try {
            socket = new ServerSocket(8080);
            this.scanner = new Scanner(System.in);
            start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void start() throws IOException {
        System.out.println("Cервер запущен");
        System.out.println("Сервер ждёт подключения ...");
        Socket accepted = socket.accept();
        System.out.println("Клиент подключен");
        System.out.println("Address " + accepted.getInetAddress());

        in = new DataInputStream(accepted.getInputStream());
        out = new DataOutputStream(accepted.getOutputStream());

        new Thread(() -> {
            while (true) {
                System.out.println("Введите сообщение: ");
                String outboundMessage = scanner.nextLine();
                try {
                    out.writeUTF(outboundMessage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        })
                .start();


        while (true) {
            String message = in.readUTF();
            System.out.println("Сообщение от клиента: " + message);


        }


    }
}
