package src;

import java.net.UnknownHostException;

import src.logic.connection.Connection;
import src.utils.requestModule.RequestFactory;
import src.utils.requestModule.TypeOfRequest;

public class Main {
    public static void main(String[] args) throws UnknownHostException {
         Client client = new Client();
         client.runClient();

//        Connection connection = new Connection("localhost", 8449);
//        connection.sendRequest(RequestFactory.createRequest("info", "", TypeOfRequest.COMMAND));
//        System.out.println(connection.catchResponse());
    }
}
