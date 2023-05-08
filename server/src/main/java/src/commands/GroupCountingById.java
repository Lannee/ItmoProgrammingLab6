package src.commands;

import module.commands.CommandArgument;
import src.logic.data.Receiver;

import java.util.Map;

/**
 * Groups the elements of the collection by the value of the id field, displays the number of elements in each group
 */
public class GroupCountingById implements Command {
    private final static boolean isCreatingObject = false;

    private static final CommandArgument[] args = new CommandArgument[0];

    private final Receiver receiver;

    public GroupCountingById(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public String execute(String[] args) {
        checkArgsConformity(args);
        StringBuilder result = new StringBuilder();
        try {
            Map<Object, Integer> groups = receiver.groupByField("id");
            groups.forEach((u, v) -> result.append(u + " : " + v + "\n"));
        } catch (NoSuchFieldException e) {
            return "Stored type does not support this command\n";
        }
        return result.toString();
    }

    @Override
    public String getDescription() {
        return "Groups the elements of the collection by the value of the id field, displays the number of elements in each group";
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