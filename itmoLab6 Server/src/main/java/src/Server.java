package src;

import module.commands.CommandDescription;
import module.responses.CommandResponse;
import module.responses.CommandsDescriptionResponse;
import module.responses.Response;
import src.commands.Invoker;
import src.logic.connection.Connection;
import src.logic.data.Receiver;
import src.utils.requestModule.Request;
import src.utils.responseModule.ResponseFactory;
import src.utils.responseModule.ResponseStatus;

import java.util.LinkedList;
import java.util.List;

public class Server {
    private final static int SERVER_PORT = 50689;
    private String fileName = "";
    private boolean running = true;

    private Invoker invoker;

    public String getFileName() {
        return fileName;
    }

    public boolean isRunning() {
        return running;
    }

    public void start(String[] args) {
//        String filePath = getFilePath(args);
        String filePath = getFilePath(new String[]{"FileJ"});

        invoker = new Invoker(
                new Receiver(filePath));

        Connection connection = new Connection(SERVER_PORT);

        while(true) {
            Request request = connection.catchRequest();
            Response response = null;
            switch (request.getTypeOfRequest()) {
                case COMMAND -> {
                    response = new CommandResponse("Все плохо, мне пришлось это переписывать"); // заменить на составление нормального запроса
                }
                case INITIALIZATION -> {
                    response = new CommandsDescriptionResponse(invoker.getCommandsDescriptions());
                }
            }

            System.out.println(response);
            connection.sendResponse(response);
        }

        // while (running) {
//            Request request = (Request) connection.catchRequest();
//            Response resultResponse = ResponseFactory.createResponse(invoker.parseRequestCommand(request), ResponseStatus.SUCCESSFULLY);
//            connection.sendResponse(resultResponse);
            
            // break;
            // if (request.getCommandName().equals("exit")) {
            //     running = false;
            //     continue;
            // }
        // }

    }

    public static String getFilePath(String[] args) {
        if (args.length == 0) {
            System.out.print("Incorrect number of arguments\n");
            System.exit(2);
        }
        String filePath = System.getenv().get(args[0]);
        if (filePath == null) {
            System.out.print("Environment variable \"" + args[0] + "\" does not exist\n");
            System.exit(1);
        }
        return filePath;
    }
}
