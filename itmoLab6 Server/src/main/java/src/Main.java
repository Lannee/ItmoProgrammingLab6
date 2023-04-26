package src;

import java.net.InetAddress;
import java.net.UnknownHostException;

import src.commands.Invoker;
import src.logic.connection.RequestCatcher;
import src.logic.data.Receiver;

public class Main {
    public static void main(String[] args) throws UnknownHostException, ClassNotFoundException {
        // Client client = new Client(args);
        // client.runClient();

        RequestCatcher requestCatcher = new RequestCatcher();
        requestCatcher.run();

        // collection storage loader from file
        if(args.length == 0) {
            System.out.print("Incorrect number of arguments\n");
            System.exit(2);
        }
        String fileName = args[0];
        String filePath = System.getenv().get(fileName);
        if(filePath == null) {
            System.out.print("Environment variable \"" + fileName + "\" does not exist\n");
            System.exit(1);
        }

        Invoker invoker = new Invoker(
                new Receiver(filePath)
            );
        invoker.parseCommand();
    }
}
