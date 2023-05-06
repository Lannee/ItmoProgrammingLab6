package module.connection.responseModule;

public class CommandResponse extends Response {

    private final String stringResponse;

    public CommandResponse(String response) {
        stringResponse = response;
    }

    public CommandResponse ofString(String response) {
        return new CommandResponse(response);
    }

    public String getResponse() {
        return stringResponse;
    }
}
