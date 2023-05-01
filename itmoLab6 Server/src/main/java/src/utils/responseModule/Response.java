package src.utils.responseModule;

import java.io.Serializable;

public class Response implements Serializable {
    private static final long serialVersionUID = 6529685098267757690L;
    private String responseCommandExecution;

    public Response (String aResponseCommandExecution) {
        responseCommandExecution = aResponseCommandExecution;
    }

    public String getResponseCommandExecution() {
        return responseCommandExecution;
    }

    @Override
    public String toString() {
        return "Response [responseCommandExecution=" + responseCommandExecution + "]";
    }
}
