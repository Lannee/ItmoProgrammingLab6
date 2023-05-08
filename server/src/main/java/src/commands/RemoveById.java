package src.commands;

import module.commands.CommandArgument;
import src.logic.data.Receiver;
import src.utils.StringConverter;

/**
 * Removes an item from the collection by its id
 */
public class RemoveById implements Command {
    private static final CommandArgument[] args = {new CommandArgument("id", int.class, false)};
    private final static boolean isCreatingObject = false;

    private final Receiver receiver;

    public RemoveById(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public String execute(String[] args) {
        checkArgsConformity(args);

        try {
            Long id = Long.parseLong(args[0]);
            Object obj = receiver.getElementByFieldValue(args()[0].getArgumentName(), id);
            if(receiver.removeOn(e -> e == obj, false)) {
                return "Object with " + args()[0] + " " + id + " was successfully removed\n";
            } else {
                return "Unable to remove element from the collection. No element with such " + args()[0] + "\n";
            }
        } catch (NoSuchFieldException e) {
            return "Stored type does not support this command\n";
        } catch (NumberFormatException e) {
            return "Invalid command argument\n";
        }
    }

    @Override
    public String getDescription() {
        return "Removes an item from the collection by its " + args[0];
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
