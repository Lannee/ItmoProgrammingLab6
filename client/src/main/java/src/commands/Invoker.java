package src.commands;

import module.commands.CommandDescription;
import module.commands.CommandType;
import module.connection.IConnection;
import module.connection.requestModule.Request;
import module.connection.requestModule.RequestFactory;
import module.connection.requestModule.TypeOfRequest;
import module.connection.responseModule.CommandResponse;
import module.connection.responseModule.Response;
import module.logic.exceptions.CannotCreateObjectException;
import module.logic.exceptions.InvalidResponseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import src.logic.callers.ArgumentCaller;
import src.logic.callers.BaseCaller;
import src.logic.callers.Callable;

import java.util.regex.MatchResult;
import java.util.regex.Pattern;

public class Invoker {

    private final CommandsHandler commands;

    private IConnection connection;

    private static final Pattern ARG_PAT = Pattern.compile("\"[^\"]+\"|\\S+");

    private static final Logger logger = LoggerFactory.getLogger(Invoker.class);

    private Callable caller;

    public Invoker(IConnection connection) {
        this.connection = connection;
        commands = new CommandsHandler(connection);
        try {
            commands.initializeCommands();
        } catch (InvalidResponseException e) {
            logger.error(e.getMessage());
        }
        logger.info("Invoker initialized.");
    }

    public String parseCommand(String line) {
        line = line.trim();
        caller = new ArgumentCaller();
        caller.handleCommand(line);
        String command = caller.getCommand();
        String[] args = caller.getArguments();
        return validateCommand(command, args);
    }

    public String validateCommand(String commandName, String[] args) {
        CommandDescription commandDescription = commands.getCommandDescription(commandName);
        if(commandDescription != null) {
            return formRequestAndGetResponse(commandName, args, commandDescription);
        } else {
            logger.error("Unknown command '{}'. Type help to get information about all commands.", commandName);
            return "Unknown command " + commandName + ". Type help to get information about all commands.\n";
        }
    }

//    public String formRequestAndGetResponse (String commandName, String[] args, CommandDescription commandDescription) {
//
//        Request request = RequestFactory.createRequest(commandName, args, TypeOfRequest.COMMAND);
////            if(commandDescription.isCreatingObject()) {
////                caller.getObjectArgument();
////            }
////        Response response = connection.sendRequestGetResponse(request);
//        connection.send(request);
//        Response response = (Response) connection.receive();
//        if(response instanceof CommandResponse commandResponse) {
//            return commandResponse.getResponse();
//        } else {
//            logger.error("Error with cached response.");
//            return "Error";
//        }
//    }

    public String formRequestAndGetResponse(String commandName, String[] args, CommandDescription commandDescription) {
        Request request;
        switch (commandDescription.getCommandType()) {
            case OBJECT_ARGUMENT_COMMAND:
                try {
                    caller.getObjectArgument();
                    CommandResponse response = sendRequestAndGetResponse(RequestFactory.createRequest(commandName, args, TypeOfRequest.COMMAND));
                    logger.info("Response with message '{}' received", response.getResponse());
                } catch (CannotCreateObjectException e) {
                    logger.error("Cannot create response");
                    return "Error";
                }
                break;
            case LINE_AND_OBJECT_ARGUMENT_COMMAND:
                try {
                    caller.getObjectArgument();
                    CommandResponse response = sendRequestAndGetResponse(RequestFactory.createRequest(commandName, args, TypeOfRequest.COMMAND));
                    logger.info("Response with message '{}'  received", response.getResponse());
                    // if () { Some logic to recognise was request correct, to form new request and send to server and ret new request }
                } catch (CannotCreateObjectException e) {
                    logger.error("Cannot create response");
                    return "Error";
                }
                break;
            default:
                CommandResponse response = sendRequestAndGetResponse(RequestFactory.createRequest(commandName, args, TypeOfRequest.COMMAND));
                return response.getResponse();
        }
        logger.error("Response didn't receive");
        return "Error";
    }

    public CommandResponse sendRequestAndGetResponse(Request request) {
        connection.send(request);
        Response response = (Response) connection.receive();
        if (response instanceof CommandResponse commandResponse) {
            return commandResponse;
        }
        return null;
    }


    private String[] parseArgs(String line) {
        return ARG_PAT.matcher(line)
                .results()
                .map(MatchResult::group)
                .map(e -> e.replaceAll("\"", ""))
                .toArray(String[]::new);
    }
}
