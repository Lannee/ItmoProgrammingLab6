package src.commands;

import module.commands.CommandArgument;
import module.commands.CommandType;
import module.stored.Dragon;
import src.logic.data.Receiver;
import src.utils.StringConverter;

/**
 * Updates the value of a collection item whose id is equal to the specified one
 */
public class Update implements Command {
    public final static CommandArgument[] args = {new CommandArgument("id", int.class),
            new CommandArgument("element", Dragon.class, false)};
    public final static CommandType commandType = CommandType.LINE_AND_OBJECT_ARGUMENT_COMMAND;

    private final Receiver receiver;

    public Update(Receiver receiver) {
        this.receiver = receiver;
    }


    // Again. It's forbidden to get in & out streams from client. Needed to be fixed.
    @Override
    public String execute(Object[] args) {
        checkArgsConformity(args);

        try {
            Long id = (Long) args[0];
//            Long id = (Long) StringConverter.methodForType.get(Long.class).apply(args[0]);
            if(id <= 0) throw new NumberFormatException();
            Object obj = receiver.getElementByFieldValue(args()[0].getArgumentName(), id);

            if(obj == null) {
                // if(!ObjectUtils.agreement(Client.in, Client.out, "Element with this id does not exist, do you want to create it (y/n) : ", false)) {
                //     return "";
                // }
            }

            receiver.removeFromCollection(obj);
            receiver.interactiveAdd(id);

            return "Element with " + args()[0] + " " + id + " was successfully updated\n";
        } catch (NoSuchFieldException e) {
            return "Stored type does not support this command\n";
        } catch (NumberFormatException nfe) {
            return "Incorrect argument value\n";
        }
    }

    @Override
    public String getDescription() {
        return  "Updates the value of a collection item whose " + args[0] + " is equal to the specified one";
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