package src.commands;

import src.Client;
import src.logic.data.Receiver;

/**
 * Outputs information about the collection to the standard output stream
 */
public class Info implements Command {
    private final static boolean isCreatingObject = false;

    public static final String[] args = new String[0];

    private final Receiver receiver;

    public Info(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public String execute(String[] args) {
        checkArgsConformity(args);
        return receiver.getInfo() + "\n";
    }

    @Override
    public String getDescription() {
        return "Outputs information about the collection to the standard output stream";
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
