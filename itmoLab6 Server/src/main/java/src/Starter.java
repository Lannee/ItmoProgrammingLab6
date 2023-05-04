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
        final String LOCAL_FILE_STORAGE_VAR_NAME = "FILE_NAME";
        final int SERVERS_PORT = 8449;

        // Collection storage && server logger initialization 
        if (args.length == 0) {
            System.out.print("Incorrect number of arguments. Type system enviroment variable name\n");
            System.exit(2);
        }
        String filePath = System.getenv().get(LOCAL_FILE_STORAGE_VAR_NAME); // Name of local storage file
        if (filePath == null) {
            System.out.print("Environment variable for local storage \"" + fileName + "\" does not exist\n");
            System.exit(1);
        }
        String logger_storage_fileName = args[0];
        String logger_filePath = System.getenv().get(logger_storage_fileName); // Name of local storage file
        if (filePath == null) {
            System.out.print("Environment variable for local storage \"" + logger_filePath + "\" does not exist\n");
            System.exit(1);
        }

        // Invoker initialization
        Invoker invoker = new Invoker(
                new Receiver(filePath));

        // Logger initialization
        
        
        Connection connectionWorker = new Connection(SERVERS_PORT);
        while (running) {
            Request request = (Request) connectionWorker.catchRequest();   
            Response resultResponse = ResponseFactory.createResponse(invoker.parseRequestCommand(request), ResponseStatus.SUCCESSFULLY);

            // Need to create an algorythm that devide response data to some count bytes and send them to client.
            connectionWorker.sendResponse(resultResponse);
            
            if (request.getCommandName().equals("stop")) {
                running = false;
                continue;
            }
        }

    }
}
