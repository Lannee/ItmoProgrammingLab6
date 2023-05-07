package src.commands;

import module.commands.CommandArgument;
import src.logic.data.Receiver;

/**
 * Outputs information about the collection to the standard output stream
 */
public class Info implements Command {
    private final static boolean isCreatingObject = false;

    public static final CommandArgument[] args = new CommandArgument[0];

    private final Receiver receiver;

    public Info(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public String execute(String[] args) throws IllegalArgumentException {
            checkArgsConformity(args);
        return receiver.getInfo() + "\n";
    }

    @Override
    public String getDescription() {
        return "Outputs information about the collection to the standard output stream";
    }

    @Override
    public CommandArgument[] args() {
        return args;
    }

    @Override
    public boolean isCreatingObject() {
        return isCreatingObject;
    }
}
