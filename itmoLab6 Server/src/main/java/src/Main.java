package src;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import src.commands.Invoker;
import src.logic.connection.ResponseSender;
import src.logic.data.Receiver;
import src.utils.requestModule.Request;
import src.utils.responseModule.ResponseFactory;
import src.utils.responseModule.ResponseStatus;

public class Main {
    public static void main(String[] args) throws UnknownHostException, ClassNotFoundException {
        // Client client = new Client(args);
        // client.runClient();

        // Create server starter
        Starter serverStarter = new Starter();
        serverStarter.startServer(args);        

        // ResponseSender responseSender = new ResponseSender("localhost", 8448);
        // responseSender.sendResponse(ResponseFactory.createResponse("Succcc", ResponseStatus.SUCCESSFULLY));

    }
}
