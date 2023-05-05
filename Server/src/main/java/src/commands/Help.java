package src.commands;

/**
 * Displays a list of all available commands
 */
public class Help implements Command {
    private final static boolean isCreatingObject = false;

    private static final String[] args = new String[0];

    private final Invoker invoker;

    public Help(Invoker invoker) {
        this.invoker = invoker;
    }

    @Override
    public String execute(String[] args) {
        checkArgsConformity(args);
        return invoker.commandsInfo();
    }

    @Override
    public String[] args() {
        return args;
    }

    @Override
    public String getDescription() {
        return "Displays a list of all available commands";
    }

    @Override
    public boolean isCreatingObject() {
        return isCreatingObject;
    }
}
