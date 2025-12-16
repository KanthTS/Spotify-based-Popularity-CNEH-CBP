import java.io.*;
import java.net.*;
import java.util.*;

public class Server {

    // Store connected clients
    static Map<Socket, String> clients = new HashMap<>();

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8888);
            System.out.println("Server started... Waiting for clients");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                ClientHandler handler = new ClientHandler(clientSocket);
                handler.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Thread to handle each client
    static class ClientHandler extends Thread {
        Socket socket;
        BufferedReader in;
        PrintWriter out;
        String username;

        ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                // Receive username
                username = in.readLine();
                clients.put(socket, username);

                System.out.println("[CONNECTED] " + username + " : " + socket);

                Scanner sc = new Scanner(System.in);

                while (true) {
                    String message = in.readLine();
                    if (message == null) break;

                    System.out.println(username + ": " + message);

                    // Server reply
                    System.out.print("Server reply: ");
                    String reply = sc.nextLine();
                    out.println(reply);
                }

            } catch (IOException e) {
                System.out.println("[ERROR] Client disconnected");
            } finally {
                try {
                    System.out.println("[DISCONNECTED] " + username);
                    clients.remove(socket);
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
