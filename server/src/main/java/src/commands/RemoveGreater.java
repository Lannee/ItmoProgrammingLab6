package src.commands;

import module.commands.CommandArgument;
import module.stored.Dragon;
import src.logic.data.Receiver;
import module.logic.exceptions.CannotCreateObjectException;
import module.utils.ObjectUtils;

/**
 * Removes all items from the collection that exceed the specified
 */
public class RemoveGreater implements Command {
    private final static boolean isCreatingObject = true;

    private final Receiver receiver;

    private static final CommandArgument[] args = {new CommandArgument("element", Dragon.class, false)};

    public RemoveGreater(Receiver receiver) {
        this.receiver = receiver;
    }

    // Needed to be fixed.
    @Override
    public String execute(String[] args) {
        checkArgsConformity(args);
        try {
            Object obj = ObjectUtils.createObjectInteractively(receiver.getStoredType());
            receiver.removeOn(e -> e.compareTo(receiver.getStoredType().cast(obj)) > 0, false);
            return "";
        } catch (CannotCreateObjectException e) {
            return "Unable to create object: " + e.getMessage() + "\n";
        }
    }

    @Override
    public String getDescription() {
        return "Removes all items from the collection that exceed the specified";
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