package src.commands;

import module.commands.CommandDescription;
import module.responses.*;
import src.logic.connection.Connection;
import src.logic.exceptions.InvalidResponseException;
import src.utils.requestModule.Request;
import src.utils.requestModule.RequestFactory;
import src.utils.requestModule.TypeOfRequest;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CommandsHandler {
    private static List<CommandDescription> commands = new LinkedList<>();

    private final Connection connection;

    public CommandsHandler(Connection connection) {
        this.connection = connection;
    }

    public void initializeCommands() throws InvalidResponseException {
        Request request = RequestFactory.createRequest(TypeOfRequest.INITIALIZATION);
        Response response = connection.sendRequestGetResponse(request);
        if(!(response instanceof CommandsDescriptionResponse commandsDescriptionResponse)) throw new InvalidResponseException();
        commands = ((CommandsDescriptionResponse) response).getCommands();
        System.out.println(response);
    }
}
