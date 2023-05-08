package src.commands;

import module.commands.CommandArgument;
import src.logic.data.Receiver;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Prints all the elements of the collection in a string representation
 */
public class Show implements Command {
    private final static boolean isCreatingObject = false;

    private static final CommandArgument[] args = new CommandArgument[0];

    private final Receiver receiver;

    public Show(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public String execute(String[] args) {
        checkArgsConformity(args);
        return receiver.getFormattedCollection() + "\n";
    }

    @Override
    public String getDescription() {
        return "Prints all the elements of the collection in a string representation";
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