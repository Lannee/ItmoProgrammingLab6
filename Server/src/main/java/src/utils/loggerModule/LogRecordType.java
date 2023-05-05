package src.utils.loggerModule;

public enum LogRecordType {
    CLIENT_SUCCESSFUL_CONNECTION ("successfully connected to the server"),
    CLIENT_FAILED_CONNECTION ("connection rejected by the server"),
    CLIENT_SUCCESSFULL_REQUEST ("request successfully reached the server"),
    CLIENT_REJECTED_REQUEST ("request rejected by the server"),
    CLIENT_MAP_FETCH ("map of commands sended"),
    CLIENT_SUCCESSFULL_RESPONSE ("response successfully completed and sended"),
    CLIENT_FAILED_RESPONSE ("response execution failed. Error message sended to client"),
    SERVER_SUCCESSFULLY_STARTED ("the server successfully started"), 
    SERVER_ERROR ("error was occured in the server ");

    private String description;

    private LogRecordType(String aDescription) {
        description = aDescription;
    }

    public String getDescription() {
        return description;
    }
}
