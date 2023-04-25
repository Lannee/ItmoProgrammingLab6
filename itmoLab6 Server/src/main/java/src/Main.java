package src;

import java.net.InetAddress;
import java.net.UnknownHostException;

import src.logic.connection.RequestCatcher;

public class Main {
    public static void main(String[] args) throws UnknownHostException {
        // Client client = new Client(args);
        // client.runClient();

        RequestCatcher requestCatcher = new RequestCatcher();
        requestCatcher.run();
    }
}
