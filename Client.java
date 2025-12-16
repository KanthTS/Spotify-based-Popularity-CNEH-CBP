import java.io.*;
import java.net.*;
import java.util.*;

public class Client {

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("127.0.0.1", 8888);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(
                    socket.getOutputStream(), true);

            Scanner sc = new Scanner(System.in);

            // Enter username
            System.out.print("Enter username: ");
            String username = sc.nextLine();
            out.println(username);

            System.out.println("Connected to server");

            while (true) {
                System.out.print("Enter prediction message: ");
                String message = sc.nextLine();
                out.println(message);

                String response = in.readLine();
                System.out.println("Server: " + response);
            }

        } catch (IOException e) {
            System.out.println("Disconnected from server");
        }
    }
}

