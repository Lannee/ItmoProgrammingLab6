package src.commands;

import src.Client;

/**
 * Displays a list of all available commands
 */
public class Help implements Command {

    private static final String[] args = new String[0];

    private final Invoker invoker;

    public Help(Invoker invoker) {
        this.invoker = invoker;
    }

    @Override
    public void execute(String[] args) {
        checkArgsConformity(args);
        Client.out.print(invoker.commandsInfo());
    }

    @Override
    public String[] args() {
        return args;
    }

    @Override
    public String getDescription() {
        return "Displays a list of all available commands";
    }
}
