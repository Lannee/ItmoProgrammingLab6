package src.commands;

import src.logic.data.Receiver;

/**
 * Add new element into collection
 */
public class Add implements Command {

    public final static String[] args = new String[0];

    private final Receiver receiver;

    public Add(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute(String[] args) {
        checkArgsConformity(args);
        receiver.interactiveAdd();
    }

    @Override
    public String getDescription() {
        return "Add new element into collection";
    }

    @Override
    public String[] args() {
        return args;
    }
}
