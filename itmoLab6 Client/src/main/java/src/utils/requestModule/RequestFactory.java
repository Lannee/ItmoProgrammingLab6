package src.utils.requestModule;

public class RequestFactory {
    public static Request createRequest(String commandName, String argumentsToCommand, TypeOfRequest typeOfRequest) {
        return new Request(commandName, argumentsToCommand, typeOfRequest);
    }
}
