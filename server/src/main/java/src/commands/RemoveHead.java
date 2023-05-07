package src.commands;

import module.commands.CommandArgument;
import src.logic.data.Receiver;

/**
 * Prints the first element of the collection and deletes it
 */
public class RemoveHead implements Command {
    private final static boolean isCreatingObject = false;

    private static final CommandArgument[] args = new CommandArgument[0];

    private final Receiver receiver;

    public RemoveHead(Receiver receiver) {
        this.receiver = receiver;
    }

    // Needed to be fixed because function <removeOn> is called in reciever
    @Override
    public String execute(String[] args) {
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
    public boolean isCreatingObject() {
        return isCreatingObject;
    }
}
