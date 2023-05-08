package src.commands;

import module.commands.CommandArgument;
import src.logic.data.Receiver;

/**
 * Clears the collection
 */
public class Clear implements Command {
    private final static CommandArgument[] args = new CommandArgument[0];

    private final Receiver receiver;
    private final static boolean isCreatingObject = false;

    public Clear(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public String execute(String[] args) {
        checkArgsConformity(args);
        receiver.clear();
        return "Successfully\n";
    }

    @Override
    public CommandArgument[] args() {
        return args;
    }

    @Override
    public boolean isCreatingObject() {
        return isCreatingObject;
    }

    @Override
    public String getDescription() {
        return "Clears the collection";
    }
}
