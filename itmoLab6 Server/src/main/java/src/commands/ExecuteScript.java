package src.commands;

/**
 * Reads and executes the script from the specified file
 */
public class ExecuteScript implements Command {

    public final static String[] args = {"file_name"};
    private final Invoker invoker;

    public ExecuteScript(Invoker invoker) {
        this.invoker = invoker;
    }

    @Override
    public String execute(String[] args) {
        checkArgsConformity(args);
        invoker.execute_script(args[0]);
        return "Successfully";
    }

    @Override
    public String getDescription() {
        return "Reads and executes the script from the specified file";
    }

    @Override
    public String[] args() {
        return args;
    }
}
