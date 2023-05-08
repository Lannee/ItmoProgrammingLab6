package module.commands;

import java.io.Serializable;

public class CommandArgument implements Serializable {

    private final static long serialVersionUID = 3381943064232312019L;

    private final String argumentName;
    private final Class<?> argumentType;
    private final CommandArgsType commandArgsType;
    private final boolean isCreatingObject;

    public CommandArgument(String argumentName, Class<?> argumentType, boolean isCreatingObject) {
        this(argumentName, argumentType, CommandArgsType.LINEARGUMENTCOMMAND, false);
    }

    public CommandArgument(String argumentName, Class<?> argumentType, CommandArgsType commandArgsType, boolean isCreatingObject) {
        this.argumentName = argumentName;
        this.argumentType = argumentType;
        this.commandArgsType = commandArgsType;
        this.isCreatingObject = isCreatingObject;
    }

    public String getArgumentName() {
        return argumentName;
    }

    public Class<?> getArgumentType() {
        return argumentType;
    }

    public CommandArgsType getCommandArgsType() {
        return commandArgsType;
    }

    @Override
    public String toString() {
        return argumentName;
    }

    public boolean isCreatingObject() {
        return isCreatingObject;
    }
}
