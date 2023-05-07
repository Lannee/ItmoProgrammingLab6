package src.logic.callers;

public interface Callable {
    public CallStatus handleCommand(String line);
}
