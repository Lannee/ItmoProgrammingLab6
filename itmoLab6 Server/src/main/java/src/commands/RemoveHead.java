package src.commands;

import src.Client;
import src.logic.data.Receiver;

/**
 * Prints the first element of the collection and deletes it
 */
public class RemoveHead implements Command {
    private final static boolean isCreatingObject = false;

    private static final String[] args = new String[0];

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
    public String[] args() {
        return args;
    }

    @Override
    public boolean isCreatingObject() {
        return isCreatingObject;
    }
}
