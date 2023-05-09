package src;

import module.connection.DatagramConnection;
import module.connection.IConnection;
import module.connection.requestModule.Request;
import module.connection.responseModule.*;
import src.commands.Invoker;
import src.logic.data.Receiver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.UnknownHostException;

public class Server {
    private final static int SERVER_PORT = 50689;
    private String fileName = "";
    private boolean running = true;

    private IConnection connection;

    private Invoker invoker;
    private static final Logger logger = LoggerFactory.getLogger(Server.class);
//    Command for spectate of working logger
//    LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();

    public String getFileName() {
        return fileName;
    }

    public boolean isRunning() {
        return running;
    }

    public void start(String[] args) {
//        Printing spectator of logger
//        StatusPrinter.print(lc);

        logger.info("Starting server.");
//        String filePath = getFilePath(args);
        String filePath = getFilePath(new String[]{"FileJ"});

        invoker = new Invoker(
                new Receiver(filePath));
        logger.info("Invoker and Receiver started.");

        try {
            connection = new DatagramConnection(SERVER_PORT, true);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }

        while(running) {
            Request request = (Request) connection.receive();
            System.out.println(request);
            Response response = null;
            switch (request.getTypeOfRequest()) {
                case COMMAND, CONFIRMATION -> {
                    response = new CommandResponse(invoker.parseRequest(request));
                }
                case INITIALIZATION -> {
                    response = new CommandsDescriptionResponse(invoker.getCommandsDescriptions());
                }
            }

            logger.info("Response Obj created.");
            connection.send(response);
            logger.info("Response sent.");
        }
    }

    public static String getFilePath(String[] args) {
//        return "base.csv";

        if (args.length == 0) {
            logger.error("Incorrect number of arguments.");
            System.exit(2);
        }
        String filePath = System.getenv().get(args[0]);
        if (filePath == null) {
            logger.error("Environment variable \"" + args[0] + "\" does not exist.");
            System.exit(1);
        }
        return filePath;
    }
}
