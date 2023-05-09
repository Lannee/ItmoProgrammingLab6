package src.commands;

import module.commands.CommandArgument;
import module.commands.CommandType;
import src.logic.data.Receiver;

/**
 * Prints all the elements of the collection in a string representation
 */
public class Show implements Command {
    private static final CommandArgument[] args = new CommandArgument[0];
    public final static CommandType commandType = CommandType.NON_ARGUMENT_COMMAND;

    private final Receiver receiver;

    public Show(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public String execute(Object[] args) {
        checkArgsConformity(args);
        return receiver.getFormattedCollection() + "\n";
    }

    @Override
    public String getDescription() {
        return "Prints all the elements of the collection in a string representation";
    }

    @Override
    public CommandArgument[] args() {
        return args;
    }

    @Override
    public CommandType getCommandType() {
        return commandType;
    }
}