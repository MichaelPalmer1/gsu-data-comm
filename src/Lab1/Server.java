package Lab1;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static final int DEFAULT_PORT = 8080;

    public static void main(String[] args) throws Exception {
        int port = DEFAULT_PORT;
        if (args.length >= 1) {
            port = Integer.parseInt(args[0]);
        }

        // Bind to the port
        ServerSocket server = new ServerSocket(port);
        System.out.printf("Server listening on %s:%d\n",
                server.getInetAddress().getHostAddress(), server.getLocalPort());

        // Keep listening for connections
        while (true) {
            // Got a connection
            Socket client = server.accept();
            System.out.printf("Received connection from %s:%d\n",
                    client.getInetAddress().getHostAddress(),
                    client.getPort());

            // Create input stream reader
            BufferedReader input = new BufferedReader(new InputStreamReader(client.getInputStream()));
            String line;

            // Read the message from the client, output it, and send it back
            PrintWriter output = new PrintWriter(client.getOutputStream(), true);
            while ((line = input.readLine()) != null) {
                System.out.printf("Message: %s\n", line);
                output.println(line);
            }

            // Close connections
            output.close();
            input.close();
            client.close();
        }
    }
}
