package src.commands;

import src.logic.data.Receiver;

/**
 * Add new element into collection
 */
public class Add implements Command {

    private final static boolean isCreatingObject = true;

    public final static String[] args = new String[0];

    private final Receiver receiver;

    public Add(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public String execute(String[] args) {
        checkArgsConformity(args);
        return receiver.interactiveAdd();
    }

    @Override
    public String getDescription() {
        return "Add new element into collection";
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
