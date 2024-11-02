package telran.net;

import java.util.Arrays;
import java.util.HashSet;

import telran.view.*;

public class Main {
    final static HashSet<String> operationsSet = new HashSet<String>(Arrays.asList("reverse", "length"));
    static EchoClient echoClient;

    public static void main(String[] args) {

        Item[] items = {
                Item.of("Get started!", Main::startSession),
                Item.of("Exit", Main::exit, true)
        };
        Menu menu = new Menu("Take a Length or Reverse the String Application", items);
        menu.perform(new StandardInputOutput());
    }

    static void startSession(InputOutput io) {
        String host = io.readString("Enter hostname");
        int port = io.readNumberRange("Enter port", "Wrong port", 3000, 50000).intValue();
        if (echoClient != null) {
            echoClient.close();
        }
        echoClient = new EchoClient(host, port);
        String string = io.readString("Enter any string");
        String operationType = io.readStringOptions("Chose operation from list:" + operationsSet.toString(), "Wrong operation", operationsSet);
        String response = echoClient.sendAndReceive(string, operationType);
    
        io.writeLine("\nConnected to " + host + ":" + port);
        io.writeLine("Sent: " + string);
        io.writeLine("Operation:" + operationType);
        io.writeLine("Received response: " + response);
    }
    static void exit(InputOutput io) {
        if(echoClient != null) {
            echoClient.close();
        }
    }
}