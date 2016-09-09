/**
 * Michael Palmer
 * 900757121
 * CSCI 5332
 * 9 September 2016
 * Lab 1
 */
package Lab1;

import java.io.*;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static final int DEFAULT_PORT = 8080;

    /**
     * Server
     *
     * Usage: java Lab1.Server [port]
     *
     * @param args Arguments
     *             port - Port to listen on. Defaults to 8080.
     */
    public static void main(String[] args) {
        // Port
        int port = DEFAULT_PORT;
        if (args.length >= 1) {
            port = Integer.parseInt(args[0]);
        }

        try {
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
                    System.out.printf("Message received: %s\n", line);
                    output.println(line);
                    System.out.println("Sent message back to client");
                }

                // Close connections
                output.close();
                input.close();
                client.close();
            }
        } catch (BindException e) {
            System.out.printf("Error binding socket - %s\n", e.getMessage());
        } catch (IOException e) {
            System.out.printf("IO Exception - %s\n", e.getMessage());
        }
    }
}
