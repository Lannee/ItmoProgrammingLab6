package src.commands;

import src.Client;
import src.logic.data.Receiver;
import src.utils.StringConverter;

/**
 * Removes an item from the collection by its id
 */
public class RemoveById implements Command {

    private static final String[] args = {"id"};

    private final Receiver receiver;

    public RemoveById(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute(String[] args) {
        checkArgsConformity(args);

        try {
            Long id = Long.parseLong(args[0]);
            Object obj = receiver.getElementByFieldValue(args()[0], id);
            if(receiver.removeOn(e -> e == obj, false)) {
                Client.out.print("Object with " + args()[0] + " " + id + " was successfully removed\n");
            } else {
                Client.out.print("Unable to remove element from the collection. No element with such " + args()[0] + "\n");
            }
        } catch (NoSuchFieldException e) {
            Client.out.print("Stored type does not support this command\n");
        } catch (NumberFormatException e) {
            Client.out.print("Invalid command argument\n");
        }
    }

    @Override
    public String getDescription() {
        return "Removes an item from the collection by its " + args[0];
    }

    @Override
    public String[] args() {
        return args;
    }
}
