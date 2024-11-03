package telran.net;

import java.net.*;
import java.io.*;

public class Main {
    private static final int PORT = 4000;

    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(PORT);

        while (true) {
            Socket socket = serverSocket.accept();
            runSession(socket);
        }
    }

    private static void runSession(Socket socket) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintStream writer = new PrintStream(socket.getOutputStream())) {
            String request;
            while ((request = reader.readLine()) != null) {
                String [] requestParts = request.split("#");
                String receivedString = requestParts[0];
                String operationType = requestParts[1];
                String responseString;
                switch (operationType) {
                    case "length":
                        responseString = String.valueOf(receivedString.length());
                        break;
                    case "reverse":
                        responseString = new StringBuilder(receivedString).reverse().toString();
                        break;
                    default:
                        responseString = "No such operation";
                        break;
                }
                writer.println(responseString);
            }
        } catch (Exception e) {
            System.out.println("Client closed connection abnormally");
        }
    }
}