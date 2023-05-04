package src;

public class Main {
    public static void main(String[] args){
        // Client client = new Client(args);
        // client.runClient();

        // Create server
        Server server = new Server();
        server.start(args);

    }
}
