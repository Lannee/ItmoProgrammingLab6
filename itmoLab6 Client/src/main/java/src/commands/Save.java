package src.commands;

import src.logic.data.Receiver;

/**
 * Saves the collection to the file
 */
public class Save implements Command {

    private static final String[] args = new String[0];

    private final Receiver receiver;

    public Save(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute(String[] args) {
        checkArgsConformity(args);
        receiver.saveCollection();
    }

    @Override
    public String getDescription() {
        return "Saves the collection to the file";
    }

    @Override
    public String[] args() {
        return args;
    }
}
