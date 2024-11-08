package telran.net;

import java.net.*;
import java.io.*;
import org.json.JSONObject;


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
                String response = getResponse(request);
                writer.println(response);
            }
        } catch (Exception e) {
            System.out.println("Client closed connection abnormally");
        }
    }
    private static String getResponse(String request) {
        JSONObject jsonObj = new JSONObject(request);
        String type = jsonObj.getString("type");
        String string = jsonObj.getString("string");
        String response = switch(type){
            case "reverse" -> new StringBuilder(string).reverse().toString();
            case "length" -> string.length() + "";
            default -> type + " Wrong type";
        };
        return response;
    }
}