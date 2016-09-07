package Lab1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private static final String DEFAULT_HOST = "localhost";
    private static final int DEFAULT_PORT = 8080;

    /**
     * Client
     *
     * Usage: java Client.class [host] [port]
     *
     * @param args Arguments
     *             host - Host to connect to. Defaults to 'localhost'.
     *             port - Port to connect on. Defaults to 8080.
     * @throws Exception Exception
     */
    public static void main(String[] args) throws Exception {
        String host = DEFAULT_HOST;
        int port = DEFAULT_PORT;
        String message;

        // Hostname
        if (args.length >= 1) {
            host = args[0];
        }

        // Port
        if (args.length >= 2) {
            port = Integer.parseInt(args[1]);
        }

        // Connect to the server
        Socket socket = new Socket(host, port);
        System.out.printf("Connected to %s:%d on port %d\n",
                socket.getInetAddress().getHostAddress(), socket.getPort(), socket.getLocalPort());

        // Input
        Scanner stdIn = new Scanner(System.in);
        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        // Output
        PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

        // Prompt for a message
        System.out.print("Send message (type 'exit' to quit): ");

        // Keep requesting a message until "exit" is entered
        while (!(message = stdIn.nextLine()).equals("exit")) {
            // Send the message
            output.println(message);
            System.out.println("Message sent");

            // Output the response
            System.out.printf("Message received: %s\n\n", input.readLine());

            // Prompt for a message
            System.out.print("Send message (type 'exit' to quit): ");
        }

        // Close connections
        stdIn.close();
        input.close();
        output.close();
        socket.close();
    }
}
