package src.commands;

import src.logic.data.Receiver;

/**
 * Print the number of elements whose weight field value is greater than the specified one
 */
public class CountGreaterThanWeight implements Command {
    private final static boolean isCreatingObject = false;


    private final static String[] args = {"weight"};

    private final Receiver receiver;

    public CountGreaterThanWeight(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public String execute(String[] args) {
        checkArgsConformity(args);
        try {
            int amount = receiver.countCompareToValueByField(args()[0], args[0], (u, v) -> -u.compareTo(v));
            return amount + "\n";
        } catch (NumberFormatException e) {
            return "Incorrect given value\n";
        } catch (NoSuchFieldException e) {
            return "Stored type does not have typed field\n";
        }
    }

    @Override
    public String[] args() {
        return args;
    }

    @Override
    public String getDescription() {
        return "Print the number of elements whose " + args[0] + " field value is greater than the specified one";
    }

    @Override
    public boolean isCreatingObject() {
        return isCreatingObject;
    }
}