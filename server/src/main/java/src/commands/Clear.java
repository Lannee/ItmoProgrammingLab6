package src.commands;

import module.commands.CommandArgument;
import module.commands.CommandType;
import src.logic.data.Receiver;

/**
 * Clears the collection
 */
public class Clear implements Command {
    private final static CommandArgument[] args = new CommandArgument[0];
    public final static CommandType commandType = CommandType.NON_ARGUMENT_COMMAND;

    private final Receiver receiver;

    public Clear(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public String execute(Object[] args) {
        checkArgsConformity(args);
        receiver.clear();
        return "Successfully\n";
    }

    @Override
    public CommandArgument[] args() {
        return args;
    }

    @Override
    public String getDescription() {
        return "Clears the collection";
    }

    @Override
    public CommandType getCommandType() {
        return commandType;
    }
}