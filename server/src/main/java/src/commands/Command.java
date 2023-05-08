package src.commands;

import module.commands.CommandArgument;
import module.commands.CommandType;

import java.util.Arrays;

/**
 * Command interface, sets the behavior for each team in the project
 */
public interface Command {
    String execute(String[] args);

    String getDescription();

    CommandArgument[] args();

    CommandType getCommandType();
    /**
     * Checks arguments matching
     * @param args1
     * @param args2
     */
    default void checkArgsConformity(String[] args) {
        if(args.length != Arrays.stream(args()).filter(CommandArgument::isEnteredByUser).count()) throw new IllegalArgumentException("Invalid number of arguments");
    }

    static void checkArgsConformity(String[] args1, String[] args2) {
        if(args1.length != args2.length) throw new IllegalArgumentException("Invalid number of arguments");
    }
}
