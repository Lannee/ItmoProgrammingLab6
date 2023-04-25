package src;

import java.net.UnknownHostException;

import src.logic.connection.Connection;
import src.utils.requestModule.RequestFactory;
import src.utils.requestModule.TypeOfRequest;

public class Main {
    public static void main(String[] args) throws UnknownHostException {
        // Client client = new Client(args);
        // client.runClient();

        Connection connection = new Connection("localhost", 8448);
        connection.sendRequest(RequestFactory.createRequest("info", "", TypeOfRequest.COMMAND));
    }
}
