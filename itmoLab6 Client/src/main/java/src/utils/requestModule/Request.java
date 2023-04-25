package src.utils.requestModule;

public class Request {
    private String commandName;
    private String argumentsToCommand;
    private TypeOfRequest typeOfRequest;

    public Request (String aCommandName, String anArgumentsToCommand, TypeOfRequest aTypeOfRequest) {
        commandName = aCommandName;
        argumentsToCommand = anArgumentsToCommand;
        typeOfRequest = aTypeOfRequest;
    }

    public String getCommandName() {
        return commandName;
    }

    public String getArgumentsToCommand() {
        return argumentsToCommand;
    }

    public TypeOfRequest getTypeOfRequest() {
        return typeOfRequest;
    }

}
