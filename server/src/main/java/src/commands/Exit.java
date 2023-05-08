package src.commands;


import module.commands.CommandArgument;

/**
 * Exit program
 */
public class Exit implements Command {
    private final static boolean isCreatingObject = false;

    private static final CommandArgument[] args = new CommandArgument[0];

    @Override
    public String execute(String[] args) {
        checkArgsConformity(args);
        return "Successfully\n";
    }

    @Override
    public String getDescription() {
        return "Exit program";
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