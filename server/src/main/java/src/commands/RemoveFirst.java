package src.commands;

import module.commands.CommandArgument;
import module.commands.CommandType;
import src.logic.data.Receiver;

/**
 * Removes the first element from the collection
 */
public class RemoveFirst implements Command {
    private final static CommandArgument[] args = new CommandArgument[0];
    public final static CommandType commandType = CommandType.NON_ARGUMENT_COMMAND;

    private final Receiver receiver;

    public RemoveFirst(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public String execute(String[] args) {
        checkArgsConformity(args);
        if(receiver.removeByIndex(0, false)) {
            return "First object was successfully removed\n";
        }
        return "";
    }

    @Override
    public String getDescription() {
        return "Removes the first element from the collection";
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