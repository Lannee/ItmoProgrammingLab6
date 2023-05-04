package src.utils.requestModule;

public class RequestFactory {
    public static Request createRequest(String commandName, String argumentsToCommand, TypeOfRequest typeOfRequest) {
        return new Request(commandName, argumentsToCommand, typeOfRequest);
    }

    public static Request createRequest(TypeOfRequest typeOfRequest) {
        return new Request(null, null, typeOfRequest);
    }
}
