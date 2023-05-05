package src.commands;

import src.logic.data.Receiver;

/**
 * Clears the collection
 */
public class Clear implements Command {
    private final static boolean isCreatingObject = false;

    private final static String[] args = new String[0];

    private final Receiver receiver;

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
    public String[] args() {
        return args;
    }

    @Override
    public String getDescription() {
        return "Clears the collection";
    }

    @Override
    public boolean isCreatingObject() {
        return isCreatingObject;
    }
}