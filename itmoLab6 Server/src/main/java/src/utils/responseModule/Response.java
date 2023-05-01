package src.utils.responseModule;

import java.io.Serializable;

public class Response implements Serializable {
    private static final long serialVersionUID = 6529685098267757690L;
    private String responseCommandExecution;
    private ResponseStatus responseStatus;

    public Response (String aResponseCommandExecution, ResponseStatus aResponseStatus) {
        responseCommandExecution = aResponseCommandExecution;
        responseStatus = aResponseStatus;
    }

    public String getResponseCommandExecution() {
        return responseCommandExecution;
    }
    
    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    @Override
    public String toString() {
        return "Response [responseCommandExecution=" + responseCommandExecution + ", responseStatus=" + responseStatus
                + "]";
    }
}
