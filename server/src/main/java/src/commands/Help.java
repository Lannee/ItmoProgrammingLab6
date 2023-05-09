package src.commands;

import module.commands.CommandArgument;
import module.commands.CommandType;

/**
 * Displays a list of all available commands
 */
public class Help implements Command {
    private static final CommandArgument[] args = new CommandArgument[0];
    public final static CommandType commandType = CommandType.NON_ARGUMENT_COMMAND;

    private final Invoker invoker;

    public Help(Invoker invoker) {
        this.invoker = invoker;
    }

    @Override
    public String execute(Object[] args) {
        checkArgsConformity(args);
        return invoker.commandsInfo();
//        return "";
    }

    @Override
    public CommandArgument[] args() {
        return args;
    }

    @Override
    public String getDescription() {
        return "Displays a list of all available commands";
    }

    @Override
    public CommandType getCommandType() {
        return commandType;
    }
}