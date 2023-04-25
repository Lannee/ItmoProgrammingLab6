package src.commands;

import src.Client;
import src.logic.data.Receiver;
import src.utils.ObjectUtils;
import src.utils.StringConverter;

/**
 * Updates the value of a collection item whose id is equal to the specified one
 */
public class Update implements Command {

    public final static String[] args = {"id"};

    private final Receiver receiver;

    public Update(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute(String[] args) {
        checkArgsConformity(args);

        try {
            Long id = (Long) StringConverter.methodForType.get(Long.class).apply(args[0]);
            if(id <= 0) throw new NumberFormatException();
            Object obj = receiver.getElementByFieldValue(args()[0], id);

            if(obj == null) {
                if(!ObjectUtils.agreement(Client.in, Client.out, "Element with this id does not exist, do you want to create it (y/n) : ", false)) {
                    return;
                }
            }

            receiver.removeFromCollection(obj);
            receiver.interactiveAdd(id);

            Client.out.print("Element with " + args()[0] + " " + id + " was successfully updated\n");
        } catch (NoSuchFieldException e) {
            Client.out.print("Stored type does not support this command\n");
        } catch (NumberFormatException nfe) {
            Client.out.print("Incorrect argument value\n");
        }
    }

    @Override
    public String getDescription() {
        return  "Updates the value of a collection item whose " + args[0] + " is equal to the specified one";
    }

    @Override
    public String[] args() {
        return args;
    }
}
