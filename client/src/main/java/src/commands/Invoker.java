package src.commands;

import module.commands.CommandArgument;
import module.commands.CommandDescription;
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
import src.logic.callers.Callable;
import src.utils.StringConverter;

import java.util.Arrays;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

public class Invoker {

    private final CommandsHandler commands;

    private final IConnection connection;

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
                    return "Invalid argument type\n";
                }
            }

            return formRequestAndGetResponse(commandName, parsedArguments, commandDescription);
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

    public String formRequestAndGetResponse(String commandName, Object[] args, CommandDescription commandDescription) {
        CommandResponse response;
        switch (commandDescription.getCommandType()) {
            case LINE_AND_OBJECT_ARGUMENT_COMMAND:
                response = sendRequestAndGetResponse(RequestFactory.createRequest(commandName, args, TypeOfRequest.CONFIRMATION));
                logger.info("Response with status '{}' received.", response.getResponseStatus());
                if (response.getResponseStatus() != ResponseStatus.WAITING) {
                    return response.getResponse();
                }
            case OBJECT_ARGUMENT_COMMAND:
                // Getting array of arguments of command
                CommandArgument[] objectArguments = Arrays.stream(commandDescription.getArguments()).
                        filter(e -> !e.isEnteredByUser()).
                        toArray(CommandArgument[]::new);

                for(CommandArgument objectArgument : objectArguments) {
                    try {
                        args = addArgument(new Object[] {}, caller.getObjectArgument(objectArgument.getArgumentType()));
                        response = sendRequestAndGetResponse(RequestFactory.createRequest(commandName, args, TypeOfRequest.CONFIRMATION));
                        logger.info("Response with status '{}' received.", response.getResponseStatus());
                        if (response.getResponseStatus() != ResponseStatus.WAITING) {
                            return response.getResponse();
                        }
                    } catch (CannotCreateObjectException e) {
                        logger.error("Cannot create object as argument to command with its type.");
                        return "Error with creating object as argument to command.\n";
                    }
                }
                break;
            default:
                // If command is NON_ARGUMENT or LINE_ARGUMENT
                response = sendRequestAndGetResponse(RequestFactory.createRequest(commandName, args, TypeOfRequest.COMMAND));
                logger.info("Response with status '{}' received.", response.getResponseStatus());
                return response.getResponse();
        }
        logger.error("Forming request and getting response were failed");
        return "Error in forming request and getting response\n";
    }

    public CommandResponse sendRequestAndGetResponse(Request request) {
        connection.send(request);
        Response response = (Response) connection.receive();
        if (response instanceof CommandResponse commandResponse) {
            return commandResponse;
        }
        return null;
    }

    public static Object[] addArgument(Object[] args, Object obj) {
        int newArraySize = args.length + 1;
        Object[] newArray = new Object[newArraySize];
        System.arraycopy(args, 0, newArray, 0, args.length);
        newArray[args.length] = obj;
        return newArray;
    }


    private String[] parseArgs(String line) {
        return ARG_PAT.matcher(line)
                .results()
                .map(MatchResult::group)
                .map(e -> e.replaceAll("\"", ""))
                .toArray(String[]::new);
    }
}
