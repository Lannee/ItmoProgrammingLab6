package src;

import src.commands.Invoker;
import src.logic.connection.Connection;
import src.logic.data.Receiver;
import src.utils.requestModule.Request;
import src.utils.responseModule.Response;
import src.utils.responseModule.ResponseFactory;
import src.utils.responseModule.ResponseStatus;

public class Starter {
    private String fileName = "";
    private boolean running = true;

    public String getFileName() {
        return fileName;
    }

    public boolean isRunning() {
        return running;
    }

    public void startServer(String[] args) {
        // Collection storage loader from file
        if (args.length == 0) {
            System.out.print("Incorrect number of arguments\n");
            System.exit(2);
        }
        fileName = args[0];
        String filePath = System.getenv().get(fileName);
        if (filePath == null) {
            System.out.print("Environment variable \"" + fileName + "\" does not exist\n");
            System.exit(1);
        }

        Invoker invoker = new Invoker(
                new Receiver(filePath));

        Connection connectionWorker = new Connection(8449);
        while (running) {
            Request request = (Request) connectionWorker.catchRequest();
            Response resultResponse = ResponseFactory.createResponse(invoker.parseRequestCommand(request), ResponseStatus.SUCCESSFULLY);

            // Need to create an algorythm that devide response data to some count bytes and send them to client.
            connectionWorker.sendResponse(resultResponse);
            
            if (request.getCommandName().equals("exit")) {
                running = false;
                continue;
            }
        }

    }
}
