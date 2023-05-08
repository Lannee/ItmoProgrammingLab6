package src.commands;


import module.commands.CommandArgument;
import module.commands.CommandType;

/**
 * Exit program
 */
public class Exit implements Command {
    private static final CommandArgument[] args = new CommandArgument[0];
    public final static CommandType commandType = CommandType.NON_ARGUMENT_COMMAND;


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
    public CommandType getCommandType() {
        return commandType;
    }
}