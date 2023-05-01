package src.commands;

import src.Client;
import src.logic.data.Receiver;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Prints the elements of the collection in ascending order
 */
public class PrintAscending implements Command {

    private static final String[] args = new String[0];

    private final Receiver receiver;

    public PrintAscending(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public String execute(String[] args) {
        checkArgsConformity(args);
        return receiver.getFormattedCollection(Comparator.naturalOrder()) + "\n";
    }

    @Override
    public String getDescription() {
        return "Prints the elements of the collection in ascending order";
    }

    @Override
    public String[] args() {
        return args;
    }
}
