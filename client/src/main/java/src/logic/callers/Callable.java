package src.logic.callers;

public interface Callable {
    public CallStatus handleCommand(String line);
    public CallStatus getObjectArgument();

    public String getCommand();
    public String[] getArguments();
}
