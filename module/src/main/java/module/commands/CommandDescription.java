package module.commands;

import java.io.Serializable;
import java.util.Arrays;

public class CommandDescription implements Serializable {

    private final String commandName;
    private CommandArgument[] arguments;
    private final boolean isCreatingObject;

    public CommandDescription(String commandName, CommandArgument[] arguments, boolean isCreatingObject) {
        this.commandName = commandName;
        this.arguments = arguments;
        this.isCreatingObject = isCreatingObject;
    }

    public String getCommandName() {
        return commandName;
    }

    public CommandArgument[] getArguments() {
        return arguments;
    }

    public boolean isCreatingObject() {
        return isCreatingObject;
    }

    @Override
    public String toString() {
        return "CommandDescription{" +
                "commandName='" + commandName + '\'' +
                ", arguments=" + Arrays.toString(arguments) +
                ", isCreatingObject=" + isCreatingObject +
                '}';
    }
}

