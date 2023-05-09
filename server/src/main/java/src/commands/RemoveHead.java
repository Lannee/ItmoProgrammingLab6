package src.commands;

import module.commands.CommandArgument;
import module.commands.CommandType;
import src.logic.data.Receiver;

/**
 * Prints the first element of the collection and deletes it
 */
public class RemoveHead implements Command {
    private static final CommandArgument[] args = new CommandArgument[0];
    public final static CommandType commandType = CommandType.NON_ARGUMENT_COMMAND;

    private final Receiver receiver;

    public RemoveHead(Receiver receiver) {
        this.receiver = receiver;
    }

    // Needed to be fixed because function <removeOn> is called in reciever
    @Override
    public String execute(Object[] args) {
        checkArgsConformity(args);
        receiver.removeByIndex(0, true);
        return "";
    }

    @Override
    public String getDescription() {
        return "Prints the first element of the collection and deletes it";
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