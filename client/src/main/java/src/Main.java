package src;

import module.commands.CommandArgument;
import module.commands.CommandDescription;
import module.commands.CommandType;
import module.connection.requestModule.Request;
import module.connection.requestModule.RequestFactory;
import module.connection.requestModule.TypeOfRequest;
import module.connection.responseModule.CommandResponse;
import module.connection.responseModule.ResponseStatus;
import module.logic.exceptions.CannotCreateObjectException;

import java.util.Arrays;

public class Main {

    public static String formRequestAndGetResponse(CommandType commandType) {
        String result = "";
        boolean lineAndObjectArgumentFlag = false;
        switch (commandType) {
            case LINE_AND_OBJECT_ARGUMENT_COMMAND:
                CommandResponse response;
                result += "LINE_AND_OBJECT_ARGUMENT_COMMAND";
            case OBJECT_ARGUMENT_COMMAND:
                // Getting array of arguments of command
                result += "OBJECT_ARGUMENT_COMMAND";
                break;
            default:
                if (!lineAndObjectArgumentFlag) {
                    result += "default";
                }
        }

        return result;
    }
    public static void main(String[] args) {
//        Client client = new Client();
//        client.runClient();
        System.out.println(formRequestAndGetResponse(CommandType.NON_ARGUMENT_COMMAND));

    }
}
