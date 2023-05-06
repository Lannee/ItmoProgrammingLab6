package src.commands;

import module.commands.CommandDescription;
import module.connection.requestModule.Request;
import module.connection.requestModule.RequestFactory;
import module.connection.requestModule.TypeOfRequest;
import module.connection.responseModule.CommandResponse;
import module.connection.responseModule.Response;
import module.logic.exceptions.InvalidResponseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import src.logic.connection.Connection;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

public class Invoker {

    private final CommandsHandler commands;

    private Connection connection;

    private static final Pattern ARG_PAT = Pattern.compile("\"[^\"]+\"|\\S+");

    private static final Logger logger = LoggerFactory.getLogger(Invoker.class);

    public Invoker(Connection connection) {
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

//        Needed to create separate module that parse typed line
        line = line.trim();
        if(line.equals("")) return "";

        String[] words = line.split(" ", 2);

        String command = words[0].toLowerCase();
        String[] args;
        if(words.length == 1)
            args = new String[0];
        else
            args = parseArgs(words[1]);
        return validateCommand(command, args);
    }

    public String validateCommand(String commandName, String[] args) {
        CommandDescription commandDescription = commands.getCommandDescription(commandName);
        if(commandDescription != null) {
            return formRequestAndGetResponse(commandName, args);
        } else {
            logger.error("Unknown command '{}'. Type help to get information about all commands.", commandName);
            return "Unknown command " + commandName + ". Type help to get information about all commands.\n";
        }
    }

    public String formRequestAndGetResponse (String commandName, String[] args) {
        Request request = RequestFactory.createRequest(commandName, args, TypeOfRequest.COMMAND);
//            if(commandDescription.isCreatingObject()) {
//            }
        Response response = connection.sendRequestGetResponse(request);
        if(response instanceof CommandResponse commandResponse) {
            return commandResponse.getResponse();
        } else {
            logger.error("Error with cached response.");
            return "Error";
        }
    }


    private String[] parseArgs(String line) {
        return ARG_PAT.matcher(line)
                .results()
                .map(MatchResult::group)
                .map(e -> e.replaceAll("\"", ""))
                .toArray(String[]::new);
    }
}
