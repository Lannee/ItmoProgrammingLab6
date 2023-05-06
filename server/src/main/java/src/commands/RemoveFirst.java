package src.commands;

import src.logic.data.Receiver;

/**
 * Removes the first element from the collection
 */
public class RemoveFirst implements Command {
    private final static boolean isCreatingObject = false;

    private final static String[] args = new String[0];

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
    public String[] args() {
        return args;
    }

    @Override
    public boolean isCreatingObject() {
        return isCreatingObject;
    }
}