package src.commands;


/**
 * Exit program
 */
public class Exit implements Command {
    private final static boolean isCreatingObject = false;

    private static final String[] args = new String[0];

    @Override
    public String execute(String[] args) {
        checkArgsConformity(args);
        return "Successfully";
    }

    @Override
    public String getDescription() {
        return "Exit program";
    }

    @Override
    public String[] args() {
        return args;
    }

    @Override
    public boolean isCreatingObject() {
        return isCreatingObject;
    }
}
