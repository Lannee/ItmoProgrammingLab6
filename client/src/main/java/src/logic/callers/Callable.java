package src.logic.callers;

import module.logic.exceptions.CannotCreateObjectException;

public interface Callable {
    public CallStatus handleCommand(String line);
    public CallStatus getObjectArgument(Class tClass) throws CannotCreateObjectException;
    public String getCommand();
    public String[] getArguments();
}
