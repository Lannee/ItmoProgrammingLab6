package src.commands;

import module.commands.CommandArgsType;
import module.commands.CommandArgument;

/**
 * Reads and executes the script from the specified file
 */
public class ExecuteScript implements Command {
    public final static CommandArgument[] args = {new CommandArgument("file_name", String.class, false)};
    private final Invoker invoker;

    private final static boolean isCreatingObject = false;

    public ExecuteScript(Invoker invoker) {
        this.invoker = invoker;
    }

    @Override
    public String execute(String[] args) {
        checkArgsConformity(args);
        invoker.execute_script(args[0]);
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
    public boolean isCreatingObject() {
        return isCreatingObject;
    }
}
