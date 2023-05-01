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

        Connection connectionWorker = new Connection("localhost", 8448);
        // while (running) {
            Request request = (Request) connectionWorker.catchRequest();
            System.out.println(request);
            Response resultResponse;
            resultResponse = ResponseFactory.createResponse("HELLOOO", ResponseStatus.SUCCESSFULLY);
            System.out.println(resultResponse);
            connectionWorker.sendResponse(resultResponse);
            // break;
            // if (request.getCommandName().equals("exit")) {
            //     running = false;
            //     continue;
            // }
        // }

    }
}
