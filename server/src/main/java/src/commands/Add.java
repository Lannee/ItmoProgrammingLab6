package src.commands;

import module.commands.CommandArgument;
import module.stored.Dragon;
import src.logic.data.Receiver;

/**
 * Add new element into collection
 */
public class Add implements Command {

    private final static boolean isCreatingObject = true;

    public final static CommandArgument[] args = {new CommandArgument("element", Dragon.class, false)};

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
    public CommandArgument[] args() {
        return args;
    }

    @Override
    public boolean isCreatingObject() {
        return isCreatingObject;
    }

}