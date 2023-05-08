package src.commands;

import module.commands.CommandArgument;
import module.commands.CommandType;
import src.logic.data.Receiver;

/**
 * Saves the collection to the file
 */
public class Save implements Command {
    private static final CommandArgument[] args = new CommandArgument[0];
    public final static CommandType commandType = CommandType.NON_ARGUMENT_COMMAND;

    private final Receiver receiver;

    public Save(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public String execute(String[] args) {
        checkArgsConformity(args);
        receiver.saveCollection();
        return "Successfully\n";
    }

    @Override
    public String getDescription() {
        return "Saves the collection to the file";
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