package src.utils.requestModule;

import java.io.Serializable;

public class Request implements Serializable {
    private static final long serialVersionUID = 6529685098267757690L;
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

    @Override
    public String toString() {
        return "Request [commandName=" + commandName + ", argumentsToCommand=" + argumentsToCommand + ", typeOfRequest="
                + typeOfRequest + "]";
    }
}
