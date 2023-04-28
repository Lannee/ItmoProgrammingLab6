package src;

import src.commands.Invoker;
import src.logic.connection.RequestCatcher;
import src.logic.data.Receiver;
import src.utils.requestModule.Request;

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

        RequestCatcher requestCatcher = new RequestCatcher();
        while (running) {
            Request request = requestCatcher.run();
            invoker.parseCommand(request);
            if (request.getCommandName().equals("exit")) {
                running = false;
                continue;
            }
        }

    }
}
