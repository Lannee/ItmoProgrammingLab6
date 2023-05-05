package src.commands;

import module.commands.CommandDescription;
import module.connection.requestModule.Request;
import module.connection.requestModule.RequestFactory;
import module.connection.requestModule.TypeOfRequest;
import module.connection.responseModule.CommandResponse;
import module.connection.responseModule.Response;
import module.logic.exceptions.InvalidResponseException;
import src.logic.connection.Connection;

import java.util.Arrays;
import java.util.Optional;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

public class Invoker {

    private final CommandsHandler commands;

    private Connection connection;

    private static final Pattern ARG_PAT = Pattern.compile("\"[^\"]+\"|\\S+");

    public Invoker(Connection connection) {
        this.connection = connection;
        commands = new CommandsHandler(connection);
        try {
            commands.initializeCommands();
        } catch (InvalidResponseException e) {
            // log must be here
        }
    }

    public String parseCommand(String line) {
        line = line.trim();
        if(line.equals("")) return "";

        String[] words = line.split(" ", 2);

        String command = words[0].toLowerCase();
        String[] args;
        if(words.length == 1)
            args = new String[0];
        else
            args = parseArgs(words[1]);

        CommandDescription commandDescription = commands.getCommandDescription(command);
        if(commandDescription != null) {
            Request request = RequestFactory.createRequest(command, args, TypeOfRequest.COMMAND);
//            connection.sendRequest(request);
//            if(commandDescription.isCreatingObject()) {
//
//            }
            Response response = connection.sendRequestGetResponse(request);
            if(response instanceof CommandResponse commandResponse) {
                return commandResponse.getResponse();
            } else {
                return "Error";
            }
        } else {
            return "Unknown command " + command + ". Type help to get information about all commands.\n";
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
