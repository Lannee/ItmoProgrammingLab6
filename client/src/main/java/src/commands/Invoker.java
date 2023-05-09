package src.commands;

import module.commands.CommandArgument;
import module.commands.CommandDescription;
import module.commands.CommandType;
import module.connection.IConnection;
import module.connection.requestModule.Request;
import module.connection.requestModule.RequestFactory;
import module.connection.requestModule.TypeOfRequest;
import module.connection.responseModule.CommandResponse;
import module.connection.responseModule.Response;
import module.connection.responseModule.ResponseStatus;
import module.logic.exceptions.CannotCreateObjectException;
import module.logic.exceptions.InvalidResponseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import src.logic.callers.ArgumentCaller;
import src.logic.callers.BaseCaller;
import src.logic.callers.Callable;
import src.utils.StringConverter;

import java.util.Arrays;
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
            CommandArgument[] arguments  = commandDescription.getArguments();

            if(args.length != Arrays.stream(arguments).filter(CommandArgument::isEnteredByUser).count()) return "Invalid number of arguments";

            Object[] parsedArguments = new Object[args.length];

            for(int i = 0; i < arguments.length; i++) {
                CommandArgument argument = arguments[i];
                if(!argument.isEnteredByUser())
                    break;

                try {
                    parsedArguments[i] = StringConverter.methodForType.get(argument.getArgumentType()).apply(args[i]);
                } catch (NumberFormatException nfe) {
                    return "Invalid argument type";
                }
            }

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
            case OBJECT_ARGUMENT_COMMAND -> {
                try {
                    Class[] argumentClassArray = argumentClassSearcher(commandDescription.getArguments());
                    System.out.println(argumentClassArray);
                    int iterator = 0;
                    while (iterator < argumentClassArray.length && argumentClassArray[iterator] != null) {
                        caller.getObjectArgument(argumentClassArray.getClass()); // Necessary to add into args variable to send to the server correct arguments
                        CommandResponse response = sendRequestAndGetResponse(RequestFactory.createRequest(commandName, args, TypeOfRequest.COMMAND));
                        logger.info("Response with message '{}' and status '{}' received.", response.getResponse(), response.getResponseStatus());
                        if (response.getResponseStatus() == ResponseStatus.SUCCESSFULLY ) {
                            return response.getResponse();
                        }
                        if (response.getResponseStatus() == ResponseStatus.FAILED) {
                            break;
                        }
                        iterator++;
                    }
                    logger.error("Failed. Server rejected your typed values into objects.");
                } catch (CannotCreateObjectException e) {
                    logger.error("Cannot create response");
                    return "Error";
                }
            }
            case LINE_AND_OBJECT_ARGUMENT_COMMAND -> {
                try {
                    CommandResponse response = sendRequestAndGetResponse(RequestFactory.createRequest(commandName, args, TypeOfRequest.CONFIRMATION));
                    if (response.getResponse().equals("Good")) {
                        caller.getObjectArgument(commandDescription.getArguments()[0].getArgumentType());
                        response = sendRequestAndGetResponse(RequestFactory.createRequest(commandName, args, TypeOfRequest.COMMAND));
                        logger.info("Response with message '{}'  received", response.getResponse());
                    }
                    // if () { Some logic to recognise was request correct, to form new request and send to server and ret new request }
                } catch (CannotCreateObjectException e) {
                    logger.error("Cannot create response");
                    return "Error";
                }
            }
            default -> {
                CommandResponse response = sendRequestAndGetResponse(RequestFactory.createRequest(commandName, args, TypeOfRequest.COMMAND));
                return response.getResponse();
            }
        }
        logger.error("Response didn't receive");
        return "Error";
    }

    // Method argumentClassSearcher return array of classes of each argument from CommandDescription
    public Class[] argumentClassSearcher(CommandArgument[] commandArgumentsArray) {
        Class[] argumentClassArray = new Class[commandArgumentsArray.length];
        int argumentClassIter = 0;
        for (int iter = 0; iter < commandArgumentsArray.length; iter++) {
            if (commandArgumentsArray[iter].isEnteredByUser()) {
                argumentClassArray[argumentClassIter] = commandArgumentsArray[iter].getArgumentType();
                argumentClassIter++;
            }
        }
        return argumentClassArray;
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
