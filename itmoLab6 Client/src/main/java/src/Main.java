package src;

import java.net.InetAddress;
import java.net.UnknownHostException;

import src.logic.connection.Connection;

public class Main {
    public static void main(String[] args) throws UnknownHostException {
        // Client client = new Client(args);
        // client.runClient();

        Connection connection = new Connection("localhost", 8448);
        connection.sendMessage("Zhopa");
    }
}
