package src.commands;

import module.commands.CommandArgument;
import module.commands.CommandType;

/**
 * Reads and executes the script from the specified file
 */
public class ExecuteScript implements Command {
    public final static CommandArgument[] args = {new CommandArgument("file_name", String.class)};
    public final static CommandType commandType = CommandType.OBJECT_ARGUMENT_COMMAND;

    private final Invoker invoker;

    public ExecuteScript(Invoker invoker) {
        this.invoker = invoker;
    }

    @Override
    public String execute(Object[] args) {
        checkArgsConformity(args);
        invoker.execute_script((String) args[0]);
        return "Successfully\n";
    }

    @Override
    public String getDescription() {
        return "Reads and executes the script from the specified file";
    }

    @Override
    public CommandArgument[] args() {
        return args;
    }

    @Override
    public CommandType getCommandType() {
        return commandType;
    }
}