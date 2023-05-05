package src.utils.responseModule;

public class ResponseFactory {
    public static Response createResponse(String aResponseCommandExecution, ResponseStatus responseStatus) {
        return new Response(aResponseCommandExecution, responseStatus);
    }
}
