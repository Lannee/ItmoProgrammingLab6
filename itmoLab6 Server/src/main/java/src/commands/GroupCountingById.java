package src.commands;

import src.Client;
import src.logic.data.Receiver;

import java.util.Map;

/**
 * Groups the elements of the collection by the value of the id field, displays the number of elements in each group
 */
public class GroupCountingById implements Command {
    private static final String[] args = new String[0];

    private final Receiver receiver;

    public GroupCountingById(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute(String[] args) {
        checkArgsConformity(args);
        try {
            Map<Object, Integer> groups = receiver.groupByField("id");
            groups.forEach((u, v) -> Client.out.print(u + " : " + v + "\n"));
        } catch (NoSuchFieldException e) {
            Client.out.print("Stored type does not support this command\n");
        }
    }

    @Override
    public String getDescription() {
        return "Groups the elements of the collection by the value of the id field, displays the number of elements in each group";
    }

    @Override
    public String[] args() {
        return args;
    }
}
