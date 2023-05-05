package src.commands;


/**
 * Exit program
 */
public class Exit implements Command {

    private static final String[] args = new String[0];

    @Override
    public void execute(String[] args) {
        checkArgsConformity(args);
        System.exit(0);
    }

    @Override
    public String getDescription() {
        return "Exit program";
    }

    @Override
    public String[] args() {
        return args;
    }
}
